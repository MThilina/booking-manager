package com.thilina.booking_manager.service;

import com.thilina.booking_manager.dto.BookDto;
import com.thilina.booking_manager.entity.Book;
import com.thilina.booking_manager.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book book;
    private BookDto bookDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize sample data

        book = new Book();
        book.setId("1");
        book.setTitle("Spring in Action");
        book.setAuthor("Craig Walls");

        bookDto = new BookDto();
        bookDto.setId("1");
        bookDto.setTitle("Spring in Action");
        bookDto.setAuthor("Craig Walls");

    }

    @Test
    void registerBook_success() {
        // Arrange
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        // Act
        BookDto result = bookService.registerBook(bookDto);

        // Assert
        assertNotNull(result);
        assertEquals(bookDto.getTitle(), result.getTitle());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void registerBook_failure() {
        // Arrange
        when(bookRepository.save(any(Book.class))).thenThrow(new RuntimeException("Failed to save book"));

        // Act & Assert
        try {
            bookService.registerBook(bookDto);
        } catch (Exception e) {
            assertEquals("Failed to save book", e.getMessage());
        }
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void getAllBooks_success() {
        // Arrange
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book));

        // Act
        List<BookDto> result = bookService.getAllBooks();

        // Assert
        assertEquals(1, result.size());
        assertEquals(book.getTitle(), result.get(0).getTitle());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void getAllBooks_emptyList() {
        // Arrange
        when(bookRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<BookDto> result = bookService.getAllBooks();

        // Assert
        assertEquals(0, result.size());
        verify(bookRepository, times(1)).findAll();
    }
}
