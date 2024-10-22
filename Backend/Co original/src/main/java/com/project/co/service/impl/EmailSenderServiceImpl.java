package com.project.co.service.impl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.project.co.service.EmailSenderService;

@Service
public class EmailSenderServiceImpl implements EmailSenderService{

    private JavaMailSender mailSender;

    public EmailSenderServiceImpl(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(String name, String from, String phone, String messsage ){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo("tharushaukwaththa@gmail.com");
        simpleMailMessage.setSubject("Performance Tracker");
        simpleMailMessage.setText(messsage +"\n"+"\n"+"Best Regards, \n"+"  "+name+".\n"+"  "+phone+"\n"+"  "+from);
        simpleMailMessage.setFrom(from);

        this.mailSender.send(simpleMailMessage);
    }

}

