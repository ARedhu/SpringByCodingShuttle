package com.codingshuttle.module1introduction;

import com.sun.nio.sctp.Notification;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class Module1introductionApplication implements CommandLineRunner {

    // In Java the annotation are basically to tell the fellow programs the usecase of this method and using these are completely optional like @Override. But in case of String it is importatnt to use Annotations because they actually write execution logic behind the scenes and we can't remove them.
    // Way-1 of DI (it is not preferred)
    //	@Autowired
    //	NotificationService notificationService;

    // Way-2: Constructor DI (preferred) because now we can  make it final.
//	final NotificationService notificationService;
//	Module1introductionApplication(@Qualifier("emailNotif") NotificationService notificationService){

    // Way-3: Using ConditionalOnProperty.
//    final NotificationService notificationService;
//    Module1introductionApplication(NotificationService notificationService){
//        this.notificationService = notificationService;
//    }

    // Way-4: If we want to fetch all the implementations of the interface or let's say we want to send both email and sms as notification.
    private final Map<String, NotificationService> hm;
    Module1introductionApplication(Map<String, NotificationService> hm){
        this.hm = hm;
    }
    public static void main(String[] args) {
        SpringApplication.run(Module1introductionApplication.class, args);
//		SmsService smsService = new SmsService();
//		smsService.send("ashish");
//		EmailService emailService = new EmailService();
//		emailService.send("golu");
    }


    @Override
    public void run(String... args) throws Exception {
//        notificationService.send("ashish");
        for(var entry : hm.entrySet()){
            System.out.println(entry.getKey());
            entry.getValue().send("ashish");
        }
    }
}
