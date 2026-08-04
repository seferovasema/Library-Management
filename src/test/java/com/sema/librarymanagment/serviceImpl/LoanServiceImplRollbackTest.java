package com.sema.librarymanagment.serviceImpl;

import com.sema.librarymanagment.dto.request.LoanRequestDto;
import com.sema.librarymanagment.entity.Author;
import com.sema.librarymanagment.entity.Book;
import com.sema.librarymanagment.entity.Category;
import com.sema.librarymanagment.entity.Loan;
import com.sema.librarymanagment.entity.Member;
import com.sema.librarymanagment.repository.AuthorRepository;
import com.sema.librarymanagment.repository.BookRepository;
import com.sema.librarymanagment.repository.CategoryRepository;
import com.sema.librarymanagment.repository.LoanRepository;
import com.sema.librarymanagment.repository.MemberRepository;
import com.sema.librarymanagment.service.LoanService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
class LoanServiceImplRollbackTest {

    @Autowired
    private LoanService loanService;

    @MockitoSpyBean
    private LoanRepository loanRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void returnBook_ShouldRollBackMemberUpdate_WhenLoanSaveFailsAfterwards() {

        Author author = authorRepository.save(newAuthor());

        Member member = memberRepository.save(newMember());
        member.setBorrowedBooksCount(1);
        member = memberRepository.save(member);

        Category category = categoryRepository.save(newCategory());

        Book book = new Book();
        book.setTitle("Refactoring");
        book.setPrice(BigDecimal.valueOf(48));
        book.setAuthor(author);
        book.setCategories(List.of(category));
        book = bookRepository.save(book);

        Loan loan = new Loan();
        loan.setBook(book);
        loan.setMember(member);
        loan.setBorrowDate(LocalDate.now().minusDays(3));
        loan.setDueDate(LocalDate.now().plusDays(11));
        loan.setReturned(false);
        loan = loanRepository.save(loan);

        int countBefore = memberRepository.findById(member.getId())
                .orElseThrow()
                .getBorrowedBooksCount();

        Long loanId = loan.getId();

        doThrow(new RuntimeException("Simulated failure while saving the loan"))
                .when(loanRepository)
                .save(any(Loan.class));

        assertThrows(RuntimeException.class,
                () -> loanService.returnBook(loanId));

        int countAfter = memberRepository.findById(member.getId())
                .orElseThrow()
                .getBorrowedBooksCount();

        assertEquals(countBefore, countAfter);
    }

    @Test
    void borrowBook_ShouldRollBackMemberUpdate_WhenLoanSaveFailsAfterwards() {

        Author author = authorRepository.save(newAuthor());

        Member member = memberRepository.save(newMember());

        Category category = categoryRepository.save(newCategory());

        Book book = new Book();
        book.setTitle("Domain-Driven Design");
        book.setPrice(BigDecimal.valueOf(60));
        book.setAuthor(author);
        book.setCategories(List.of(category));
        book = bookRepository.save(book);

        int countBefore = memberRepository.findById(member.getId())
                .orElseThrow()
                .getBorrowedBooksCount();

        long loanCountBefore = loanRepository.count();

        LoanRequestDto dto = new LoanRequestDto(
                book.getId(),
                member.getId(),
                LocalDate.now().plusDays(14)
        );

        doThrow(new RuntimeException("Simulated failure while saving the loan"))
                .when(loanRepository)
                .save(any(Loan.class));

        assertThrows(RuntimeException.class,
                () -> loanService.borrowBook(dto));

        int countAfter = memberRepository.findById(member.getId())
                .orElseThrow()
                .getBorrowedBooksCount();

        assertEquals(countBefore, countAfter);
        assertEquals(loanCountBefore, loanRepository.count());
    }

    private Author newAuthor() {
        Author author = new Author();
        author.setName("Robert Martin");
        author.setEmail("robert" + System.nanoTime() + "@example.com");
        return author;
    }

    private Member newMember() {
        Member member = new Member();
        member.setFullName("Test Member");
        member.setEmail("member" + System.nanoTime() + "@example.com");
        member.setPhone("050" + String.format("%07d", System.nanoTime() % 10_000_000));
        member.setBorrowedBooksCount(0);
        return member;
    }

    private Category newCategory() {
        Category category = new Category();
        category.setName("Programming" + System.nanoTime());
        return category;
    }
}