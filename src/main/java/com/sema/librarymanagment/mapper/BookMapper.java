package com.sema.librarymanagment.mapper;

import com.sema.librarymanagment.dto.request.BookRequestDto;
import com.sema.librarymanagment.dto.response.BookResponseDto;
import com.sema.librarymanagment.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {
    @Mapping(source = "author.name", target = "authorName")
    @Mapping(source = "member.fullName", target = "memberName")
    BookResponseDto toDto(Book book);

    Book toEntity(BookRequestDto dto);
}
