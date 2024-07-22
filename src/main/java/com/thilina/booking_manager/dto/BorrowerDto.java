package com.thilina.booking_manager.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BorrowerDto {
    private String id;
    @NotNull @NotEmpty
    private String name;
    @NotNull @NotEmpty
    private String email;
}
