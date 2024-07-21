package com.thilina.booking_manager.entity;

import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;

@Getter
@Setter
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

    @CreationTimestamp(source = SourceType.DB)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "borrow_date",nullable = false)
    private String bookBorrowDate;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "return_date")
    private String bookReturnDate;
}
