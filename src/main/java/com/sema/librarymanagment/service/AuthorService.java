package com.sema.librarymanagment.service;

import com.sema.librarymanagment.dto.request.AuthorRequestDto;
import com.sema.librarymanagment.dto.response.AuthorResponseDto;
import com.sema.librarymanagment.dto.response.PageResponseDto;
import org.springframework.data.domain.Pageable;


public interface AuthorService {
    AuthorResponseDto createAuthor(AuthorRequestDto dto);

    PageResponseDto<AuthorResponseDto> getAll(Pageable pageable);

    AuthorResponseDto findById(Long id);

    AuthorResponseDto update(Long id, AuthorRequestDto dto);

    void delete(Long id);

}
