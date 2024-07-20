package com.thilina.booking_manager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "book_borrow_data")
public class BookRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "borrower_id", referencedColumnName = "id")
    private Borrower borrowerId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book bookId;

    @Column(name = "borrow_date",nullable = false)
    private String bookBorrowDate;

    @Column(name = "return_date",nullable = false)
    private String bookReturnDate;
}
