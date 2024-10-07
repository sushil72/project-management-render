package com.SpringbootProject.ProjectManagementTool.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String category;
    @ElementCollection
    @CollectionTable(name = "project_tags", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "tag")
    private List<String> tags;

  ;  @JsonIgnore
    @OneToOne(mappedBy =  "project" , cascade = CascadeType.ALL,orphanRemoval = true)
    private Chat chat;

    @ManyToOne
    private User owner;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Issue>issues=new ArrayList<>();

    @ManyToMany()
    private List<User>team=new ArrayList<>();

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", chatId=" + (chat != null ? chat.getId() : null) +
                '}';
    }

}
