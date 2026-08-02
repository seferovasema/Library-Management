package com.sema.librarymanagment.controller;

import com.sema.librarymanagment.dto.request.LoanRequestDto;
import com.sema.librarymanagment.dto.response.LoanResponseDto;
import com.sema.librarymanagment.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
        description = "Loan management operations"
)
public class LoanController {
    private final LoanService loanService;

    @Operation(summary = "Borrow a book")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Book borrowed successfully"),
            @ApiResponse(responseCode = "404", description = "Book or Member not found"),
            @ApiResponse(responseCode = "409", description = "Book is already borrowed")
    })
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/borrow")
    public ResponseEntity<LoanResponseDto> borrowBook(@Valid @RequestBody LoanRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(loanService.borrowBook(dto));
    }


    @Operation(summary = "Return a borrowed book")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Book returned successfully"),
            @ApiResponse(responseCode = "404", description = "Loan not found"),
            @ApiResponse(responseCode = "409", description = "Loan already returned")
    })
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/{loanId}/return")
    public ResponseEntity<LoanResponseDto> returnBook(@PathVariable Long loanId) {
        return ResponseEntity.ok(loanService.returnBook(loanId));
    }



    @Operation(summary = "Get active loans")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Active loans retrieved successfully")
    })
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/active")
    public ResponseEntity<List<LoanResponseDto>> getActiveLoans() {

        return ResponseEntity.ok(loanService.getActiveLoans());
    }

    @Operation(summary = "Get overdue loans")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Overdue loans retrieved successfully")
    })
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/overdue")
    public ResponseEntity<List<LoanResponseDto>> getOverdueLoans() {

        return ResponseEntity.ok(loanService.getOverdueLoans());
    }

    @Operation(summary = "Get active loans by member")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Loans retrieved successfully")
    })
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<LoanResponseDto>> getActiveLoansByMember(
            @PathVariable Long memberId) {

        return ResponseEntity.ok(
                loanService.getActiveLoansByMember(memberId)
        );
    }

    @Operation(summary = "Search loans by book title")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Loans retrieved successfully")
    })
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/search")
    public ResponseEntity<List<LoanResponseDto>> searchLoansByBookTitle(
            @RequestParam String title) {

        return ResponseEntity.ok(
                loanService.searchLoansByBookTitle(title)
        );
    }
}
