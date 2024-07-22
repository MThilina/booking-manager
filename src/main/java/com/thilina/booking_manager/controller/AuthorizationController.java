package com.thilina.booking_manager.controller;

import com.thilina.booking_manager.dto.AuthTokenDto;
import com.thilina.booking_manager.util.JwtTokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthorizationController {

    private final JwtTokenUtil tokenUtil;

    public AuthorizationController(JwtTokenUtil tokenUtil) {
        this.tokenUtil = tokenUtil;
    }

    @GetMapping("/token")
    public ResponseEntity<AuthTokenDto> generateToken(Authentication authentication) {
        // only serve authenticated user based on spring security filter
        if (authentication.isAuthenticated()) {
            String username = authentication.getName();
            String token = tokenUtil.generateToken(username);
            String expiryDate = tokenUtil.getTokenExpiryDate();
            AuthTokenDto tokenDTO = new AuthTokenDto(token, HttpStatus.OK.value(), "Created", expiryDate);
            return ResponseEntity.ok(tokenDTO);
        } else {
            AuthTokenDto tokenDTO = new AuthTokenDto(null, HttpStatus.UNAUTHORIZED.value(), "Authentication Failed", "");
            return new ResponseEntity<>(tokenDTO, HttpStatus.UNAUTHORIZED);
        }
    }

}
