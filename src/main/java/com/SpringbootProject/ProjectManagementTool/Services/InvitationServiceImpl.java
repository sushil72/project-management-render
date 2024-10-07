package com.SpringbootProject.ProjectManagementTool.Services;

import com.SpringbootProject.ProjectManagementTool.model.Invitation;
import com.SpringbootProject.ProjectManagementTool.repository.InvitationRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InvitationServiceImpl implements  InvitationService{

    @Autowired
    private InvitationRepository invitationRepository;

    @Autowired
    private EmailService emailService;
    @Override
    public void sendInvitation(String email, Long projectId) throws MessagingException {
        System.out.println("email and  ProjectId : "+email + "  "+ projectId);
        String Invitationtoken= UUID.randomUUID().toString();
        Invitation invitation = new Invitation();
        invitation.setEmail(email);
        invitation.setToken(Invitationtoken);
        invitation.setProjectId(projectId);
        invitationRepository.save(invitation);
        String invitationLink = " http://localhost:5173/accept_invitation?token="+Invitationtoken;
        emailService.sendEmailWithToken(email,invitationLink);
        System.out.println("invitation saved and sent ");
    }

    @Override
    public Invitation acceptInvitation(String token, Long UserId) throws Exception {
        Invitation invitation = invitationRepository.findByToken(token);
        if (invitation==null)
        {
            throw new Exception("Invalid invitation token");
        }
        return invitation;
    }

    @Override
    public String getTokenByUserMail(String email) {
        return invitationRepository.findByEmail(email);
    }

    @Override
    public void deletetoken(String token) {
        invitationRepository.delete(invitationRepository.findByToken(token));
    }
}
