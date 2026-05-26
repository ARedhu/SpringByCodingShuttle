package com.codingshuttle.module1introduction.impl;

import com.codingshuttle.module1introduction.NotificationService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

//@Primary // We multiple beans are there for the same interface/parent class then we have to make one of them as primary else spring will not be able to detect which one to use with the interface name calling.
@Component
@Qualifier("emailNotif") // To make then identifiable seperately.
public class EmailService implements NotificationService {
    @Override
    public void send(String msg) {
        System.out.println("Email service msg: "+msg);
    }
}
