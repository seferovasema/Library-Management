package com.sema.librarymanagment.serviceImpl;

import com.sema.librarymanagment.dto.request.BookRequestDto;
import com.sema.librarymanagment.dto.response.BookResponseDto;
import com.sema.librarymanagment.dto.response.PageResponseDto;
import com.sema.librarymanagment.entity.Author;
import com.sema.librarymanagment.entity.Book;
import com.sema.librarymanagment.entity.Member;
import com.sema.librarymanagment.exception.ResourceNotFoundException;
import com.sema.librarymanagment.mapper.BookMapper;
import com.sema.librarymanagment.repository.AuthorRepository;
import com.sema.librarymanagment.repository.BookRepository;
import com.sema.librarymanagment.repository.MemberRepository;
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

import java.math.BigDecimal;
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
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void create_ShouldReturnBookResponseDto() {
        BookRequestDto request = new BookRequestDto(
                "Clean Code",
                BigDecimal.valueOf(50),
                1L,
                1L);
        Author author = new Author(1L,
                "Robert Martin",
                "martin@gmail.com");
        Member member = new Member(1L,
                "Leyla",
                "leyla@gmail.com",
                "0500000000");

        Book book = new Book();
        book.setTitle("Clean Code");
        book.setPrice(BigDecimal.valueOf(50));

        BookResponseDto response = new BookResponseDto(
                1L,
                "Clean Code",
                BigDecimal.valueOf(50),
                "Robert Martin",
                "Leyla");

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(bookMapper.toEntity(request)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(response);

        BookResponseDto result = bookService.create(request);

        assertEquals("Clean Code", result.getTitle());
        assertEquals(BigDecimal.valueOf(50), result.getPrice());

        verify(bookRepository).save(book);
        verify(bookMapper).toDto(book);
    }

    @Test
    void create_ShouldThrowException_WhenAuthorNotFound() {

        BookRequestDto request = new BookRequestDto(
                "Clean Code",
                BigDecimal.valueOf(50),
                1L,
                1L
        );


        when(authorRepository.findById(1L))
                .thenReturn(Optional.empty());


        assertThrows(ResourceNotFoundException.class,
                () -> bookService.create(request));


        verify(authorRepository)
                .findById(1L);


        verify(memberRepository, never())
                .findById(any());


        verify(bookRepository, never())
                .save(any());
    }

    @Test
    void create_ShouldThrowException_WhenMemberNotFound() {
        BookRequestDto request = new BookRequestDto("Clean Code",
                BigDecimal.valueOf(50),
                1L,
                1L);
        Author author = new Author(1L,
                "Robert Martin",
                "martin@gmail.com");

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(memberRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> bookService.create(request));

        verify(memberRepository).findById(1L);
        verifyNoInteractions(bookRepository);
    }

    @Test
    void findById_ShouldReturnBookResponseDto() {
        Long id = 1L;
        Book book = new Book();
        book.setId(id);
        book.setTitle("Clean Code");

        BookResponseDto response = new BookResponseDto(
                1L, "Clean Code", BigDecimal.valueOf(50),
                "Robert Martin",
                "Leyla");

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(response);

        BookResponseDto result = bookService.findById(id);

        assertEquals("Clean Code", result.getTitle());

        verify(bookRepository).findById(id);
        verify(bookMapper).toDto(book);
    }

    @Test
    void findById_ShouldThrowException_WhenBookNotFound() {
        Long id = 99L;

        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> bookService.findById(id));

        verify(bookRepository).findById(id);
        verifyNoInteractions(bookMapper);
    }

    @Test
    void update_ShouldReturnUpdatedBook() {
        Long id = 1L;
        BookRequestDto request = new BookRequestDto("Clean Architecture",
                BigDecimal.valueOf(70),
                1L,
                1L);
        Book book = new Book();
        Author author = new Author(1L,
                "Robert Martin",
                "martin@gmail.com");
        Member member = new Member(1L,
                "Leyla",
                "leyla@gmail.com",
                "0500000000");

        BookResponseDto response = new BookResponseDto(
                1L,
                "Clean Architecture",
                BigDecimal.valueOf(70),
                "Robert Martin",
                "Leyla");

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(response);

        BookResponseDto result = bookService.update(id, request);

        assertEquals("Clean Architecture", book.getTitle());
        assertEquals(BigDecimal.valueOf(70), book.getPrice());
        assertEquals("Clean Architecture", result.getTitle());

        verify(bookRepository).save(book);
        verify(bookMapper).toDto(book);
    }

    @Test
    void update_ShouldThrowException_WhenBookNotFound() {
        Long id = 99L;
        BookRequestDto request = new BookRequestDto(
                "Clean Architecture",
                BigDecimal.valueOf(70),
                1L,
                1L);

        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> bookService.update(id, request));

        verifyNoInteractions(authorRepository, memberRepository, bookMapper);
    }

    @Test
    void delete_ShouldDeleteBook() {
        Long id = 1L;
        Book book = new Book();
        book.setId(id);

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        bookService.delete(id);

        verify(bookRepository).findById(id);
        verify(bookRepository).delete(book);
    }

    @Test
    void delete_ShouldThrowException_WhenBookNotFound() {
        Long id = 99L;

        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> bookService.delete(id));

        verify(bookRepository, never()).delete(any());
    }

    @Test
    void getAll_ShouldReturnPageResponseDto() {
        Pageable pageable = PageRequest.of(0, 10);
        Book book = new Book();
        book.setTitle("Clean Code");

        BookResponseDto response = new BookResponseDto(
                1L,
                "Clean Code",
                BigDecimal.valueOf(50),
                "Robert Martin",
                "Leyla");

        Page<Book> page = new PageImpl<>(List.of(book));

        when(bookRepository.findAll(pageable)).thenReturn(page);
        when(bookMapper.toDto(book)).thenReturn(response);

        PageResponseDto<BookResponseDto> result = bookService.getAll(pageable);

        assertEquals(1, result.getContent().size());
        assertEquals("Clean Code", result.getContent().get(0).getTitle());

        verify(bookRepository).findAll(pageable);
        verify(bookMapper).toDto(book);
    }
}
