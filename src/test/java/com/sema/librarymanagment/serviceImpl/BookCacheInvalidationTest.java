package com.sema.librarymanagment.serviceImpl;

import com.sema.librarymanagment.dto.request.BookRequestDto;
import com.sema.librarymanagment.entity.Author;
import com.sema.librarymanagment.entity.Book;
import com.sema.librarymanagment.entity.Category;
import com.sema.librarymanagment.repository.AuthorRepository;
import com.sema.librarymanagment.repository.BookRepository;
import com.sema.librarymanagment.repository.CategoryRepository;
import com.sema.librarymanagment.service.impl.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@Transactional
class BookCacheInvalidationTest {

    @Autowired
    private BookServiceImpl bookService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CacheManager cacheManager;

    @Test
    void update_ShouldEvictBookCaches() {

        Cache bookByIdCache =
                cacheManager.getCache("bookById");

        Cache booksCache =
                cacheManager.getCache("books");

        Cache booksByAuthorCache =
                cacheManager.getCache("booksByAuthor");

        assertNotNull(bookByIdCache);
        assertNotNull(booksCache);
        assertNotNull(booksByAuthorCache);

        String uniqueId = UUID.randomUUID().toString();


        Author author = new Author();
        author.setName("Robert Martin");
        author.setEmail("robert.martin@test.com");
        author = authorRepository.save(author);

        Category category = new Category();
        category.setName("Programming");
        category = categoryRepository.save(category);

        Book book = new Book();
        book.setTitle("Old Title " + uniqueId);
        book.setPrice(BigDecimal.valueOf(50));
        book.setAuthor(author);
        book.setCategories(List.of(category));

        book = bookRepository.save(book);

        Long bookId = book.getId();
        Long authorId = author.getId();
        Long categoryId = category.getId();


        bookByIdCache.put(bookId, "old-book-data");
        booksCache.put("test-key", "old-books-data");
        booksByAuthorCache.put(authorId, "old-author-books");

        BookRequestDto request =
                new BookRequestDto(
                        "New Title",
                        BigDecimal.valueOf(70),
                        authorId,
                        List.of(categoryId)
                );

        bookService.update(bookId, request);

        assertNull(bookByIdCache.get(bookId));
        assertNull(booksCache.get("test-key"));
        assertNull(booksByAuthorCache.get(authorId));
    }
}