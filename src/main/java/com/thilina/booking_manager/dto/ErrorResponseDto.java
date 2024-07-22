package com.thilina.booking_manager.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDto {
    private String message;
    @NotNull @NotEmpty
    private Integer status;
    @NotNull @NotEmpty
    private Instant time;

}
