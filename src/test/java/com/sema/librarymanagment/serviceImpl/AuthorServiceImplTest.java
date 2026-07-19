package com.sema.librarymanagment.serviceImpl;

import com.sema.librarymanagment.dto.request.AuthorRequestDto;
import com.sema.librarymanagment.dto.response.AuthorResponseDto;
import com.sema.librarymanagment.dto.response.PageResponseDto;
import com.sema.librarymanagment.entity.Author;
import com.sema.librarymanagment.exception.ResourceNotFoundException;
import com.sema.librarymanagment.mapper.AuthorMapper;
import com.sema.librarymanagment.repository.AuthorRepository;
import com.sema.librarymanagment.service.impl.AuthorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private AuthorMapper authorMapper;

    @InjectMocks
    private AuthorServiceImpl authorService;

    @Test
    void createAuthor_ShouldReturnAuthorResponseDto() {
        AuthorRequestDto request = new AuthorRequestDto(
                "Sema",
                "sema@gmail.com");
        Author author = new Author(1L,
                "Sema",
                "sema@gmail.com");
        AuthorResponseDto response = new AuthorResponseDto(1L,
                "Sema",
                "sema@gmail.com",
                null);

        when(authorMapper.toEntity(request)).thenReturn(author);
        when(authorRepository.save(author)).thenReturn(author);
        when(authorMapper.toDto(author)).thenReturn(response);

        AuthorResponseDto result = authorService.createAuthor(request);

        assertEquals("Sema", result.getName());
        assertEquals("sema@gmail.com", result.getEmail());

        verify(authorMapper).toEntity(request);
        verify(authorRepository).save(author);
        verify(authorMapper).toDto(author);
    }

    @Test
    void findById_ShouldReturnAuthorResponseDto() {
        Long id = 1L;
        Author author = new Author(1L,
                "Sema",
                "sema@gmail.com");
        AuthorResponseDto response = new AuthorResponseDto(1L,
                "Sema",
                "sema@gmail.com",
                null);

        when(authorRepository.findById(id)).thenReturn(Optional.of(author));
        when(authorMapper.toDto(author)).thenReturn(response);

        AuthorResponseDto result = authorService.findById(id);

        assertEquals("Sema", result.getName());
        assertEquals("sema@gmail.com", result.getEmail());

        verify(authorRepository).findById(id);
        verify(authorMapper).toDto(author);
    }

    @Test
    void findById_ShouldThrowException_WhenAuthorNotFound() {
        Long id = 99L;

        when(authorRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> authorService.findById(id));

        verify(authorRepository).findById(id);
        verifyNoInteractions(authorMapper);
    }

    @Test
    void update_ShouldModifyEntityFields() {
        Long id = 1L;
        AuthorRequestDto request = new AuthorRequestDto("Leyla",
                "leyla@gmail.com");
        Author author = new Author(1L,
                "Sema",
                "sema@gmail.com");
        AuthorResponseDto response = new AuthorResponseDto(1L,
                "Leyla",
                "leyla@gmail.com",
                null);

        when(authorRepository.findById(id)).thenReturn(Optional.of(author));
        when(authorMapper.toDto(author)).thenReturn(response);

        AuthorResponseDto result = authorService.update(id, request);

        assertEquals("Leyla", author.getName());
        assertEquals("leyla@gmail.com", author.getEmail());

        assertEquals("Leyla", result.getName());
        assertEquals("leyla@gmail.com", result.getEmail());

        verify(authorRepository).findById(id);
        verify(authorMapper).toDto(author);
    }

    @Test
    void update_ShouldThrowException_WhenAuthorNotFound() {
        Long id = 99L;
        AuthorRequestDto request = new AuthorRequestDto(
                "Leyla",
                "leyla@gmail.com");

        when(authorRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> authorService.update(id, request));

        verify(authorRepository).findById(id);
        verifyNoInteractions(authorMapper);
    }

    @Test
    void delete_ShouldDeleteAuthor() {
        Long id = 1L;
        Author author = new Author(1L, "Sema",
                "sema@gmail.com");

        when(authorRepository.findById(id)).thenReturn(Optional.of(author));

        authorService.delete(id);

        verify(authorRepository).findById(id);
        verify(authorRepository).delete(author);
    }

    @Test
    void delete_ShouldThrowException_WhenAuthorNotFound() {
        Long id = 99L;

        when(authorRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> authorService.delete(id));

        verify(authorRepository, never()).delete(any());
    }

    @Test
    void getAll_ShouldReturnPageResponseDto() {
        Pageable pageable = PageRequest.of(0, 10);
        Author author = new Author(1L,
                "Leyla",
                "leyla@gmail.com");
        AuthorResponseDto response = new AuthorResponseDto(1L,
                "Leyla",
                "leyla@gmail.com",
                null);
        Page<Author> authorPage = new PageImpl<>(List.of(author));

        when(authorRepository.findAll(pageable)).thenReturn(authorPage);
        when(authorMapper.toDto(author)).thenReturn(response);

        PageResponseDto<AuthorResponseDto> result = authorService.getAll(pageable);

        assertEquals(1, result.getContent().size());
        assertEquals("Leyla", result.getContent().get(0).getName());

        verify(authorRepository).findAll(pageable);
        verify(authorMapper).toDto(author);
    }
}
