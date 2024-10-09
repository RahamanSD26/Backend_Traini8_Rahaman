package com.Backend_Traini8.Rahaman.repository;

import com.Backend_Traini8.Rahaman.domain.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BaseRepository<T extends BaseEntity,ID> extends MongoRepository<T,ID> {
    Page<T> findAll(Pageable pageable);
    Page<T> findByIsActive(Boolean isActive, Pageable pageable);
    Optional<T> findByGuid(String guid);
    Optional<T> findTopByOrderByCreatedOnDesc();
    Optional<T> findTopByGuidStartsWithOrderByCreatedOnDesc(String regex);
}
