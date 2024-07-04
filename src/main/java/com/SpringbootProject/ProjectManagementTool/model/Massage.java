package com.SpringbootProject.ProjectManagementTool.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.yaml.snakeyaml.events.Event;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Massage {
    @Id
    private Long id;

    private String content;

    private LocalDateTime createdAt;

    @ManyToOne
    private User sender;
}
