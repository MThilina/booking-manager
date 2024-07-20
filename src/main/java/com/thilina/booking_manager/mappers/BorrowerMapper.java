package com.thilina.booking_manager.mappers;


import com.thilina.booking_manager.dto.BorrowerDto;
import com.thilina.booking_manager.entity.Borrower;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BorrowerMapper {
    BorrowerMapper INSTANCE = Mappers.getMapper(BorrowerMapper.class);

    BorrowerDto toDTO(Borrower borrower);

    Borrower toEntity(BorrowerDto borrowerDTO);
}