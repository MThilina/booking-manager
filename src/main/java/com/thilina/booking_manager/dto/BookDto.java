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
public class BookDto {
    private String Id;
    @NotNull @NotEmpty
    private String isbn;
    @NotNull @NotEmpty
    private String title;
    @NotNull @NotEmpty
    private String author;
}
