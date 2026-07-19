package com.sema.librarymanagment.mapper;

import com.sema.librarymanagment.dto.request.AuthorRequestDto;
import com.sema.librarymanagment.dto.response.AuthorResponseDto;
import com.sema.librarymanagment.entity.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = BookMapper.class)
public interface AuthorMapper {

    AuthorResponseDto toDto(Author author);

    Author toEntity(AuthorRequestDto dto);
}
