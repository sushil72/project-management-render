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
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonIgnore
    @OneToOne
    private Project project;


    @JsonIgnore
    @OneToMany(mappedBy = "chat" , cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Message> messages;

    @ManyToMany
    private List<User> users = new ArrayList<>();

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", projectId=" + (project != null ? project.getId() : null) +
                ", messageCount=" + (messages != null ? messages.size() : 0) +
                '}';
    }

}
