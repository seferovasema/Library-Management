package com.sema.librarymanagment.repository;

import com.sema.librarymanagment.entity.Loan;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    @EntityGraph(attributePaths = {"book", "member"})
    List<Loan> findByReturnedFalse();

    @EntityGraph(attributePaths = {"book", "member"})
    List<Loan> findByReturnedFalseAndDueDateBefore(LocalDate date);

    @EntityGraph(attributePaths = {"book", "member"})
    List<Loan> findByMemberIdAndReturnedFalse(Long memberId);

    @EntityGraph(attributePaths = {"book", "member"})
    List<Loan> findByBookTitleContainingIgnoreCase(String titleKeyword);

    Optional<Loan> findByBookIdAndReturnedFalse(Long bookId);

    boolean existsByBookIdAndReturnedFalse(Long bookId);

    @EntityGraph(attributePaths = {"book", "member"})
    List<Loan> findByReturnedTrueAndReturnDateBefore(LocalDate date);

    @EntityGraph(attributePaths = {"book", "member"})
    @Query("""
            SELECT l
            FROM Loan l
            WHERE l.returned = false
              AND l.dueDate < :date
        """)
    List<Loan> findOverdueLoans(@Param("date") LocalDate date);
}