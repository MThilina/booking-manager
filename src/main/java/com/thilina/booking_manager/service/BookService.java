package com.thilina.booking_manager.service;

import com.thilina.booking_manager.dto.BookDto;
import com.thilina.booking_manager.entity.Book;
import com.thilina.booking_manager.mappers.BookMapper;
import com.thilina.booking_manager.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p></p>
 * This service responsible for all the Book related actions
 * Basically this contains mainly the CRUD services
 */
@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookDto registerBook(BookDto bookDTO) {
        Book book = BookMapper.INSTANCE.toEntity(bookDTO);
        book = bookRepository.save(book);
        return BookMapper.INSTANCE.toDTO(book);
    }

    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(BookMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }
}