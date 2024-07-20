package com.thilina.booking_manager.mappers;


import com.thilina.booking_manager.dto.BookDto;
import com.thilina.booking_manager.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    BookDto toDTO(Book book);

    Book toEntity(BookDto bookDTO);
}