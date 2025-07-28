package com.gooderreads.gooder_reads.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.gooderreads.gooder_reads.dto.BookDTO;
import com.gooderreads.gooder_reads.entity.Book;
import com.gooderreads.gooder_reads.mapper.BookMapper;
import com.gooderreads.gooder_reads.repository.BookRepository;

import jakarta.transaction.Transactional;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;
    @Autowired
    BookMapper bookMapper;

    
    @Transactional
    public List<BookDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();

        return books.stream()
            .map(book -> bookMapper.convertToDTO(book))
            .collect(Collectors.toList());
    }

    @Transactional
    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found."));
        return bookMapper.convertToDTO(book);
    }

}
