package com.SpringbootProject.ProjectManagementTool.controller;

import com.SpringbootProject.ProjectManagementTool.Services.ProjectService;
import com.SpringbootProject.ProjectManagementTool.Services.UserService;
import com.SpringbootProject.ProjectManagementTool.model.Chat;
import com.SpringbootProject.ProjectManagementTool.model.Project;
import com.SpringbootProject.ProjectManagementTool.model.User;
import com.SpringbootProject.ProjectManagementTool.repository.ProjectRepository;
import com.SpringbootProject.ProjectManagementTool.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    @Autowired
        ProjectService projectService;
    @Autowired
        UserService userService;
    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping
    public ResponseEntity<List<Project>> getProjects(
            @RequestParam(required = false )String Category,
            @RequestParam(required = false) String tag,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);
        List<Project>projects = projectService.getAllProjectByTeam(user,Category,tag);
        return new ResponseEntity<>(projects, HttpStatus.OK);

    }

    @GetMapping("/{projectId}")
    public  ResponseEntity<Project> getProjectById(@PathVariable Long projectId ,
                                           @RequestHeader("authorization") String jwt,
                                           )throws  Exception{
        User user = userService.findUserProfileByJwt(jwt);
        Project project = projectService.getProjectById(projectId);
        return new ResponseEntity<>(project,HttpStatus.OK);
    }

    @PostMapping("/{projectId}")
    public ResponseEntity<Project> createProject(@PathVariable Long projectId ,
                              @RequestHeader("Authorization") String jwt ,
                              @RequestBody Project project
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Project createdproject = projectService.createProject(project , user);
        return new ResponseEntity<>(createdproject,HttpStatus.CREATED);
    }

    @PatchMapping("{projectId}")
    public ResponseEntity<Project> updateProject(@PathVariable Long projectId,
              @RequestBody Project projectr) throws Exception
    {
        Project updatedProject = projectService.updateProject(projectr,projectId);
        return new ResponseEntity<>(updatedProject,HttpStatus.OK);
    }

    @DeleteMapping("{projectid}")
    public ResponseEntity<MessageResponse> deleteProject(
            @PathVariable Long projectid , @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        projectService.deleteProject(projectid,user.getId());
        MessageResponse meassage = new MessageResponse("Project Successfullyt Deleted");
        return new ResponseEntity<>(meassage,HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Project>> searchProject(@RequestParam(required = false)String keyword ,
                                                       @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        List<Project> projects = projectService.searchProjects(keyword,user);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/{projectid}/chat")
    public ResponseEntity<Chat> getChatByprojectId(@PathVariable Long projectid ,
                                                   @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Chat chat = projectService.getChatByProjectId(projectid);
        return new ResponseEntity<>(chat,HttpStatus.OK);
    }





}
