package com.Backend_Traini8.Rahaman.controller;

import com.Backend_Traini8.Rahaman.config.Constants;
import com.Backend_Traini8.Rahaman.config.RequestEndPoints;
import com.Backend_Traini8.Rahaman.dto.center.TrainingCenterDto;
import com.Backend_Traini8.Rahaman.pagination.CustomPage;
import com.Backend_Traini8.Rahaman.service.center.TrainingCenterService;
import com.Backend_Traini8.Rahaman.util.CommonUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(RequestEndPoints.TRAINING_CENTER_API_V1)
@RestController
public class TrainingController {
    @Autowired
    private TrainingCenterService trainingCenterService;

    @Autowired
    private CommonUtil commonUtil ;
    @PostMapping
    public ResponseEntity<CustomPage<TrainingCenterDto>> createEntity(@Valid @RequestBody TrainingCenterDto requestDTO) throws Exception {
        commonUtil.validateBaseDto(requestDTO);
        commonUtil.validateAddress(requestDTO.getAddress());
        return new ResponseEntity<>(new CustomPage<>(trainingCenterService.createEntity(requestDTO), Constants.BLANK_CONSTANT), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<CustomPage<TrainingCenterDto>> getAllEntities(Pageable pageable) throws Exception {
        return new ResponseEntity<>(new CustomPage<>(trainingCenterService.getAllEntities(pageable),Constants.BLANK_CONSTANT), HttpStatus.OK);
    }
    @GetMapping({"/{id}"})
    public ResponseEntity<CustomPage<TrainingCenterDto>> getEntityById(@PathVariable(name="id") String id) throws Exception{
        id = commonUtil.validateEntityId(id) ;
        return new ResponseEntity<>(new CustomPage<>(trainingCenterService.getEntityById(id), Constants.BLANK_CONSTANT), HttpStatus.OK);
    }
    @GetMapping({"/phone/{phone}"})
    public ResponseEntity<CustomPage<TrainingCenterDto>> getEntityByPhone(@PathVariable(name="phone") String phone) throws Exception{
        TrainingCenterDto trainingCenterDto=trainingCenterService.getCenterByContactPhoneWithNull(phone);
        if(trainingCenterDto==null){
            throw new Exception("ERR_ADMIN_0020");
        }
        return new ResponseEntity<>(new CustomPage<>(trainingCenterDto, Constants.BLANK_CONSTANT), HttpStatus.OK);
    }
    @GetMapping({"/email/{email}"})
    public ResponseEntity<CustomPage<TrainingCenterDto>> getEntityByEmail(@PathVariable(name="email") String email) throws Exception{
        TrainingCenterDto trainingCenterDto=trainingCenterService.getCenterByEmailWithNull(email);
        if(trainingCenterDto==null){
            throw new Exception("ERR_ADMIN_0019");
        }
        return new ResponseEntity<>(new CustomPage<>(trainingCenterDto, Constants.BLANK_CONSTANT), HttpStatus.OK);
    }
}
