package com.SpringbootProject.ProjectManagementTool.Services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class  EmailServiceimpl implements EmailService {
    @Autowired
    private JavaMailSender mailSender;
    @Override
    public void sendEmailWithToken(String email, String link) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,"utf-8");
        String subject = "Invitation for Project Joining ";
        String text = "Click link to join the project team "+link;
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(text);
        mimeMessageHelper.setTo(email);
        mailSender.send(mimeMessage);
    }

}

