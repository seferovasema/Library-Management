package com.sema.librarymanagment.service.impl;

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
import com.sema.librarymanagment.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final AuthorRepository authorRepository;
    private final MemberRepository memberRepository;

    @Override
    public BookResponseDto create(BookRequestDto dto) {
        Book book = bookMapper.toEntity(dto);
        Author author = authorRepository
                .findById(dto.getAuthorId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Author not found"));
        Member member = memberRepository
                .findById(dto.getMemberId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Member not found"));

        book.setAuthor(author);
        book.setMember(member);

        Book bookSaved = bookRepository.save(book);
        return bookMapper.toDto(bookSaved);
    }

    @Override
    public PageResponseDto<BookResponseDto> getAll(Pageable pageable) {
        Page<Book> books = bookRepository.findAll(pageable);

        Page<BookResponseDto> dtoPage = books.map(bookMapper::toDto);

        return PageResponseDto.of(dtoPage);
    }

    @Override
    public BookResponseDto findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Book not found"));
        return bookMapper.toDto(book);
    }

    @Override
    public List<BookResponseDto> getBooksByAuthor(Long authorId) {
        List<Book> books = bookRepository.findByAuthorId(authorId);
        return books
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public List<BookResponseDto> getBooksByMember(Long memberId) {
        List<Book> books = bookRepository.findByMemberId(memberId);
        return books
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookResponseDto update(Long id, BookRequestDto dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Book not found"));
        Author author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Author not found"));

        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Member not found"));

        book.setTitle(dto.getTitle());
        book.setPrice(dto.getPrice());
        book.setAuthor(author);
        book.setMember(member);
        Book updatedBook = bookRepository.save(book);
        return bookMapper.toDto(updatedBook);
    }

    @Override
    public void delete(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Book not found"));

        bookRepository.delete(book);
    }
}
