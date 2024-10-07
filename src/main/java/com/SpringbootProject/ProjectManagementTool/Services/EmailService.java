package com.SpringbootProject.ProjectManagementTool.Services;

import jakarta.mail.MessagingException;

public interface EmailService {
    public void sendEmailWithToken(String email, String link) throws MessagingException;
}
