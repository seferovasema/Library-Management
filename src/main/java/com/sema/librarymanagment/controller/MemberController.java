package com.sema.librarymanagment.controller;

import com.sema.librarymanagment.dto.request.MemberRequestDto;
import com.sema.librarymanagment.dto.response.MemberResponseDto;
import com.sema.librarymanagment.dto.response.PageResponseDto;
import com.sema.librarymanagment.service.MemberService;
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
@RequestMapping("/members")
@RequiredArgsConstructor
@Tag(
        name = "Member",
        description = "Library member management operations"
)
@SecurityRequirement(name = "Bearer Authentication")
public class MemberController {

    private final MemberService memberService;

    @Operation(
            summary = "Create member",
            description = "Creates a new library member."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Member created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<MemberResponseDto> create(
            @Valid @RequestBody MemberRequestDto dto) {

        MemberResponseDto response =
                memberService.create(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @Operation(
            summary = "Get all members",
            description = "Returns a paginated list of library members."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Members retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Authentication required")
    })
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping
    public ResponseEntity<PageResponseDto<MemberResponseDto>> getAll(
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {

        return ResponseEntity.ok(
                memberService.getAll(pageable)
        );
    }

    @Operation(
            summary = "Get member by ID",
            description = "Returns detailed information about a specific member."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Member found"),
            @ApiResponse(responseCode = "404", description = "Member not found"),
            @ApiResponse(responseCode = "401", description = "Authentication required")
    })
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDto> findById(
            @Parameter(description = "Member ID", example = "1")
            @PathVariable Long id) {

        return ResponseEntity.ok(
                memberService.findById(id)
        );
    }

    @Operation(
            summary = "Update member",
            description = "Updates an existing library member."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Member updated successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed"),
            @ApiResponse(responseCode = "404", description = "Member not found"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<MemberResponseDto> update(
            @Parameter(description = "Member ID", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody MemberRequestDto dto) {

        return ResponseEntity.ok(
                memberService.update(id, dto)
        );
    }

    @Operation(
            summary = "Delete member",
            description = "Deletes an existing library member."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Member deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Member not found"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Member ID", example = "1")
            @PathVariable Long id) {

        memberService.delete(id);

        return ResponseEntity.noContent().build();
    }
}