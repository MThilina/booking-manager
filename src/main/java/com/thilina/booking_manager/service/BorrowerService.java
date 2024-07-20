package com.thilina.booking_manager.service;


import com.thilina.booking_manager.dto.BookDto;
import com.thilina.booking_manager.dto.BorrowerDto;
import com.thilina.booking_manager.entity.Book;
import com.thilina.booking_manager.entity.BookRecord;
import com.thilina.booking_manager.entity.Borrower;
import com.thilina.booking_manager.exception.BadRequestException;
import com.thilina.booking_manager.exception.BookNotReturnedException;
import com.thilina.booking_manager.exception.BookRecordNotFoundException;
import com.thilina.booking_manager.exception.NotfoundException;
import com.thilina.booking_manager.mappers.BookMapper;
import com.thilina.booking_manager.mappers.BorrowerMapper;
import com.thilina.booking_manager.repository.BookRecordRepository;
import com.thilina.booking_manager.repository.BookRepository;
import com.thilina.booking_manager.repository.BorrowerRepository;
import org.springframework.stereotype.Service;

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

    public BookDto borrowBook(String borrowerId, String bookId) {
        // validate user and book
        Book book = CommonValidation(borrowerId, bookId);

        Optional<BookRecord> optionalRecord = recordRepository.findById(book.getId());

        BookRecord record = optionalRecord.orElseThrow(() -> new BookRecordNotFoundException("Book distribution details cannot found."));

        if (record.getBookBorrowDate() != null && record.getBookReturnDate() == null) {
            throw new BookNotReturnedException("Book hasn't been returned yet");
        }else{
            // else books can be borrowed
            recordRepository.save(record);
        }
        return BookMapper.INSTANCE.toDTO(book);
    }


    public BookDto returnBook(String borrowerId, String bookId) {
        Book book = CommonValidation(borrowerId, bookId);

        Optional<BookRecord> optionalRecord = recordRepository.findById(book.getId());

        BookRecord record = optionalRecord.orElseThrow(() -> new BookRecordNotFoundException("Book distribution details cannot found."));

        if (record.getBookBorrowDate() != null && record.getBookReturnDate() != null) {
            throw new BookNotReturnedException("Book has been return already ");
        }else{
            // else books can be borrowed
            record.getBookReturnDate();
            recordRepository.save(record);
        }

        return BookMapper.INSTANCE.toDTO(book);
    }


    private Book CommonValidation(String borrowerId, String bookId) {
        Borrower borrower = borrowerRepository.findById(borrowerId)
                .orElseThrow(() -> new NotfoundException("Borrower not found"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotfoundException("Book not found"));
        return book;
    }
}