package com.gooderreads.gooder_reads.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gooderreads.gooder_reads.service.ReviewService;
import com.gooderreads.gooder_reads.dto.ReviewDTO;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api/review")
public class ReviewController {
    
    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        List<ReviewDTO> reviews = reviewService.getAllReviews();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> getReview(@PathVariable Long id) {
        ReviewDTO review = reviewService.getReviewById(id);
        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ReviewDTO> postReview(@RequestBody ReviewDTO review) {
        ReviewDTO savedReviewDTO = reviewService.createReview(review);
        return new ResponseEntity<>(savedReviewDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewDTO> putReview(@PathVariable Long id, @RequestBody ReviewDTO updatedReview) {
        ReviewDTO savedReviewDTO = reviewService.updateReview(id, updatedReview);
        return new ResponseEntity<>(savedReviewDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return new ResponseEntity<>("Review with id of " + id + " successfully deleted.", HttpStatus.OK);
    }
    
    
}
