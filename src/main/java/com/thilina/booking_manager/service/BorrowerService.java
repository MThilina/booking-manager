package com.thilina.booking_manager.service;


import com.thilina.booking_manager.dto.BookDto;
import com.thilina.booking_manager.dto.BorrowerDto;
import com.thilina.booking_manager.entity.Book;
import com.thilina.booking_manager.entity.BookRecord;
import com.thilina.booking_manager.entity.Borrower;
import com.thilina.booking_manager.exception.*;
import com.thilina.booking_manager.mappers.BookMapper;
import com.thilina.booking_manager.mappers.BorrowerMapper;
import com.thilina.booking_manager.repository.BookRecordRepository;
import com.thilina.booking_manager.repository.BookRepository;
import com.thilina.booking_manager.repository.BorrowerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Service
public class BorrowerService {

    private final BorrowerRepository borrowerRepository;
    private final BookRepository bookRepository;
    private final BookRecordRepository recordRepository;


    public BorrowerService(BorrowerRepository borrowerRepository,
                           BookRepository bookRepository,
                           BookRecordRepository recordRepository) {

        this.borrowerRepository = borrowerRepository;
        this.bookRepository = bookRepository;
        this.recordRepository = recordRepository;
    }

    public BorrowerDto registerBorrower(BorrowerDto borrowerDTO) {
        Borrower borrower = BorrowerMapper.INSTANCE.toEntity(borrowerDTO);
        borrower = borrowerRepository.save(borrower);
        return BorrowerMapper.INSTANCE.toDTO(borrower);
    }

    @Transactional(value = Transactional.TxType.REQUIRED,rollbackOn =Exception.class)
    public BookDto borrowBook(String borrowerId, String bookId) {
        // Validate user and book
        Borrower borrower = borrowerRepository.findById(borrowerId)
                .orElseThrow(() -> new NotfoundException("Borrower not found"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotfoundException("Book not found"));

        Optional<BookRecord> optionalRecord = recordRepository.findByBookIdAndBookBorrowDateIsNotNullAndBookReturnDateIsNull(book);

        BookRecord record;
        if (optionalRecord.isPresent()) {
            record = optionalRecord.get();

            // If the record exists, check the borrow and return dates (execute validations)
            this.validateBookRecord(record);
        } else {
            // Create a new BookRecord if it doesn't exist
            record = this.createNewBookRecord(book, borrower);
        }
        // Set the default return date and save the record
        record.setBookReturnDate(null); // Assuming the book is now being borrowed, so reset return date
        recordRepository.save(record);

        return BookMapper.INSTANCE.toDTO(book);
    }


    @Transactional(value = Transactional.TxType.REQUIRED,rollbackOn =Exception.class)
    public BookDto returnBook(String borrowerId, String bookId) {
        Borrower borrower = borrowerRepository.findById(borrowerId)
                .orElseThrow(() -> new NotfoundException("Borrower not found"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotfoundException("Book not found"));

        Optional<BookRecord> optionalRecord = recordRepository.findByBookIdAndBookBorrowDateIsNotNullAndBookReturnDateIsNull(book);

        if(!optionalRecord.isPresent()){
            // throw exception
            throw new BookRecordNotFoundException("Book distribution details cannot found.");
        }
        BookRecord record;
        record = optionalRecord.get();
        if (record.getBookBorrowDate() != null && record.getBookReturnDate() != null) {
            throw new BookNotReturnedException("Book has been return already ");
        }
        record.setBookReturnDate(Timestamp.from(Instant.now()).toString());
        // else books can be borrowed
        recordRepository.save(record);
        return BookMapper.INSTANCE.toDTO(book);
    }

    /******************************** Private Methods *************************************************/

    private void validateBookRecord(BookRecord record) {
        if (record.getBookBorrowDate() != null && record.getBookReturnDate() == null) {
            throw new BookNotReturnedException("Book hasn't been returned yet");
        }

        if (record.getBookBorrowDate() == null && record.getBookReturnDate() != null) {
            throw new DataCurruptedException("Book return date is corrupted");
        }
    }

    private BookRecord createNewBookRecord(Book book, Borrower borrower) {
        BookRecord record = new BookRecord();
        record.setBookId(book);
        record.setBorrowerId(borrower);
        return record;
    }

}