package com.sema.librarymanagment.serviceImpl;

import com.sema.librarymanagment.dto.request.BookRequestDto;
import com.sema.librarymanagment.dto.response.BookResponseDto;
import com.sema.librarymanagment.dto.response.PageResponseDto;
import com.sema.librarymanagment.entity.Author;
import com.sema.librarymanagment.entity.Book;
import com.sema.librarymanagment.entity.Category;
import com.sema.librarymanagment.exception.ResourceNotFoundException;
import com.sema.librarymanagment.mapper.BookMapper;
import com.sema.librarymanagment.repository.AuthorRepository;
import com.sema.librarymanagment.repository.BookRepository;
import com.sema.librarymanagment.repository.CategoryRepository;
import com.sema.librarymanagment.service.impl.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void create_ShouldReturnBookResponseDto() {
        BookRequestDto request = new BookRequestDto("Clean Code", BigDecimal.valueOf(50), 1L, List.of(1L));
        Author author = new Author();
        Category category = new Category();
        Book book = new Book();
        Book savedBook = new Book();
        BookResponseDto response = new BookResponseDto(1L, "Clean Code", BigDecimal.valueOf(50), "Robert Martin", List.of("Programming"));

        when(bookMapper.toEntity(request)).thenReturn(book);
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(categoryRepository.findAllById(List.of(1L))).thenReturn(List.of(category));
        when(bookRepository.save(book)).thenReturn(savedBook);
        when(bookMapper.toDto(savedBook)).thenReturn(response);

        BookResponseDto result = bookService.create(request);

        assertNotNull(result);
        assertEquals("Clean Code", result.getTitle());
        assertEquals(BigDecimal.valueOf(50), result.getPrice());

        verify(bookMapper).toEntity(request);
        verify(authorRepository).findById(1L);
        verify(categoryRepository).findAllById(List.of(1L));
        verify(bookRepository).save(book);
        verify(bookMapper).toDto(savedBook);
    }

    @Test
    void create_ShouldThrowException_WhenAuthorNotFound() {
        BookRequestDto request = new BookRequestDto("Clean Code", BigDecimal.valueOf(50), 1L, List.of(1L));
        Book book = new Book();

        when(bookMapper.toEntity(request)).thenReturn(book);
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.create(request));

        verify(authorRepository).findById(1L);
        verify(categoryRepository, never()).findAllById(any());
        verify(bookRepository, never()).save(any());
    }

    @Test
    void findById_ShouldReturnBookResponseDto() {
        Long id = 1L;
        Book book = new Book();
        BookResponseDto response = new BookResponseDto(id, "Clean Code", BigDecimal.valueOf(50), "Robert Martin", List.of("Programming"));

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(response);

        BookResponseDto result = bookService.findById(id);

        assertNotNull(result);
        assertEquals("Clean Code", result.getTitle());

        verify(bookRepository).findById(id);
        verify(bookMapper).toDto(book);
    }

    @Test
    void findById_ShouldThrowException_WhenBookNotFound() {
        Long id = 99L;

        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.findById(id));

        verify(bookRepository).findById(id);
        verifyNoInteractions(bookMapper);
    }

    @Test
    void getBooksByAuthor_ShouldReturnListOfBookResponseDto() {
        Long authorId = 1L;
        Book book = new Book();
        BookResponseDto response = new BookResponseDto(1L, "Clean Code", BigDecimal.valueOf(50), "Robert Martin", List.of("Programming"));

        when(bookRepository.findByAuthorId(authorId)).thenReturn(List.of(book));
        when(bookMapper.toDto(book)).thenReturn(response);

        List<BookResponseDto> result = bookService.getBooksByAuthor(authorId);

        assertEquals(1, result.size());
        assertEquals("Clean Code", result.get(0).getTitle());

        verify(bookRepository).findByAuthorId(authorId);
        verify(bookMapper).toDto(book);
    }

    @Test
    void update_ShouldReturnUpdatedBookResponseDto() {
        Long id = 1L;
        BookRequestDto request = new BookRequestDto("Clean Architecture", BigDecimal.valueOf(70), 1L, List.of(1L));
        Book book = new Book();
        Author author = new Author();
        Category category = new Category();
        Book updatedBook = new Book();
        BookResponseDto response = new BookResponseDto(id, "Clean Architecture", BigDecimal.valueOf(70), "Robert Martin", List.of("Programming"));

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(categoryRepository.findAllById(List.of(1L))).thenReturn(List.of(category));
        when(bookRepository.save(book)).thenReturn(updatedBook);
        when(bookMapper.toDto(updatedBook)).thenReturn(response);

        BookResponseDto result = bookService.update(id, request);

        assertNotNull(result);
        assertEquals("Clean Architecture", result.getTitle());

        verify(bookRepository).findById(id);
        verify(authorRepository).findById(1L);
        verify(categoryRepository).findAllById(List.of(1L));
        verify(bookRepository).save(book);
        verify(bookMapper).toDto(updatedBook);
    }

    @Test
    void update_ShouldThrowException_WhenBookNotFound() {
        Long id = 99L;
        BookRequestDto request = new BookRequestDto("Clean Architecture", BigDecimal.valueOf(70), 1L, List.of(1L));

        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.update(id, request));

        verify(bookRepository).findById(id);
        verifyNoInteractions(authorRepository, categoryRepository, bookMapper);
    }

    @Test
    void delete_ShouldDeleteBook() {
        Long id = 1L;
        Book book = new Book();

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        bookService.delete(id);

        verify(bookRepository).findById(id);
        verify(bookRepository).delete((Book) book);
    }

    @Test
    void delete_ShouldThrowException_WhenBookNotFound() {
        Long id = 99L;

        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.delete(id));

        verify(bookRepository).findById(id);
        verify(bookRepository, never()).delete(any(Book.class));
    }

    @Test
    void getAll_ShouldReturnPageResponseDto() {
        Pageable pageable = PageRequest.of(0, 10);
        Book book = new Book();
        BookResponseDto response = new BookResponseDto(1L, "Clean Code", BigDecimal.valueOf(50), "Robert Martin", List.of("Programming"));
        Page<Book> page = new PageImpl<>(List.of(book));

        when(bookRepository.findAll(pageable)).thenReturn(page);
        when(bookMapper.toDto(book)).thenReturn(response);

        PageResponseDto<BookResponseDto> result = bookService.getAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Clean Code", result.getContent().get(0).getTitle());

        verify(bookRepository).findAll(pageable);
        verify(bookMapper).toDto(book);
    }

    @Test
    void searchBooks_ShouldReturnPageResponseDto() {
        Pageable pageable = PageRequest.of(0, 10);
        Book book = new Book();
        BookResponseDto response = new BookResponseDto(1L, "Clean Code", BigDecimal.valueOf(50), "Robert Martin", List.of("Programming"));
        Page<Book> page = new PageImpl<>(List.of(book));

        when(bookRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);
        when(bookMapper.toDto(book)).thenReturn(response);

        PageResponseDto<BookResponseDto> result = bookService.searchBooks("Clean", "Martin", "Programming", pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());

        verify(bookRepository).findAll(any(Specification.class), any(Pageable.class));
        verify(bookMapper).toDto(book);
    }
}