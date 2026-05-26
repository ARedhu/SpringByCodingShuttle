package com.codingshuttle.module1introduction.impl;

import com.codingshuttle.module1introduction.NotificationService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
//@Qualifier("smsNotif")
//@ConditionalOnProperty(name = "notification.type", havingValue = "smsNotif")

public class SmsService implements NotificationService {
    @Override
    public void send(String msg){
        System.out.println("Sms Service msg: "+msg);
    }
}
