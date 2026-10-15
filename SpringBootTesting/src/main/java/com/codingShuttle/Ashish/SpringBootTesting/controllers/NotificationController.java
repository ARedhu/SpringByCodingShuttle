package com.codingShuttle.Ashish.SpringBootTesting.controllers;

import com.codingShuttle.Ashish.SpringBootTesting.services.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.Notification;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    private NotificationService notificationService;

    public NotificationController(NotificationService notificationService){
        this.notificationService = notificationService;
    }

    @GetMapping
    public ResponseEntity<String> sendNotification(){
        String notification = notificationService.send();
        return ResponseEntity.ok(notification);
    }
}
