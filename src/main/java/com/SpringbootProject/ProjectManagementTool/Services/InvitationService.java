package com.SpringbootProject.ProjectManagementTool.Services;

import com.SpringbootProject.ProjectManagementTool.model.Invitation;
import jakarta.mail.MessagingException;

public interface InvitationService {

    public void sendInvitation(String email , Long projectId) throws MessagingException;

    public Invitation acceptInvitation(String toiken ,  Long UserId ) throws Exception;

    public String getTokenByUserMail(String email);

    void deletetoken(String token);

}
