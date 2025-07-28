package com.gooderreads.gooder_reads.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gooderreads.gooder_reads.dto.ReviewDTO;
import com.gooderreads.gooder_reads.entity.Review;
import com.gooderreads.gooder_reads.mapper.ReviewMapper;
import com.gooderreads.gooder_reads.repository.ReviewRepository;

import jakarta.transaction.Transactional;

@Service
public class ReviewService {

    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    ReviewMapper reviewMapper;

    @Transactional
    public List<ReviewDTO> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream()
            .map(review -> reviewMapper.convertToDTO(review))
            .collect(Collectors.toList());
    }

    @Transactional
    public ReviewDTO getReviewById(Long id) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found."));
        return reviewMapper.convertToDTO(review);
    }

}
