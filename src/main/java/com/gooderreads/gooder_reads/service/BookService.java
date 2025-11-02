package com.gooderreads.gooder_reads.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.gooderreads.gooder_reads.dto.BookDTO;
import com.gooderreads.gooder_reads.entity.Book;
import com.gooderreads.gooder_reads.exception.ResourceNotFoundException;
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

        List<BookDTO> bookDTOs = books.stream()
            .map(book -> bookMapper.convertToDTO(book))
            .collect(Collectors.toList());

        return bookDTOs;

    }

    @Transactional
    public BookDTO getBookById(Long id) {

        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        return bookMapper.convertToDTO(book);

    }

    @Transactional
    public BookDTO createBook(BookDTO book) {

        Book savedBook = bookMapper.convertToEntity(book);
        savedBook = bookRepository.save(savedBook);

        BookDTO savedBookDTO = bookMapper.convertToDTO(savedBook);

        return savedBookDTO;

    }

    @Transactional
    public void deleteBook(Long id) {

        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        bookRepository.delete(book);

    }

    @Transactional
    public BookDTO updateBook(Long id, BookDTO updatedBook) {

        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        book.setTitle(updatedBook.getTitle());
        book.setAuthorId(updatedBook.getAuthorId());

        Book updatedSavedBook = bookRepository.save(book);

        BookDTO updatedBookDTO = bookMapper.convertToDTO(updatedSavedBook);
        return updatedBookDTO;
        
    }

}
