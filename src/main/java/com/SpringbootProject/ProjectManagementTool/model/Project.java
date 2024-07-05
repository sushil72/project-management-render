package com.SpringbootProject.ProjectManagementTool.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    private Long id;
    private String name;
    private String description;
    private String category;
    private List<String> tags=new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy =  "project" , cascade = CascadeType.ALL,orphanRemoval = true)
    private Chat chat;

    @ManyToOne
    private User owner;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Issue>issues=new ArrayList<>();

    @OneToMany
    private List<User>team=new ArrayList<>();





}
