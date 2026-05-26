package com.codingshuttle.module1introduction;

import com.codingshuttle.module1introduction.impl.EmailService;
import com.codingshuttle.module1introduction.impl.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Module1introductionApplication implements CommandLineRunner {

	// In Java the annotation are basically to tell the fellow programs the usecase of this method and using these are completely optional like @Override. But in case of String it is importatnt to use Annotations because they actually write execution logic behind the scenes and we can't remove them.
	@Autowired
	NotificationService notificationService;
	public static void main(String[] args) {
		SpringApplication.run(Module1introductionApplication.class, args);
//		SmsService smsService = new SmsService();
//		smsService.send("ashish");
//		EmailService emailService = new EmailService();
//		emailService.send("golu");
	}


	@Override
	public void run(String... args) throws Exception {
		notificationService.send("ashish");
	}
}
