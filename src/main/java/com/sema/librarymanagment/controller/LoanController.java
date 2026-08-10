package com.sema.librarymanagment.controller;

import com.sema.librarymanagment.dto.request.LoanRequestDto;
import com.sema.librarymanagment.dto.response.LoanResponseDto;
import com.sema.librarymanagment.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
@Tag(
        name = "Loan",
        description = "Book borrowing and returning operations"
)
@SecurityRequirement(name = "Bearer Authentication")
public class LoanController {

    private final LoanService loanService;

    @Operation(
            summary = "Borrow a book",
            description = "Creates a new loan for a member and book."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Book borrowed successfully"),
            @ApiResponse(responseCode = "404", description = "Book or member not found"),
            @ApiResponse(responseCode = "409", description = "Book is already borrowed"),
            @ApiResponse(responseCode = "401", description = "Authentication required")
    })
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/borrow")
    public ResponseEntity<LoanResponseDto> borrowBook(
            @Valid @RequestBody LoanRequestDto dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(loanService.borrowBook(dto));
    }

    @Operation(
            summary = "Return a borrowed book",
            description = "Marks an active loan as returned and updates the member's borrowed book count."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Book returned successfully"),
            @ApiResponse(responseCode = "404", description = "Loan not found"),
            @ApiResponse(responseCode = "409", description = "Loan already returned"),
            @ApiResponse(responseCode = "401", description = "Authentication required")
    })
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/{loanId}/return")
    public ResponseEntity<LoanResponseDto> returnBook(
            @Parameter(description = "Loan ID", example = "1")
            @PathVariable Long loanId) {

        return ResponseEntity.ok(
                loanService.returnBook(loanId)
        );
    }

    @Operation(
            summary = "Get active loans",
            description = "Returns all currently active loans."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Active loans retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Authentication required")
    })
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/active")
    public ResponseEntity<List<LoanResponseDto>> getActiveLoans() {

        return ResponseEntity.ok(
                loanService.getActiveLoans()
        );
    }

    @Operation(
            summary = "Get overdue loans",
            description = "Returns all active loans whose due date has passed."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Overdue loans retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Authentication required")
    })
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/overdue")
    public ResponseEntity<List<LoanResponseDto>> getOverdueLoans() {

        return ResponseEntity.ok(
                loanService.getOverdueLoans()
        );
    }

    @Operation(
            summary = "Get active loans by member",
            description = "Returns all active loans belonging to a specific member."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Loans retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Authentication required")
    })
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<LoanResponseDto>> getActiveLoansByMember(
            @Parameter(description = "Member ID", example = "1")
            @PathVariable Long memberId) {

        return ResponseEntity.ok(
                loanService.getActiveLoansByMember(memberId)
        );
    }

    @Operation(
            summary = "Search loans by book title",
            description = "Searches loans using a case-insensitive book title keyword."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Loans retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Authentication required")
    })
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/search")
    public ResponseEntity<List<LoanResponseDto>> searchLoansByBookTitle(
            @Parameter(
                    description = "Book title keyword",
                    example = "Clean Code"
            )
            @RequestParam String title) {

        return ResponseEntity.ok(
                loanService.searchLoansByBookTitle(title)
        );
    }
}