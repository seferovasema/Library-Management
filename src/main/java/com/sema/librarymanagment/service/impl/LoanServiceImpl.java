
package com.sema.librarymanagment.service.impl;

import com.sema.librarymanagment.dto.request.LoanRequestDto;
import com.sema.librarymanagment.dto.response.LoanResponseDto;
import com.sema.librarymanagment.entity.Book;
import com.sema.librarymanagment.entity.Loan;
import com.sema.librarymanagment.entity.Member;
import com.sema.librarymanagment.exception.ResourceNotFoundException;
import com.sema.librarymanagment.mapper.LoanMapper;
import com.sema.librarymanagment.repository.BookRepository;
import com.sema.librarymanagment.repository.LoanRepository;
import com.sema.librarymanagment.repository.MemberRepository;
import com.sema.librarymanagment.service.LoanService;
import com.sema.librarymanagment.service.notification.NotificationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final LoanMapper loanMapper;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final NotificationService notificationService;

    @Override
    public List<LoanResponseDto> getActiveLoans() {
        List<Loan> loans = loanRepository.findByReturnedFalse();

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
        List<Loan> loans =
                loanRepository.findByMemberIdAndReturnedFalse(memberId);

        return loans.stream()
                .map(loanMapper::toDto)
                .toList();
    }

    @Override
    public List<LoanResponseDto> searchLoansByBookTitle(String title) {
        List<Loan> loans =
                loanRepository.findByBookTitleContainingIgnoreCase(title);

        return loans.stream()
                .map(loanMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public LoanResponseDto borrowBook(LoanRequestDto dto) {

        if (loanRepository.existsByBookIdAndReturnedFalse(dto.getBookId())) {
            throw new IllegalStateException("Book is already borrowed");
        }

        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Book not found"));

        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Member not found"));

        Loan loan = new Loan();

        loan.setBook(book);
        loan.setMember(member);
        loan.setBorrowDate(LocalDate.now());
        loan.setDueDate(dto.getDueDate());
        loan.setReturned(false);

        member.setBorrowedBooksCount(
                member.getBorrowedBooksCount() + 1
        );

        memberRepository.save(member);

        Loan savedLoan = loanRepository.save(loan);

        notificationService.sendLoanNotification(
                "Book borrowed successfully. Loan ID: "
                        + savedLoan.getId()
        );

        return loanMapper.toDto(savedLoan);
    }

    @Override
    @Transactional
    public LoanResponseDto returnBook(Long loanId) {

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Loan not found"));

        if (loan.isReturned()) {
            throw new IllegalStateException("Book has already been returned");
        }

        loan.setReturned(true);
        loan.setReturnDate(LocalDate.now());

        Member member = loan.getMember();

        member.setBorrowedBooksCount(
                Math.max(0, member.getBorrowedBooksCount() - 1)
        );

        memberRepository.save(member);

        Loan updatedLoan = loanRepository.save(loan);

        notificationService.sendLoanNotification(
                "Book returned successfully. Loan ID: "
                        + updatedLoan.getId()
        );

        return loanMapper.toDto(updatedLoan);
    }
}

