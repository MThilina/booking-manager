package com.thilina.booking_manager.service.security;

import com.thilina.booking_manager.entity.User;
import com.thilina.booking_manager.entity.UserRole;
import com.thilina.booking_manager.exception.NotfoundException;
import com.thilina.booking_manager.repository.UserRepository;
import com.thilina.booking_manager.repository.UserRoleRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository;
    private  final UserRoleRepository userRoleRepository;

    public CustomUserDetailsService(UserRepository userRepository,
    UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws NotfoundException {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new NotfoundException("User not found"));

        UserRole role = userRoleRepository.findByUserId(user)
                .orElseThrow(() -> new NotfoundException("User role does not allocated to the user"));

        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(role.getRoleName()))
        );
    }
}
