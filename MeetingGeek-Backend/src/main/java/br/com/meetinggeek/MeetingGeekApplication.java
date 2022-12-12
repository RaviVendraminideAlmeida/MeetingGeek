package br.com.meetinggeek;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication//(exclude = SecurityAutoConfiguration.class)
public class MeetingGeekApplication {

    public static void main(String[] args) {
        SpringApplication.run(MeetingGeekApplication.class, args);
    }

}



