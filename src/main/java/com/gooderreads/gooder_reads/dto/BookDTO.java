package com.gooderreads.gooder_reads.dto;

import java.util.List;

import com.gooderreads.gooder_reads.entity.Review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private Long id;
    private String title;
    private Long authorId;
    private String synopsis;
    private List<Review> reviews;
}
