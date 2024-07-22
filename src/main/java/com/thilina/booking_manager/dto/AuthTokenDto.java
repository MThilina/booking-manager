package com.thilina.booking_manager.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthTokenDto {
    private String token;
    private int status;
    private String message;
    private String expiryDate;
}
