package com.thilina.booking_manager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "user_data")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name",nullable = false)
    private String userName;

    @Column(name = "password",nullable = false)
    private String password;

    @CreationTimestamp(source = SourceType.DB)
    @Column(name = "created_time",nullable = false)
    private String createdTime;

}
