package com.Backend_Traini8.Rahaman.util;

import com.Backend_Traini8.Rahaman.config.Constants;
import com.Backend_Traini8.Rahaman.domain.BaseEntity;
import com.Backend_Traini8.Rahaman.dto.BaseEntityDto;
import com.Backend_Traini8.Rahaman.exception.response.ResponseStatusDTO;
import com.Backend_Traini8.Rahaman.repository.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Component
public class CommonUtil {
    private static MessageSource errorMessageSource ;
    private static MessageSource successMessageSource ;

    @Autowired
    public CommonUtil(MessageSource errorMessageSource, MessageSource successMessageSource) {
        CommonUtil.errorMessageSource = errorMessageSource;
        CommonUtil.successMessageSource = successMessageSource;
    }

    public static Locale getPreferredLocale(){
        return Locale.ENGLISH ;
    }
    private static Long convertDateTimeToMilliSeconds(String dateTime){
        //String myDate = "2014/10/29 18:10:45";
        //LocalDateTime localDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss") );
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        /*
          With this new Date/Time API, when using a date, you need to
          specify the Zone where the date/time will be used. For your case,
          seems that you want/need to use the default zone of your system.
          Check which zone you need to use for specific behaviour e.g.
          CET or America/Lima
        */
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    private static String convertMilliSecondsToDateTime(Long msDateTime){
        DateFormat obj = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date res = new Date(msDateTime);
        return obj.format(res);
    }
    public static void mapCommonFieldsToEntity(BaseEntity entity, BaseEntityDto dto){
        //Common Fields
        entity.setId(dto.getId());
        entity.setGuid(dto.getGuid());
        entity.setIsActive(dto.getIsActive());
        if(dto.getCreatedOn()!=null) {
            entity.setCreatedOn(CommonUtil.convertDateTimeToMilliSeconds(dto.getCreatedOn()));
        }
        if(dto.getLastUpdatedOn()!=null) {
            entity.setLastUpdatedOn(CommonUtil.convertDateTimeToMilliSeconds(dto.getLastUpdatedOn()));
        }
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setLastUpdatedBy(dto.getLastUpdatedBy());
    }

    public static void mapCommonFieldsToDto(BaseEntity entity, BaseEntityDto dto){
        //Common Fields
        dto.setId(entity.getId());
        dto.setGuid(entity.getGuid());
        dto.setIsActive(entity.getIsActive());
        if(entity.getCreatedOn()!=null) {
            dto.setCreatedOn(CommonUtil.convertMilliSecondsToDateTime(entity.getCreatedOn()));
        }
        if(entity.getLastUpdatedOn()!=null) {
            dto.setLastUpdatedOn(CommonUtil.convertMilliSecondsToDateTime(entity.getLastUpdatedOn()));
        }
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setLastUpdatedBy(entity.getLastUpdatedBy());
    }

    private static Long getLimitFromPartNumberCount(Integer partNumberCount){
        Long limit = 0L;
        String limitString="";
        final String NINE="9";
        for(int i=0; i<partNumberCount; i++){
            limitString=limitString+NINE;
        }
        limit=Long.parseLong(limitString);
        return limit;
    }

    public static Integer getDigitsCountFromInteger(Long number){
        Integer count = 0 ;
        while (number != 0) {
            number /= 10; ++count;
        }
        return count;
    }
    public static String getCustomGeneratedId(String prefix, Integer partNumberDigitCount, Long serialNumber) throws Exception {
        String generatedId=null ;
        Integer serialNumberDigitCount=getDigitsCountFromInteger(serialNumber) ;
        if(prefix!=null){
            prefix=prefix.trim();
            if(prefix.length()==2){
                if(partNumberDigitCount>2){
                    generatedId=prefix ;
                    for(int i=1; i<=partNumberDigitCount-serialNumberDigitCount; i++){
                        generatedId = generatedId + Constants.NUMBER_CONSTANT_ZERO;
                    }
                    generatedId=generatedId+serialNumber ;
                    return generatedId;
                }else{
                    throw new Exception("ERR_ADMIN_0006");
                }
            }else{
                throw new Exception("ERR_ADMIN_0005");
            }
        }else{
            throw new Exception("ERR_ADMIN_0004");
        }
    }

    public static String getNewGeneratedId(BaseRepository baseRepository, String idPrefix, Integer partNumberCount) throws Exception {
        Optional<BaseEntity> lastEntity = (Optional<BaseEntity>) baseRepository.findTopByOrderByCreatedOnDesc() ;
        Long serialNumber= Constants.NUMBER_CONSTANT_LONG_ONE ;
        if(lastEntity.isPresent()){
            Long lastSerialNumber = Long.valueOf(lastEntity.get().getGuid().replace(idPrefix,Constants.BLANK_CONSTANT));
            serialNumber = lastSerialNumber+1;
            if(serialNumber>getLimitFromPartNumberCount(partNumberCount)){
                throw new Exception("ERR_ADMIN_0007") ;
            }
        }
        return getCustomGeneratedId(idPrefix,partNumberCount,serialNumber) ;
    }

    public Boolean validateBaseDto(BaseEntityDto dto) throws Exception {
        if(dto.getId()!=null){
            throw new Exception("ERR_ADMIN_0010") ;
        }
        if(dto.getCreatedOn()!=null){
            throw new Exception("ERR_ADMIN_0011") ;
        }
        if(dto.getLastUpdatedOn()!=null){
            throw new Exception("ERR_ADMIN_0012") ;
        }
        if(dto.getCreatedBy()!=null){
            throw new Exception("ERR_ADMIN_0013") ;
        }
        if(dto.getLastUpdatedBy()!=null){
            throw new Exception("ERR_ADMIN_0014") ;
        }
        if(dto.getIsActive()!=null){
            throw new Exception("ERR_ADMIN_0015") ;
        }
        if(dto.getGuid()!=null){
            throw new Exception("ERR_ADMIN_0016") ;
        }
        return true ;
    }

    private static String getErrorCodeMessageFromMANVException(MethodArgumentNotValidException exception){
        final Optional<ObjectError> firstError = exception.getBindingResult().getAllErrors().stream().findFirst();
        if (firstError.isPresent()) {
            return firstError.get().getDefaultMessage();
        }
        return null ;
    }

    private static String getExceptionSpecificErrorCode(Exception ex){
        String errorCode = null ;
        if(ex instanceof MethodArgumentNotValidException){
            errorCode = getErrorCodeMessageFromMANVException((MethodArgumentNotValidException)ex) ;
        }
        return errorCode ;
    }

    private static String getAdjustedStatusCode(String statusCode){
        //Adjusting StatusCode
        String subCode = statusCode;
        if(statusCode.contains(Constants.ERROR_CODE_PREFIX)){
            subCode = statusCode.substring(statusCode.indexOf(Constants.ERROR_CODE_PREFIX)+1);
            statusCode="E"+subCode.trim();
        }else{
            subCode = statusCode.substring(statusCode.indexOf(Constants.SUCCESS_CODE_PREFIX)+1);
            statusCode="S"+subCode.trim();
        }
        return statusCode;
    }

    public static ResponseStatusDTO getStatusObject(String statusCode, Exception ex){
        ResponseStatusDTO responseDTO = new ResponseStatusDTO();
        String statusMessage = Constants.BLANK_CONSTANT;
        boolean isKnownMessage=true;
        statusCode=statusCode.trim();

        //Setting Default Messages
        if(statusCode.startsWith(Constants.SUCCESS_CODE_PREFIX)){
            statusMessage = successMessageSource.getMessage(Constants.DEFAULT_SUCCESS_MESSAGE_CODE,null, getPreferredLocale());
            responseDTO = new ResponseStatusDTO(Constants.DEFAULT_SUCCESS_MESSAGE_CODE,statusMessage,null) ;
        }else if(statusCode.startsWith(Constants.ERROR_CODE_PREFIX)){
            statusMessage = errorMessageSource.getMessage(Constants.DEFAULT_ERROR_MESSAGE_CODE,null, getPreferredLocale());
            responseDTO = new ResponseStatusDTO(Constants.DEFAULT_ERROR_MESSAGE_CODE,statusMessage,null) ;
        }else{
            isKnownMessage=false;
            statusMessage = errorMessageSource.getMessage(Constants.DEFAULT_ERROR_MESSAGE_CODE,null, getPreferredLocale());
            responseDTO = new ResponseStatusDTO(Constants.DEFAULT_ERROR_MESSAGE_CODE,statusMessage,statusCode) ;
        }
        //Setting StatusCode Message
        if(isKnownMessage){
            //Getting Error & Success Code Messages
            if (statusCode.startsWith(Constants.ERROR_CODE_PREFIX)) {
                //Getting Specific Error Message By Code
                statusMessage = errorMessageSource.getMessage(statusCode, null, getPreferredLocale());
            } else if (statusCode.startsWith(Constants.SUCCESS_CODE_PREFIX)) {
                //Getting Specific Success Message By Code
                statusMessage = successMessageSource.getMessage(statusCode, null, getPreferredLocale());
            } else {
                responseDTO.setActualMessage(statusCode);
            }
            //Setting Final Message & Code
            responseDTO.setCode(statusCode);
            responseDTO.setMessage(statusMessage);
        }else{
            statusCode=getExceptionSpecificErrorCode(ex);
            if(statusCode!=null){
                responseDTO.setCode(statusCode);
                responseDTO.setMessage(errorMessageSource.getMessage(statusCode,null, getPreferredLocale()));
                responseDTO.setActualMessage(null);
            }
        }
        return responseDTO;
    }

    public String validateEntityId(String id) throws Exception {
        if(id==null){
            throw new Exception("ERR_ADMIN_0018") ;
        }
        id = id.trim() ;
        if(id.length()<4){
            throw new Exception("ERR_ADMIN_0018") ;
        }
        return id ;
    }
    public static HttpStatus getHttpStatusByException(Exception ex){
        return HttpStatus.BAD_REQUEST ;
    }

    public static String getRandomUUID(Integer digits){
        return UUID.randomUUID().toString().toUpperCase();
    }
}
