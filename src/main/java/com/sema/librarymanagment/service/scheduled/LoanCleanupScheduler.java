package com.sema.librarymanagment.service.scheduled;

import com.sema.librarymanagment.entity.Loan;
import com.sema.librarymanagment.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoanCleanupScheduler {

    private final LoanRepository loanRepository;

    @Scheduled(cron = "0 0 2 * * *")
    public void cleanupOldReturnedLoans() {

        LocalDate limitDate = LocalDate.now().minusDays(30);

        List<Loan> oldLoans =
                loanRepository.findByReturnedTrueAndReturnDateBefore(limitDate);

        if (oldLoans.isEmpty()) {
            log.info("No old returned loans found for cleanup");
            return;
        }

        loanRepository.deleteAll(oldLoans);

        log.info("{} old returned loans were deleted", oldLoans.size());
    }
}