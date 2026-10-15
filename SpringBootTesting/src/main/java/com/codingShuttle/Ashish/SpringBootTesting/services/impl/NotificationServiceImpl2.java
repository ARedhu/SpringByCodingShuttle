package com.codingShuttle.Ashish.SpringBootTesting.services.impl;

import com.codingShuttle.Ashish.SpringBootTesting.services.NotificationService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("prod")
public class NotificationServiceImpl2 implements NotificationService {
    @Override
    public String send() {
        return "This is the real Notification";
    }
}
