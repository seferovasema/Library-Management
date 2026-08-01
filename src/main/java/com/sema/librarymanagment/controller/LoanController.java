package com.sema.librarymanagment.controller;

import com.sema.librarymanagment.dto.response.LoanResponseDto;
import com.sema.librarymanagment.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
