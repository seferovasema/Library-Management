package com.sema.librarymanagment.service;

import com.sema.librarymanagment.dto.request.LoanRequestDto;
import com.sema.librarymanagment.dto.response.LoanResponseDto;

import java.util.List;

public interface LoanService {

    List<LoanResponseDto> getActiveLoans();

    List<LoanResponseDto> getOverdueLoans();

    List<LoanResponseDto> getActiveLoansByMember(Long memberId);

    List<LoanResponseDto> searchLoansByBookTitle(String title);

    LoanResponseDto borrowBook(LoanRequestDto dto);

    LoanResponseDto returnBook(Long loanId);
}
