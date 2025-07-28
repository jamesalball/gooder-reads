package com.gooderreads.gooder_reads.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gooderreads.gooder_reads.dto.ReviewDTO;
import com.gooderreads.gooder_reads.entity.Review;

@Component
public class ReviewMapper {

    @Autowired
    private ModelMapper modelMapper;

    public ReviewDTO convertToDTO(Review review) {
        ReviewDTO reviewDTO = modelMapper.map(review, ReviewDTO.class);
        return reviewDTO;
    }

    public Review convertToEntity(ReviewDTO reviewDTO) {
        Review review = modelMapper.map(reviewDTO, Review.class);
        return review;
    }
}
