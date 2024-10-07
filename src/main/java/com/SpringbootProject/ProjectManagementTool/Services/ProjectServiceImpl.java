package com.SpringbootProject.ProjectManagementTool.Services;

import com.SpringbootProject.ProjectManagementTool.model.Chat;
import com.SpringbootProject.ProjectManagementTool.model.Project;
import com.SpringbootProject.ProjectManagementTool.model.User;
import com.SpringbootProject.ProjectManagementTool.repository.ChatRepository;
import com.SpringbootProject.ProjectManagementTool.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    ChatService chatService;

    @Override
    public Project createProject(Project project, User user) throws Exception {

        Project newProject = new Project();
        newProject.setName(project.getName());
        newProject.setDescription(project.getDescription());
        newProject.setOwner(user);
        newProject.setDescription(project.getDescription());
        newProject.setCategory(project.getCategory());
        newProject.setIssues(project.getIssues());
        newProject.getTeam().add(user);
        newProject.setTags(project.getTags());
        Project savesProject = projectRepository.save(newProject);

        Chat chat = new Chat();
        chat.setProject(savesProject);
        Chat projectChat = chatService.createChat(chat);
        savesProject.setChat(projectChat);
        return savesProject;
    }

    @Override
    public List<Project> getAllProjectByTeam(User user, String category, String tag) throws Exception {
       List<Project> projects=projectRepository.findByTeamContainingOrOwner(user,user);
        System.out.println("\n\nCategory  "+ category + ", tag: " + tag);
//        System.out.println(projects.getLast().getTags());
        if(category!=null)
       {
           projects = projects.stream().filter(projec-> projec.getCategory().equals(category)).collect(Collectors.toList());
       }
//        if (tag != null ) {
//            System.out.println("Filtered by tag: ");
//            projects = projects.stream()
//                    .filter(project -> project.getTags().contains(tag))
//                    .collect(Collectors.toList());
//            System.out.println(projects);
//        }
        if (tag != null) {
            System.out.println("Filtered by tag: " + tag);
            projects = projects.stream()
                    .filter(project -> project.getTags() != null && project.getTags().stream().anyMatch(t -> t.equalsIgnoreCase(tag)))
                    .collect(Collectors.toList());
            System.out.println(projects);
        }
        return  projects;
    }



    @Override
    public Project getProjectById(Long id) throws Exception {
        Project project =  projectRepository.findById(id).orElse(null);
        if(project==null)
        {
            throw new Exception("Project not found");
        }
        return project;
    }

    @Override
    public void deleteProject(Long pid, Long uid) throws Exception {
            projectRepository.deleteById(pid);
    }

    @Override
    public Project updateProject(Project updatedProject, Long id) throws Exception {
        Project project = getProjectById(id);
        project.setName(updatedProject.getName());
        project.setDescription(updatedProject.getDescription());
        project.setTags(updatedProject.getTags());

        return projectRepository.save(project);
    }

    @Override
    public void addUserToProject(Long projectId, Long userId) throws Exception {
        Project project = getProjectById(projectId);
        User user = userService.findUserByid(userId);

        for(User member : project.getTeam()){
            if(member.getId().equals(userId)){
                return;

            }
        }
            project.getChat().getUsers().add(user);
            project.getTeam().add(user);
            projectRepository.save(project);
    }

    @Override
    public void remobeUserFromProject(Long projectId, Long userId) throws Exception {
        Project project = getProjectById(projectId);
        User user = userService.findUserByid(userId);
        if(project.getTeam().contains(user)){
            project.getChat().getUsers().remove(user);
            project.getTeam().remove(user);
        }
        projectRepository.save(project);
    }

//    @Override
//    public Chat getChatByProjectId(Long projectId) throws Exception {
//        System.out.println("project id : "+projectId);
//        Project project = getProjectById(projectId);
//        System.out.println("project name : "+project.getName());
//        System.out.println("project chat: "+project.getChat().getMessages());
//        return project.getChat();
//    }
@Override
public Chat getChatByProjectId(Long projectId) throws Exception {
    Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new Exception("Project not found"));
    return project.getChat();
}
    @Override
    public List<Project> searchProjects(String keyword, User user) {
        return projectRepository.findByNameContainingAndTeamContains(keyword, user);
    }
}
