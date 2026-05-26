package com.codingshuttle.module1introduction.impl;

import com.codingshuttle.module1introduction.NotificationService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class EmailService implements NotificationService {
    @Override
    public void send(String msg) {
        System.out.println("Email service msg: "+msg);
    }
}
