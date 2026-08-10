package com.sema.librarymanagment.controller;

import com.sema.librarymanagment.dto.request.BookRequestDto;
import com.sema.librarymanagment.dto.response.BookResponseDto;
import com.sema.librarymanagment.dto.response.PageResponseDto;
import com.sema.librarymanagment.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@Tag(
        name = "Book",
        description = "Book management, search and retrieval operations"
)
@SecurityRequirement(name = "Bearer Authentication")
public class BookController {

    private final BookService bookService;

    @Operation(
            summary = "Create book",
            description = "Creates a new book with an author and categories."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Book created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed"),
            @ApiResponse(responseCode = "404", description = "Author or category not found"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<BookResponseDto> create(
            @Valid @RequestBody BookRequestDto dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(bookService.create(dto));
    }

    @Operation(
            summary = "Get all books",
            description = "Returns a paginated and sortable list of books."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Books retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Authentication required")
    })
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping
    public ResponseEntity<PageResponseDto<BookResponseDto>> getAllBooks(
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {

        return ResponseEntity.ok(
                bookService.getAll(pageable)
        );
    }

    @Operation(
            summary = "Get book by ID",
            description = "Returns a specific book by its ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Book found"),
            @ApiResponse(responseCode = "404", description = "Book not found"),
            @ApiResponse(responseCode = "401", description = "Authentication required")
    })
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDto> findById(
            @Parameter(description = "Book ID", example = "1")
            @PathVariable Long id) {

        return ResponseEntity.ok(
                bookService.findById(id)
        );
    }

    @Operation(
            summary = "Get books by author",
            description = "Returns all books written by the specified author."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Books retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Authentication required")
    })
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<BookResponseDto>> getBooksByAuthor(
            @Parameter(description = "Author ID", example = "1")
            @PathVariable Long authorId) {

        return ResponseEntity.ok(
                bookService.getBooksByAuthor(authorId)
        );
    }

    @Operation(
            summary = "Search books",
            description = "Searches books dynamically by title, author name and category."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Search completed successfully"),
            @ApiResponse(responseCode = "401", description = "Authentication required")
    })
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/search")
    public ResponseEntity<PageResponseDto<BookResponseDto>> searchBooks(

            @Parameter(description = "Book title keyword", example = "Clean Code")
            @RequestParam(required = false) String title,

            @Parameter(description = "Author name keyword", example = "Robert Martin")
            @RequestParam(required = false) String authorName,

            @Parameter(description = "Category name", example = "Programming")
            @RequestParam(required = false) String category,

            @PageableDefault(size = 10, sort = "id") Pageable pageable) {

        return ResponseEntity.ok(
                bookService.searchBooks(
                        title,
                        authorName,
                        category,
                        pageable
                )
        );
    }

    @Operation(
            summary = "Update book",
            description = "Updates an existing book. Only administrators can perform this operation."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Book updated successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed"),
            @ApiResponse(responseCode = "404", description = "Book, author or category not found"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDto> update(
            @Parameter(description = "Book ID", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody BookRequestDto dto) {

        return ResponseEntity.ok(
                bookService.update(id, dto)
        );
    }

    @Operation(
            summary = "Delete book",
            description = "Deletes an existing book."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Book deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Book not found"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Book ID", example = "1")
            @PathVariable Long id) {

        bookService.delete(id);

        return ResponseEntity.noContent().build();
    }
}