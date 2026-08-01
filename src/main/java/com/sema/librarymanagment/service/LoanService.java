package com.sema.librarymanagment.service;

import com.sema.librarymanagment.dto.response.LoanResponseDto;

import java.util.List;

public interface LoanService {

    List<LoanResponseDto> getActiveLoans();

    List<LoanResponseDto> getOverdueLoans();

    List<LoanResponseDto> getActiveLoansByMember(Long memberId);
}
