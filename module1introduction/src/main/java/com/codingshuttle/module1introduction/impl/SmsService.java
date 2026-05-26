package com.codingshuttle.module1introduction.impl;

import com.codingshuttle.module1introduction.NotificationService;
import org.springframework.stereotype.Component;

@Component
public class SmsService implements NotificationService {
    @Override
    public void send(String msg){
        System.out.println("Sms Service msg: "+msg);
    }
}
