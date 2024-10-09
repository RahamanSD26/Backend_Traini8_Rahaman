package com.Backend_Traini8.Rahaman.service.center;

import com.Backend_Traini8.Rahaman.config.Constants;
import com.Backend_Traini8.Rahaman.domain.center.TrainingCenter;
import com.Backend_Traini8.Rahaman.dto.center.TrainingCenterDto;
import com.Backend_Traini8.Rahaman.mapper.center.TrainingCenterMapper;
import com.Backend_Traini8.Rahaman.repository.center.TrainingCenterRepository;
import com.Backend_Traini8.Rahaman.util.CommonUtil;
import com.mongodb.DuplicateKeyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrainingCenterServiceImpl implements TrainingCenterService{
    @Autowired
    private TrainingCenterRepository trainingCenterRepository;

    @Override
    public TrainingCenterDto createEntity(TrainingCenterDto requestDto) throws Exception {
        //Checking For Existing Country
        if(getCenterByEmailWithNull(requestDto.getContactEmail())!=null || getCenterByContactPhoneWithNull(requestDto.getContactPhone())!=null){
            throw new Exception("ERR_ADMIN_0008") ;
        }
        if(requestDto.getIsActive()==null){
            requestDto.setIsActive(true);
        }
        //Incrementally Generating ID & Retry If Got DuplicateKeyException Due To ID
        while(true){
            String newGeneratedGuid= CommonUtil.getNewGeneratedId(trainingCenterRepository, Constants.ID_PREFIX_TRAINING_CENTER,Constants.ID_PART_NUMBER_COUNT_TRAINING_CENTER);
            requestDto.setGuid(newGeneratedGuid);
            try {
                return TrainingCenterMapper.toDto(trainingCenterRepository.save(TrainingCenterMapper.toEntity(requestDto)));
            }catch(Exception ex){
                if(ex instanceof DuplicateKeyException){continue;}
                throw new Exception("ERR_ADMIN_0009");
            }
        }
    }

    @Override
    public TrainingCenterDto getCenterByEmailWithNull(String email){
        Optional<TrainingCenter> trainingCenter = trainingCenterRepository.findByContactEmail(email) ;
        return trainingCenter.map(TrainingCenterMapper::toDto).orElse(null);
    }
    @Override
    public TrainingCenterDto getCenterByContactPhoneWithNull(String contactPhone){
        Optional<TrainingCenter> trainingCenter = trainingCenterRepository.findByContactPhone(contactPhone) ;
        return trainingCenter.map(TrainingCenterMapper::toDto).orElse(null);
    }

    @Override
    public Page<TrainingCenterDto> getAllEntities(Pageable pageable) throws Exception {
        Page<TrainingCenter> entityPage = trainingCenterRepository.findAll(pageable);
        if (entityPage.getContent().size() > 0) {
            return new PageImpl<>(entityPage.getContent().stream().map(TrainingCenterMapper::toDto).collect(Collectors.toList()), pageable, entityPage.getTotalElements());
        } else {
            return new PageImpl<>(new ArrayList<>(), pageable, entityPage.getTotalElements());
        }
    }

    @Override
    public TrainingCenterDto getEntityById(String id) throws Exception {
        Optional<TrainingCenter> optEntity = trainingCenterRepository.findByGuid(id);
        if (optEntity.isEmpty()) {
            throw new Exception("ERR_ADMIN_0017");
        }
        return TrainingCenterMapper.toDto(optEntity.get());
    }
}
