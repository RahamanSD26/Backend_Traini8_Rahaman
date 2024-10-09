package com.Backend_Traini8.Rahaman.repository.center;

import com.Backend_Traini8.Rahaman.domain.center.TrainingCenter;
import com.Backend_Traini8.Rahaman.repository.BaseRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TrainingCenterRepository extends BaseRepository<TrainingCenter,String> {
    Optional<TrainingCenter> findByContactEmail(String email);
    Optional<TrainingCenter> findByContactPhone(String phone);
}
