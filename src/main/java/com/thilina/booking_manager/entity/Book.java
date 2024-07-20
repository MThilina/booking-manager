package com.thilina.booking_manager.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

@Getter
@Setter
@Entity(name = "book_data")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String Id;
    @Column(name = "isbn",nullable = false)
    private String isbn;
    @Column(name = "title",nullable = false)
    private String title;
    @Column(name = "author",nullable = false)
    private String author;
    @CreationTimestamp(source = SourceType.DB)
    @Column(name = "created_time",nullable = false)
    private String createdTime;
}
