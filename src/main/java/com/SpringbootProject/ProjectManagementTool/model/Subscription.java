package com.SpringbootProject.ProjectManagementTool.model;

import com.SpringbootProject.ProjectManagementTool.model.Enum.PlanType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;
    private LocalDate subscriptionStartDate;
    private LocalDate subscriptionEndDate;
    @Enumerated(EnumType.STRING)
    private PlanType planType;
    private boolean isValid ;
    @OneToOne
    private User user;
}
