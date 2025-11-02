package com.gooderreads.gooder_reads.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gooderreads.gooder_reads.dto.BookDTO;
import com.gooderreads.gooder_reads.dto.ReviewDTO;
import com.gooderreads.gooder_reads.dto.UserDTO;
import com.gooderreads.gooder_reads.entity.Book;
import com.gooderreads.gooder_reads.entity.Review;
import com.gooderreads.gooder_reads.entity.User;
import com.gooderreads.gooder_reads.exception.ResourceNotFoundException;
import com.gooderreads.gooder_reads.mapper.ReviewMapper;
import com.gooderreads.gooder_reads.repository.ReviewRepository;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ReviewMapper reviewMapper;

    @InjectMocks
    private ReviewService reviewService;

    //Long variables to be used as Ids
    private Long recordId = 1L;
    private Long authorId = 1L;

    private Book book = new Book(recordId, "The Stand", 1L, "synopsis", new ArrayList<Review>());
    private BookDTO bookDTO = new BookDTO(recordId, "The Stand", authorId, "synopsis", new ArrayList<Review>());

    private User user = new User(recordId, "testing@test.com", "Test User", new ArrayList<Review>());
    private UserDTO userDTO = new UserDTO(recordId, "testing@test.com", "Test User");

    //Review, Optional<Review>, and ReviewDTO variables to be used in the test methods
    private Review expectedReview;
    private Optional<Review> expectedReviewOptional;
    private ReviewDTO expectedReviewDTO;

    @BeforeEach
    void setUp() {

        expectedReview = new Review(recordId, book, user, 5.0, "This book rocks!");
        expectedReviewOptional = Optional.of(expectedReview);
        expectedReviewDTO = new ReviewDTO(expectedReview);

    }

    //Test getting a Review by its Id successfully
    @Test
    void testGetReviewById_Success() {

        //Mock reviewRepository and reviewMapper behavior
        when(reviewRepository.findById(recordId)).thenReturn(expectedReviewOptional);
        when(reviewMapper.convertToDTO(expectedReview)).thenReturn(expectedReviewDTO);

        //Call the method being tested
        ReviewDTO actualReviewDTO = reviewService.getReviewById(recordId);

        //Assert a reviewDTO was returned
        assertNotNull(actualReviewDTO);

        //Assert the reviewDTO data matches what's expected
        assertEquals(expectedReviewDTO.getId(), actualReviewDTO.getId());
        assertEquals(expectedReviewDTO.getReviewText(), actualReviewDTO.getReviewText());

        //Verify the method being tested was called
        verify(reviewRepository, times(1)).findById(recordId);

    }

    //Test attempting to get a Review by its Id unsusccessfully. Applicable for getting, updating, or deleting a nonexistent review
    @Test
    void testGetReviewById_NotFound() {

        //Mock reviewRepository behavior
        when(reviewRepository.findById(recordId)).thenReturn(Optional.empty());

        //Trying to find a Review that doesn't exist should throw a ResourceNotFoundException
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> reviewService.getReviewById(recordId));

        //Assert the expected exception was thrown
        assertEquals("Review not found", thrown.getMessage());

        //Verify the method being tested was called
        verify(reviewRepository, times(1)).findById(recordId);

    }

    //Test listing all Review records
    @Test
    void testGetAllReviews() {

        //Add test variables to a List
        List<Review> expectedReviewList = new ArrayList<Review>();

        Long secondRecordId = 2L;
        Review expectedReviewTwo = new Review(secondRecordId, book, user, 4.5, "Pretty good!");
        ReviewDTO expectedReviewTwoDTO = new ReviewDTO(expectedReviewTwo);

        expectedReviewList.add(expectedReview);
        expectedReviewList.add(expectedReviewTwo);

        //Mock reviewRepository and reviewMapper behavior
        when(reviewRepository.findAll()).thenReturn(expectedReviewList);
        when(reviewMapper.convertToDTO(expectedReview)).thenReturn(expectedReviewDTO);
        when(reviewMapper.convertToDTO(expectedReviewTwo)).thenReturn(expectedReviewTwoDTO);

        //Call the method being tested
        List<ReviewDTO> actualReviewList = reviewService.getAllReviews();

        //Assert a List<ReviewDTO> was returned
        assertNotNull(actualReviewList);

        //Assert the actual ReviewDTO data matches the expected data
        assertEquals(expectedReviewList.get(0).getId(), actualReviewList.get(0).getId());
        assertEquals(expectedReviewList.get(0).getRating(), actualReviewList.get(0).getRating());

        assertEquals(expectedReviewList.get(1).getId(), actualReviewList.get(1).getId());
        assertEquals(expectedReviewList.get(1).getRating(), actualReviewList.get(1).getRating());

        //Verify the method being tested was called
        verify(reviewRepository, times(1)).findAll();

    }

    //Test creating a Review record
    @Test
    void testCreateReview() {


        ReviewDTO createReviewDTO = new ReviewDTO();
        createReviewDTO.setBookId(bookDTO.getId());
        createReviewDTO.setUserId(userDTO.getId());
        createReviewDTO.setRating(2.5);
        createReviewDTO.setReviewText("It's just okay.");

        //Mock reviewRepository and reviewMapper behavior
        when(reviewRepository.save(any(Review.class))).thenReturn(expectedReview);
        when(reviewMapper.convertToEntity(any(ReviewDTO.class))).thenReturn(expectedReview);
        when(reviewMapper.convertToDTO(any(Review.class))).thenReturn(expectedReviewDTO);

        //Call the method being tested
        ReviewDTO actualReviewDTO = reviewService.createReview(createReviewDTO);

        //Assert the actual ReviewDTO data matches the expected ReviewDTO data
        assertEquals(expectedReviewDTO.getId(), actualReviewDTO.getId());
        assertEquals(expectedReviewDTO.getRating(), actualReviewDTO.getRating());

        //Verify the method being tested was called
        verify(reviewRepository, times(1)).save(any(Review.class));

    }

    @Test
    void testUpdateReview() {

        //Set updated data
        Review updatedReview = new Review(recordId, book, user, 1.0, "Didn't like it.");
        
        ReviewDTO expectedUpdatedReviewDTO = new ReviewDTO();
        expectedUpdatedReviewDTO.setBookId(bookDTO.getId());
        expectedUpdatedReviewDTO.setUserId(userDTO.getId());
        expectedUpdatedReviewDTO.setId(recordId);
        expectedUpdatedReviewDTO.setRating(1.0);
        expectedUpdatedReviewDTO.setReviewText("Didn't like it.");

        //Mock reviewRepository behavior
        when(reviewRepository.findById(recordId)).thenReturn(expectedReviewOptional);
        when(reviewRepository.save(any(Review.class))).thenReturn(updatedReview);
        when(reviewMapper.convertToDTO(updatedReview)).thenReturn(expectedUpdatedReviewDTO);

        //Call the method being tested
        ReviewDTO actualUpdatedReviewDTO = reviewService.updateReview(recordId, expectedUpdatedReviewDTO);

        //Assert the expected data matches the actual data
        assertEquals(expectedUpdatedReviewDTO.getId(), actualUpdatedReviewDTO.getId());
        assertEquals(expectedUpdatedReviewDTO.getReviewText(), actualUpdatedReviewDTO.getReviewText());

        //Verify the method being tested was called
        verify(reviewRepository, times(1)).save(any(Review.class));

    }

    //Test deleting a Review record
    @Test
    void testDeleteReview() {

        //Mock reviewRepository behavior
        when(reviewRepository.findById(recordId)).thenReturn(expectedReviewOptional);
        
        //Call the method being tested
        reviewService.deleteReview(recordId);

        //Verify the method being tested was called
        verify(reviewRepository, times(1)).delete(expectedReview);
    }
}
