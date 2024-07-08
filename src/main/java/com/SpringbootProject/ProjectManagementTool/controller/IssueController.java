package com.SpringbootProject.ProjectManagementTool.controller;

import com.SpringbootProject.ProjectManagementTool.DTO.IssueDTO;
import com.SpringbootProject.ProjectManagementTool.Services.IssueService;
import com.SpringbootProject.ProjectManagementTool.Services.UserService;
import com.SpringbootProject.ProjectManagementTool.model.Issue;
import com.SpringbootProject.ProjectManagementTool.model.User;
import com.SpringbootProject.ProjectManagementTool.request.IssueRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/issue")
public class IssueController {
    @Autowired
    private UserService userService;
    @Autowired
    private IssueService issueService;

    @GetMapping("/{issueId}")
    public ResponseEntity<Issue> getIssue(@PathVariable Long issueId) throws Exception {
         Optional<Issue> issue = issueService.getissueById(issueId);
         return ResponseEntity.ok(issue.get());
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Issue>> getIssueByProjectId(@PathVariable Long projectId) throws Exception {
        return ResponseEntity.ok(issueService.getIssueByProjectId(projectId));
    }

    @PostMapping
    public ResponseEntity<IssueDTO> createIssue(@RequestBody IssueRequest issueReq , @RequestHeader("Authorization") String jwt) throws Exception {
        User tokerUser = userService.findUserProfileByJwt(jwt);
        User user = userService.findUserByid(tokerUser.getId());

        if(user == null) {
            Issue createdIssue = issueService.createIssue( issueReq,tokerUser);
            IssueDTO issueDTO = new IssueDTO();
            issueDTO.setDescription(createdIssue.getDescription());
            issueDTO.setTitle(createdIssue.getTitle());
            issueDTO.setStatus(createdIssue.getStatus());
            issueDTO.setTags(createdIssue.getTags());
            issueDTO.setAssignee(createdIssue.getAssignee());
            issueDTO.setProjectId(createdIssue.getProjectID());

        }

    }

}
