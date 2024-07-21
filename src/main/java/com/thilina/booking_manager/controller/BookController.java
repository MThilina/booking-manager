package com.thilina.booking_manager.controller;


import com.thilina.booking_manager.dto.BookDto;
import com.thilina.booking_manager.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/library")
public class BookController {

    @Autowired
    private BookService bookService;

    @Operation(summary = "Register a new book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @RequestMapping(value = "/books", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDto> registerBook(@Valid @RequestBody BookDto bookDTO) {
        BookDto createdBook = bookService.registerBook(bookDTO);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of all books"),
    })
    @RequestMapping(value = "/books", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<BookDto> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
}