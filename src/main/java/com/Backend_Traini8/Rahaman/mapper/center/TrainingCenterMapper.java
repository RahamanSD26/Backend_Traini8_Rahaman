package com.Backend_Traini8.Rahaman.mapper.center;

import com.Backend_Traini8.Rahaman.domain.center.TrainingCenter;
import com.Backend_Traini8.Rahaman.dto.center.TrainingCenterDto;
import com.Backend_Traini8.Rahaman.util.CommonUtil;
import org.springframework.stereotype.Component;

@Component
public class TrainingCenterMapper {

    public static TrainingCenter toEntity(TrainingCenterDto dto){
        TrainingCenter entity=new TrainingCenter();
        CommonUtil.mapCommonFieldsToEntity(entity,dto);
        //Specific Fields
        entity.setCenterName(dto.getCenterName());
        entity.setCenterCode(dto.getCenterCode());
        entity.setAddress(dto.getAddress());
        entity.setStudentCapacity(dto.getStudentCapacity());
        entity.setCourseOffered(dto.getCourseOffered());
        entity.setContactEmail(dto.getContactEmail());
        entity.setContactPhone(dto.getContactPhone());
        return entity;
    }

    public static TrainingCenterDto toDto(TrainingCenter entity){
        TrainingCenterDto dto=new TrainingCenterDto();
        CommonUtil.mapCommonFieldsToDto(entity,dto);
        //Specific Fields
        dto.setCenterName(entity.getCenterName());
        dto.setCenterCode(entity.getCenterCode());
        dto.setAddress(entity.getAddress());
        dto.setStudentCapacity(entity.getStudentCapacity());
        dto.setCourseOffered(entity.getCourseOffered());
        dto.setContactEmail(entity.getContactEmail());
        dto.setContactPhone(entity.getContactPhone());
        return dto;
    }
}
