package com.thilina.booking_manager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

@Getter
@Setter
@Entity(name = "borrower_data")
public class Borrower {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "email",nullable = false)
    private String email;
    @CreationTimestamp(source = SourceType.DB)
    @Column(name = "created_time",nullable = false)
    private String createdTime;
}
