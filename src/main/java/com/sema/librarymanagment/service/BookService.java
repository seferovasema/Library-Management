package com.sema.librarymanagment.service;

import com.sema.librarymanagment.dto.request.BookRequestDto;
import com.sema.librarymanagment.dto.response.BookResponseDto;
import com.sema.librarymanagment.dto.response.PageResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    BookResponseDto create(BookRequestDto dto);

    PageResponseDto<BookResponseDto> getAll(Pageable pageable);

    BookResponseDto findById(Long id);

    List<BookResponseDto> getBooksByAuthor(Long authorId);

    List<BookResponseDto> getBooksByMember(Long memberId);

    BookResponseDto update(Long id, BookRequestDto dto);

    void delete(Long id);
}


