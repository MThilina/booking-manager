package com.thilina.booking_manager.repository;

import com.thilina.booking_manager.entity.User;
import com.thilina.booking_manager.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, String> {
    Optional<UserRole> findByUserId(User user);
}