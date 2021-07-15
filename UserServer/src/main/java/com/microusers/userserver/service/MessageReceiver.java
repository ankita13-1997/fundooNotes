//package com.microusers.userserver.service;
//
//import com.microusers.userserver.model.RabbitMQBody;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//
//public class MessageReceiver {
//
//    @Autowired
//    private JavaMailSender javaMailSender;
//
//    public void receiveMsg(RabbitMQBody rabbitMQBody) {
//
//        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//        simpleMailMessage.setTo(rabbitMQBody.getEmail());
//        simpleMailMessage.setSubject(rabbitMQBody.getSubject());
//        simpleMailMessage.setText(rabbitMQBody.getBody());
//        javaMailSender.send(simpleMailMessage);
//    }
//}
