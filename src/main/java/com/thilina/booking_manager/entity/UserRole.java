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
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user_roles")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User userId;

    @Column(name = "role",nullable = false)
    private String roleName;

    @CreationTimestamp(source = SourceType.DB)
    @Column(name = "created_time",nullable = false)
    private String createdTime;
}
