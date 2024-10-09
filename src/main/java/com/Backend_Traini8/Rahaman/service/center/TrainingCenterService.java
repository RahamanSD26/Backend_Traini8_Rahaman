package com.Backend_Traini8.Rahaman.service.center;

import com.Backend_Traini8.Rahaman.dto.center.TrainingCenterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TrainingCenterService {
    TrainingCenterDto createEntity(TrainingCenterDto requestDto) throws Exception;
    TrainingCenterDto getCenterByEmailWithNull(String email);
    TrainingCenterDto getCenterByContactPhoneWithNull(String email);
    Page<TrainingCenterDto> getAllEntities(Pageable pageable)throws Exception;
    TrainingCenterDto getEntityById(String id) throws Exception;
}
