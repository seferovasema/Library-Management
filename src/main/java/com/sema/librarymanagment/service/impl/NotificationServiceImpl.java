package com.sema.librarymanagment.service.impl;

import com.sema.librarymanagment.service.notification.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    @Async
    @Override
    public void sendLoanNotification(String message) {

        log.info("Sending notification: {}", message);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        log.info("Notification sent: {}", message);
    }
}
