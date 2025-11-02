package com.gooderreads.gooder_reads.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import com.gooderreads.gooder_reads.entity.Book;

@DataJpaTest
public class BookRepositoryTest {
    
    @Autowired
    BookRepository bookRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    void testSaveNewBook() {
        Book newBook = new Book("The Stand", 1L);
        Book savedBook = bookRepository.save(newBook);
        assertThat(entityManager.find(Book.class, savedBook.getId())).isEqualTo(newBook);
    }

    @Test
    void testUpdateExistingBook() {
        Book book = new Book("The Stand", 1L);
        entityManager.persist(book);
        String newTitle = "The Talisman";
        book.setTitle(newTitle);
        bookRepository.save(book);
        assertThat(entityManager.find(Book.class, book.getId()).getTitle()).isEqualTo(newTitle);
    }

    @Test
    void testFindAllBooks() {
        Book bookA = new Book("The Stand", 1L);
        Book bookB = new Book("IT", 1L);
        entityManager.persist(bookA);
        entityManager.persist(bookB);

        List<Book> bookList = bookRepository.findAll();

        assertThat(entityManager.find(Book.class, bookA.getId())).isEqualTo(bookList.get(0));

    }

    @Test
    void testFindBookById() {
        Book book = new Book("The Stand", 1L);
        entityManager.persist(book);
        Optional<Book> actualBook = bookRepository.findById(book.getId());
        assertThat(actualBook).contains(book);
    }

    @Test
    void testDeleteBook() {
        Book book = new Book("The Stand", 1L);
        entityManager.persist(book);
        bookRepository.delete(book);
        assertThat(entityManager.find(Book.class, book.getId())).isNull();
    }
}
