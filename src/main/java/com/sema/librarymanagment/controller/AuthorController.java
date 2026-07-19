package com.sema.librarymanagment.controller;

import com.sema.librarymanagment.dto.request.AuthorRequestDto;
import com.sema.librarymanagment.dto.response.AuthorResponseDto;
import com.sema.librarymanagment.dto.response.PageResponseDto;
import com.sema.librarymanagment.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
@Tag(
        name = "Author",
        description = "Author management operations"
)
public class AuthorController {
    private final AuthorService authorService;

    @Operation(summary = "Create author")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Author created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed")
    })
    @PostMapping
    public ResponseEntity<AuthorResponseDto> create(@Valid @RequestBody AuthorRequestDto dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authorService.createAuthor(dto));
    }

    @Operation(summary = "Get all authors")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Authors retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<PageResponseDto<AuthorResponseDto>> getAll(
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {

        return ResponseEntity.ok(authorService.getAll(pageable));
    }

    @Operation(summary = "Get author by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Author found"),
            @ApiResponse(responseCode = "404", description = "Author not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponseDto> findById(@PathVariable Long id) {

        return ResponseEntity.ok(authorService.findById(id));
    }

    @Operation(summary = "Update author")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Author updated successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed"),
            @ApiResponse(responseCode = "404", description = "Author not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponseDto> update(@PathVariable Long id,
                                                    @Valid @RequestBody AuthorRequestDto dto) {

        return ResponseEntity.ok(authorService.update(id, dto));
    }

    @Operation(summary = "Delete author")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Author deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Author not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        authorService.delete(id);

        return ResponseEntity.noContent().build();
    }
}

