package com.sema.librarymanagment.controller;

import com.sema.librarymanagment.dto.request.AuthorRequestDto;
import com.sema.librarymanagment.dto.response.AuthorResponseDto;
import com.sema.librarymanagment.dto.response.PageResponseDto;
import com.sema.librarymanagment.service.AuthorService;
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

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
@Tag(
        name = "Author",
        description = "Author management operations"
)
@SecurityRequirement(name = "Bearer Authentication")
public class AuthorController {

    private final AuthorService authorService;

    @Operation(
            summary = "Create author",
            description = "Creates a new author. Only administrators can perform this operation."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Author created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<AuthorResponseDto> create(
            @Valid @RequestBody AuthorRequestDto dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authorService.createAuthor(dto));
    }

    @Operation(
            summary = "Get all authors",
            description = "Returns a paginated list of authors."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Authors retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Authentication required")
    })
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping
    public ResponseEntity<PageResponseDto<AuthorResponseDto>> getAll(
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {

        return ResponseEntity.ok(
                authorService.getAll(pageable)
        );
    }

    @Operation(
            summary = "Get author by ID",
            description = "Returns detailed information about a specific author."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Author found"),
            @ApiResponse(responseCode = "404", description = "Author not found"),
            @ApiResponse(responseCode = "401", description = "Authentication required")
    })
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponseDto> findById(
            @Parameter(description = "Author ID", example = "1")
            @PathVariable Long id) {

        return ResponseEntity.ok(
                authorService.findById(id)
        );
    }

    @Operation(
            summary = "Update author",
            description = "Updates an existing author. Only administrators can perform this operation."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Author updated successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed"),
            @ApiResponse(responseCode = "404", description = "Author not found"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponseDto> update(
            @Parameter(description = "Author ID", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody AuthorRequestDto dto) {

        return ResponseEntity.ok(
                authorService.update(id, dto)
        );
    }

    @Operation(
            summary = "Delete author",
            description = "Deletes an existing author. Only administrators can perform this operation."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Author deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Author not found"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Author ID", example = "1")
            @PathVariable Long id) {

        authorService.delete(id);

        return ResponseEntity.noContent().build();
    }
}