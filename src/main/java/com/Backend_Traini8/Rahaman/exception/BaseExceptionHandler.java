package com.Backend_Traini8.Rahaman.exception;

import com.Backend_Traini8.Rahaman.config.Constants;
import com.Backend_Traini8.Rahaman.exception.response.ResponseStatusDTO;
import com.Backend_Traini8.Rahaman.pagination.CustomPage;
import com.Backend_Traini8.Rahaman.util.CommonUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;



@ControllerAdvice
public class BaseExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomPage<String>> handleErrors(Exception ex, WebRequest request) throws Exception {
        ResponseStatusDTO responseStatus = getErrorResponseByCode(ex.getMessage(),ex.getClass().getName(),ex);
        HttpStatus httpStatus = CommonUtil.getHttpStatusByException(ex);
        return new ResponseEntity<>(new CustomPage<>(null, responseStatus), httpStatus);
    }

    private ResponseStatusDTO getErrorResponseByCode(String errorCode, String exceptionClassName, Exception ex){
        String errorMessage= ex.getMessage() ;
        ResponseStatusDTO responseStatusDTO = new ResponseStatusDTO(CommonUtil.getRandomUUID(16), Constants.DEFAULT_ERROR_MESSAGE_CODE,errorMessage,null) ;
        try{
            return CommonUtil.getStatusObject(errorCode,ex);
        }catch(Exception exp){
            return responseStatusDTO;
        }
    }
}