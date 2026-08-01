package com.sema.librarymanagment.mapper;

import com.sema.librarymanagment.dto.request.BookRequestDto;
import com.sema.librarymanagment.dto.response.BookResponseDto;
import com.sema.librarymanagment.entity.Book;
import com.sema.librarymanagment.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {
    @Mapping(source = "author.name", target = "authorName")
    @Mapping(source = "categories", target = "categories")
    BookResponseDto toDto(Book book);

    Book toEntity(BookRequestDto dto);

    default List<String> map(List<Category> categories) {
        return categories.stream()
                .map(Category::getName)
                .toList();
}}
