package com.gooderreads.gooder_reads.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


import com.gooderreads.gooder_reads.repository.BookRepository;
import com.gooderreads.gooder_reads.entity.Book;


@RestController
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/book")
    public ModelAndView getBookPage(@RequestParam Long id) {
        ModelAndView mav = new ModelAndView();
        Book book = bookRepository.findById(id).get();

        mav.addObject("title", book.getTitle());
        mav.addObject("author", book.getAuthor());

        return mav;
    }

    @GetMapping("/api/book")
    public Book getBookPageJson(@RequestParam Long id) {
        Book book = bookRepository.findById(id).get();
        return book;
    }
    
}
