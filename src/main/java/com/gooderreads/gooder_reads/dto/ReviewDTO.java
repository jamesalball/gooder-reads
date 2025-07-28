package com.gooderreads.gooder_reads.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    private Long id;
    private BookDTO book;
    private UserDTO user;
    private double rating;
    private String reviewText;
}
