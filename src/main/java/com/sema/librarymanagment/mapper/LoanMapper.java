package com.sema.librarymanagment.mapper;

import com.sema.librarymanagment.dto.response.LoanResponseDto;
import com.sema.librarymanagment.entity.Loan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LoanMapper {
    @Mapping(source = "book.title", target = "bookTitle")
    @Mapping(source = "member.fullName", target = "memberName")
    LoanResponseDto toDto(Loan loan);
}
