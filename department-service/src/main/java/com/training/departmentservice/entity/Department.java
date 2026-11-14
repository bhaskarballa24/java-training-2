package com.training.departmentservice.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "DEPARTMENTS")
public class Department {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "department_seq_generator"
    )
    @SequenceGenerator(
            name = "department_seq_generator",
            sequenceName = "DEPARTMENT_SEQ",
            allocationSize = 1
    )
    @Column(name = "DEPARTMENT_ID")
    private Long departmentId;

    @Column(name = "DEPARTMENT_NAME", nullable = false, unique = true, length = 100)
    private String departmentName;

    @Column(name = "DESCRIPTION", length = 255)
    private String description;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    public Department() {
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @PrePersist
    public void beforeInsert() {
        createdAt = LocalDateTime.now();
    }
}