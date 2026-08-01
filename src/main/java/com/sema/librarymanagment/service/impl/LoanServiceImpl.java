package com.sema.librarymanagment.service.impl;

import com.sema.librarymanagment.dto.response.LoanResponseDto;
import com.sema.librarymanagment.entity.Loan;
import com.sema.librarymanagment.mapper.LoanMapper;
import com.sema.librarymanagment.repository.LoanRepository;
import com.sema.librarymanagment.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final LoanMapper loanMapper;

    @Override
    public List<LoanResponseDto> getActiveLoans() {
        List<Loan> loans=loanRepository.findByReturnedFalse();
        return loans.stream()
                .map(loanMapper::toDto)
                .toList();
    }

    @Override
    public List<LoanResponseDto> getOverdueLoans() {
        List<Loan> loans = loanRepository.findOverdueLoans(LocalDate.now());

        return loans.stream()
                .map(loanMapper::toDto)
                .toList();
    }

    @Override
    public List<LoanResponseDto> getActiveLoansByMember(Long memberId) {
        List<Loan> loans = loanRepository.findByMemberIdAndReturnedFalse(memberId);

        return loans.stream()
                .map(loanMapper::toDto)
                .toList();
    }

    @Override
    public List<LoanResponseDto> searchLoansByBookTitle(String title) {
        List<Loan> loans = loanRepository.findByBookTitleContainingIgnoreCase(title);

        return loans.stream()
                .map(loanMapper::toDto)
                .toList();
    }
}
