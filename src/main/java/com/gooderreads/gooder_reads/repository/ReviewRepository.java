package com.gooderreads.gooder_reads.repository;

import com.gooderreads.gooder_reads.entity.Review;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>{

    Optional<Review> findById(Long id);
    
}
