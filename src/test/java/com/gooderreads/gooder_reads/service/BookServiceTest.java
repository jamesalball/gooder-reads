package com.gooderreads.gooder_reads.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gooderreads.gooder_reads.dto.BookDTO;
import com.gooderreads.gooder_reads.entity.Book;
import com.gooderreads.gooder_reads.entity.Review;
import com.gooderreads.gooder_reads.exception.ResourceNotFoundException;
import com.gooderreads.gooder_reads.repository.BookRepository;
import com.gooderreads.gooder_reads.mapper.BookMapper;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookService bookService;

    //Long variables to be used as Ids
    private Long recordId = 1L;
    private Long recordIdTwo = 2L;
    private Long authorId = 1L;

    //Book, Optional<Book> and BookDTO records to be used by the test methods
    private Book expectedBook;
    private Optional<Book> expectedBookOptional;
    private BookDTO expectedBookDTO;

    //expectedBookTwo is only used in testGetAllBooks, so it doesn't need an optional
    private Book expectedBookTwo;
    private BookDTO expectedBookTwoDTO;

    @BeforeEach
    void setUp() {

        //Test Book and Optional<Book> records
        expectedBook = new Book(recordId, "The Stand", authorId, "synopsis", new ArrayList<Review>());
        expectedBookOptional = Optional.of(expectedBook);

        expectedBookTwo = new Book(recordIdTwo, "IT", authorId, "synopsis", new ArrayList<Review>());

        //Test BookDTO record
        expectedBookDTO = new BookDTO(recordId, "The Stand", authorId, "synopsis", new ArrayList<Review>());
        expectedBookTwoDTO = new BookDTO(recordIdTwo, "IT", authorId, "synopsis", new ArrayList<Review>());

    }

    //Test getting a Book by its Id successfully
    @Test
    void testGetBookById_Success() {

        //Mock bookRepository and bookMapper behavior
        when(bookRepository.findById(Long.valueOf(1))).thenReturn(expectedBookOptional);
        when(bookMapper.convertToDTO(expectedBook)).thenReturn(expectedBookDTO);

        //Call the method being tested
        BookDTO actualBook = bookService.getBookById(Long.valueOf(1));

        //Assert a BookDTO was returned
        assertNotNull(actualBook);

        //Assert that the actual BookDTO data matches what's expected
        assertEquals(expectedBook.getId(), actualBook.getId());
        assertEquals(expectedBook.getTitle(), actualBook.getTitle());
        assertEquals(expectedBook.getAuthorId(), actualBook.getAuthorId());

        //Verify the method being tested was called
        verify(bookRepository, times(1)).findById(recordId);
    }

    //Test attempting to get a Book by its Id unsusccessfully. Applicable for getting, updating, or deleting a nonexistent book
    @Test
    void testGetBookById_NotFound() {

        //Mock bookRepository behavior
        when(bookRepository.findById(recordId)).thenReturn(Optional.empty());

        //Trying to find a Book that doesn't exist should throw a ResourceNotFoundException
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> bookService.getBookById(recordId));

        //Assert the expected exception was thrown
        assertEquals("Book not found", thrown.getMessage());

        //Verify the method being tested was called
        verify(bookRepository, times(1)).findById(recordId);
    }

    //Test listing all Book records
    @Test
    void testGetAllBooks() {

        //Add test records to a List
        List<Book> expectedBookList = Arrays.asList(expectedBook, expectedBookTwo);

        //Mock bookRepository and bookMapper behavior
        when(bookRepository.findAll()).thenReturn(expectedBookList);
        when(bookMapper.convertToDTO(expectedBook)).thenReturn(expectedBookDTO);
        when(bookMapper.convertToDTO(expectedBookTwo)).thenReturn(expectedBookTwoDTO);

        //Call the method being tested
        List<BookDTO> actualBookList = bookService.getAllBooks().stream().collect(Collectors.toList());

        //Assert a List<BookDTO> was returned
        assertNotNull(actualBookList);

        //Assert the actual BookDTO data matches the expected Book data
        assertEquals(expectedBookList.get(0).getId(), actualBookList.get(0).getId());
        assertEquals(expectedBookList.get(0).getTitle(), actualBookList.get(0).getTitle());
        assertEquals(expectedBookList.get(0).getAuthorId(), actualBookList.get(0).getAuthorId());

        assertEquals(expectedBookList.get(1).getId(), actualBookList.get(1).getId());
        assertEquals(expectedBookList.get(1).getTitle(), actualBookList.get(1).getTitle());
        assertEquals(expectedBookList.get(1).getAuthorId(), actualBookList.get(1).getAuthorId());

        //Verify the method being tested was called
        verify(bookRepository, times(1)).findAll();

    }

    //Test creating a Book record
    @Test
    void testCreateBook() {

        BookDTO createBookDTO = new BookDTO();
        createBookDTO.setAuthorId(authorId);
        createBookDTO.setTitle("The Stand");

        //Mock bookRepository and bookMapper behavior
        when(bookRepository.save(any(Book.class))).thenReturn(expectedBook);
        when(bookMapper.convertToEntity(any(BookDTO.class))).thenReturn(expectedBook);
        when(bookMapper.convertToDTO(any(Book.class))).thenReturn(expectedBookDTO);

        //Call the method being tested
        BookDTO actualBookDTO = bookService.createBook(createBookDTO);

        //Assert the actual BookDTO data matches the expected BookDTO data
        assertEquals(expectedBookDTO.getId(), actualBookDTO.getId());
        assertEquals(expectedBookDTO.getTitle(), actualBookDTO.getTitle());

        //Verify the method being tested was called
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    //Test deleting a Book record
    @Test
    void testDeleteBook() {

        //Mock bookRepository behavior
        when(bookRepository.findById(recordId)).thenReturn(expectedBookOptional);
        
        //Call the method being tested
        bookService.deleteBook(recordId);

        //Verify the method being tested was called
        verify(bookRepository, times(1)).delete(expectedBook);
    }

    @Test
    //Test updating a Book
    void testUpdateBook() {

        //Set updated data
        Book updatedBook = new Book(recordId, "The Talisman", authorId, "synopsis", new ArrayList<Review>());

        BookDTO expectedUpdatedBookDTO = new BookDTO();
        expectedUpdatedBookDTO.setTitle("The Talisman");
        expectedUpdatedBookDTO.setAuthorId(1L);
        expectedUpdatedBookDTO.setId(recordId);

        //Mock bookRepository behavior
        when(bookRepository.findById(recordId)).thenReturn(expectedBookOptional);
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);
        when(bookMapper.convertToDTO(updatedBook)).thenReturn(expectedUpdatedBookDTO);

        //Call the method being tested
        BookDTO actualUpdatedBookDTO = bookService.updateBook(recordId, expectedUpdatedBookDTO);

        //Assert the expected data matches the actual data
        assertEquals(expectedUpdatedBookDTO.getId(), actualUpdatedBookDTO.getId());
        assertEquals(expectedUpdatedBookDTO.getTitle(), actualUpdatedBookDTO.getTitle());

        //Verify the method being tested was called
        verify(bookRepository, times(1)).save(any(Book.class));
    }
    
}
