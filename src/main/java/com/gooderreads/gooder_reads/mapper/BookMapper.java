package com.gooderreads.gooder_reads.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gooderreads.gooder_reads.dto.BookDTO;
import com.gooderreads.gooder_reads.entity.Book;

import org.modelmapper.ModelMapper;



@Component
public class BookMapper {

    @Autowired
    private ModelMapper modelMapper;

    public BookDTO convertToDTO(Book book) {
        BookDTO bookDTO = modelMapper.map(book, BookDTO.class);
        return bookDTO;
    }

    public Book convertToEntity(BookDTO bookDTO) {
        Book book = modelMapper.map(bookDTO, Book.class);
        return book;
    }
}