package com.sema.librarymanagment.repository;

import com.sema.librarymanagment.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByReturnedFalse();

    List<Loan> findByReturnedFalseAndDueDateBefore(LocalDate date);

    List<Loan> findByMemberIdAndReturnedFalse(Long memberId);

    List<Loan> findByBookTitleContainingIgnoreCase(String titleKeyword);

    Optional<Loan> findByBookIdAndReturnedFalse(Long bookId);

    boolean existsByBookIdAndReturnedFalse(Long bookId);
}
