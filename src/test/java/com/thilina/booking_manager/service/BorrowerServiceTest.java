package com.thilina.booking_manager.service;

import com.thilina.booking_manager.dto.BookDto;
import com.thilina.booking_manager.dto.BorrowerDto;
import com.thilina.booking_manager.entity.Book;
import com.thilina.booking_manager.entity.BookRecord;
import com.thilina.booking_manager.entity.Borrower;
import com.thilina.booking_manager.exception.*;
import com.thilina.booking_manager.repository.BookRecordRepository;
import com.thilina.booking_manager.repository.BookRepository;
import com.thilina.booking_manager.repository.BorrowerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BorrowerServiceTest {

    @Mock
    private BorrowerRepository borrowerRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookRecordRepository recordRepository;

    @InjectMocks
    private BorrowerService borrowerService;

    private Borrower borrower;
    private BorrowerDto borrowerDto;
    private Book book;
    private BookDto bookDto;
    private BookRecord bookRecord;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize sample data dynamically
        borrowerDto = new BorrowerDto();
        borrowerDto.setId("1");
        borrowerDto.setName("Thilina");

        borrower = new Borrower();
        borrower.setId("1");
        borrower.setName("Thilina");

        bookDto = new BookDto();
        bookDto.setId("1");
        bookDto.setTitle("Spring in Action");
        bookDto.setAuthor("Craig Walls");

        book = new Book();
        book.setId("1");
        book.setTitle("Spring in Action");
        book.setAuthor("Craig Walls");

        bookRecord = new BookRecord();
        bookRecord.setBookId(book);
        bookRecord.setBorrowerId(borrower);
        bookRecord.setBookBorrowDate(Timestamp.from(Instant.now()).toString());
    }

    @Test
    void registerBorrower_success() {
        // Arrange
        when(borrowerRepository.save(any(Borrower.class))).thenReturn(borrower);

        // Act
        BorrowerDto result = borrowerService.registerBorrower(borrowerDto);

        // Assert
        assertNotNull(result);
        assertEquals(borrowerDto.getId(), result.getId());
        assertEquals(borrowerDto.getName(), result.getName());
        verify(borrowerRepository, times(1)).save(any(Borrower.class));
    }

    @Test
    void registerBorrower_failure() {
        // Arrange
        when(borrowerRepository.save(any(Borrower.class))).thenThrow(new RuntimeException("Failed to save borrower"));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            borrowerService.registerBorrower(borrowerDto);
        });

        assertEquals("Failed to save borrower", exception.getMessage());
        verify(borrowerRepository, times(1)).save(any(Borrower.class));
    }

    @Test
    void borrowBook_success() {
        // Arrange
        when(borrowerRepository.findById(borrower.getId())).thenReturn(Optional.of(borrower));
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(recordRepository.findByBookIdAndBookBorrowDateIsNotNullAndBookReturnDateIsNull(any(Book.class)))
                .thenReturn(Optional.empty());
        when(recordRepository.save(any(BookRecord.class))).thenReturn(bookRecord);

        // Act
        BookDto result = borrowerService.borrowBook(borrower.getId(), book.getId());

        // Assert
        assertNotNull(result);
        assertEquals(bookDto.getId(), result.getId());
        assertEquals(bookDto.getTitle(), result.getTitle());
        assertEquals(bookDto.getAuthor(), result.getAuthor());
        verify(borrowerRepository, times(1)).findById(borrower.getId());
        verify(bookRepository, times(1)).findById(book.getId());
        verify(recordRepository, times(1)).findByBookIdAndBookBorrowDateIsNotNullAndBookReturnDateIsNull(any(Book.class));
        verify(recordRepository, times(1)).save(any(BookRecord.class));
    }

    @Test
    void borrowBook_borrowerNotFound() {
        // Arrange
        when(borrowerRepository.findById(borrower.getId())).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(NotfoundException.class, () -> {
            borrowerService.borrowBook(borrower.getId(), book.getId());
        });

        assertEquals("Borrower not found", exception.getMessage());
        verify(borrowerRepository, times(1)).findById(borrower.getId());
    }

    @Test
    void borrowBook_bookNotFound() {
        // Arrange
        when(borrowerRepository.findById(borrower.getId())).thenReturn(Optional.of(borrower));
        when(bookRepository.findById(book.getId())).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(NotfoundException.class, () -> {
            borrowerService.borrowBook(borrower.getId(), book.getId());
        });

        assertEquals("Book not found", exception.getMessage());
        verify(borrowerRepository, times(1)).findById(borrower.getId());
        verify(bookRepository, times(1)).findById(book.getId());
    }

    @Test
    void borrowBook_alreadyBorrowed() {
        // Arrange
        when(borrowerRepository.findById(borrower.getId())).thenReturn(Optional.of(borrower));
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(recordRepository.findByBookIdAndBookBorrowDateIsNotNullAndBookReturnDateIsNull(any(Book.class)))
                .thenReturn(Optional.of(bookRecord));

        // Act & Assert
        Exception exception = assertThrows(BookNotReturnedException.class, () -> {
            borrowerService.borrowBook(borrower.getId(), book.getId());
        });

        assertEquals("Book hasn't been returned yet", exception.getMessage());
        verify(borrowerRepository, times(1)).findById(borrower.getId());
        verify(bookRepository, times(1)).findById(book.getId());
        verify(recordRepository, times(1)).findByBookIdAndBookBorrowDateIsNotNullAndBookReturnDateIsNull(any(Book.class));
    }

    @Test
    void returnBook_success() {
        // Arrange
        when(borrowerRepository.findById(borrower.getId())).thenReturn(Optional.of(borrower));
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(recordRepository.findByBookIdAndBookBorrowDateIsNotNullAndBookReturnDateIsNull(any(Book.class)))
                .thenReturn(Optional.of(bookRecord));
        when(recordRepository.save(any(BookRecord.class))).thenReturn(bookRecord);

        // Act
        BookDto result = borrowerService.returnBook(borrower.getId(), book.getId());

        // Assert
        assertNotNull(result);
        assertEquals(bookDto.getId(), result.getId());
        assertEquals(bookDto.getTitle(), result.getTitle());
        assertEquals(bookDto.getAuthor(), result.getAuthor());
        verify(borrowerRepository, times(1)).findById(borrower.getId());
        verify(bookRepository, times(1)).findById(book.getId());
        verify(recordRepository, times(1)).findByBookIdAndBookBorrowDateIsNotNullAndBookReturnDateIsNull(any(Book.class));
        verify(recordRepository, times(1)).save(any(BookRecord.class));
    }

    @Test
    void returnBook_borrowerNotFound() {
        // Arrange
        when(borrowerRepository.findById(borrower.getId())).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(NotfoundException.class, () -> {
            borrowerService.returnBook(borrower.getId(), book.getId());
        });

        assertEquals("Borrower not found", exception.getMessage());
        verify(borrowerRepository, times(1)).findById(borrower.getId());
    }

    @Test
    void returnBook_bookNotFound() {
        // Arrange
        when(borrowerRepository.findById(borrower.getId())).thenReturn(Optional.of(borrower));
        when(bookRepository.findById(book.getId())).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(NotfoundException.class, () -> {
            borrowerService.returnBook(borrower.getId(), book.getId());
        });

        assertEquals("Book not found", exception.getMessage());
        verify(borrowerRepository, times(1)).findById(borrower.getId());
        verify(bookRepository, times(1)).findById(book.getId());
    }


    @Test
    void returnBook_alreadyReturned() {
        // Arrange
        bookRecord.setBookReturnDate(Timestamp.from(Instant.now()).toString()); // Book has already been returned

        when(borrowerRepository.findById(borrower.getId())).thenReturn(Optional.of(borrower));
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(recordRepository.findByBookIdAndBookBorrowDateIsNotNullAndBookReturnDateIsNull(any(Book.class)))
                .thenReturn(Optional.of(bookRecord));

        // Act & Assert
        Exception exception = assertThrows(BookNotReturnedException.class, () -> {
            borrowerService.returnBook(borrower.getId(), book.getId());
        });

        assertEquals("Book has been returned already", exception.getMessage());
        verify(borrowerRepository, times(1)).findById(borrower.getId());
        verify(bookRepository, times(1)).findById(book.getId());
        verify(recordRepository, times(1)).findByBookIdAndBookBorrowDateIsNotNullAndBookReturnDateIsNull(any(Book.class));
    }
}
