package com.training.departmentservice.controller;

import com.training.departmentservice.entity.Department;
import com.training.departmentservice.service.DepartmentService;
import com.training.departmentservice.dto.DepartmentRequest;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<Department> createDepartment(
            @Valid @RequestBody DepartmentRequest request) {

        Department department = new Department();
        department.setDepartmentName(request.departmentName());
        department.setDescription(request.description());

        Department savedDepartment =
                departmentService.createDepartment(department);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedDepartment);
    }

    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {

        return ResponseEntity.ok(
                departmentService.getAllDepartments()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(
            @PathVariable Long id) {

//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//            throw new RuntimeException("Department request was interrupted", e);
//        }

        return ResponseEntity.ok(
                departmentService.getDepartmentById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(
            @PathVariable Long id,
            @Valid @RequestBody DepartmentRequest request) {

        Department department = new Department();
        department.setDepartmentName(request.departmentName());
        department.setDescription(request.description());

        return ResponseEntity.ok(
                departmentService.updateDepartment(id, department)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(
            @PathVariable Long id) {

        departmentService.deleteDepartment(id);

        return ResponseEntity.noContent().build();
    }
}