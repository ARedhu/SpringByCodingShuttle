package com.codingShuttle.Ashish.SpringBootTesting.services.impl;

import com.codingShuttle.Ashish.SpringBootTesting.services.NotificationService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"dev", "default"})
public class NotificationServiceImpl1 implements NotificationService {
    @Override
    public String send() {
        return "Dummy Notification";
    }
}
