package com.sema.librarymanagment.service.impl;

import com.sema.librarymanagment.dto.request.AuthorRequestDto;
import com.sema.librarymanagment.dto.response.AuthorResponseDto;
import com.sema.librarymanagment.dto.response.PageResponseDto;
import com.sema.librarymanagment.entity.Author;
import com.sema.librarymanagment.exception.ResourceNotFoundException;
import com.sema.librarymanagment.mapper.AuthorMapper;
import com.sema.librarymanagment.repository.AuthorRepository;
import com.sema.librarymanagment.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Override
    public AuthorResponseDto createAuthor(AuthorRequestDto dto) {
        Author author = authorMapper.toEntity(dto);

        Author savedAuthor = authorRepository.save(author);

        return authorMapper.toDto(savedAuthor);
    }

    @Override
    public PageResponseDto<AuthorResponseDto> getAll(Pageable pageable) {
        Page<Author> authors = authorRepository.findAll(pageable);

        Page<AuthorResponseDto> dtoPage = authors.map(authorMapper::toDto);

        return PageResponseDto.of(dtoPage);
    }


    @Override
    public AuthorResponseDto findById(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Author not found"));
        return authorMapper.toDto(author);
    }

    @Override
    public AuthorResponseDto update(Long id, AuthorRequestDto dto) {
        Author author = authorRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Author not found"));
        author.setName(dto.getName());
        author.setEmail(dto.getEmail());
        return authorMapper.toDto(author);
    }

    @Override
    public void delete(Long id) {
        Author author = authorRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Author not found"));
        authorRepository.delete(author);
    }
}
