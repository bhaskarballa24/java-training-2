package com.training.departmentservice.service;

import com.training.departmentservice.entity.Department;
import com.training.departmentservice.repository.DepartmentRepository;
import com.training.departmentservice.exception.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class DepartmentServiceImpl implements DepartmentService {

    private static final Logger logger =
            LoggerFactory.getLogger(DepartmentServiceImpl.class);

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

//    @Override
//    public Department createDepartment(Department department) {
//
//        if (departmentRepository.existsByDepartmentName(
//                department.getDepartmentName())) {
//
//            throw new DuplicateDepartmentException(
//                    "Department already exists: "
//                            + department.getDepartmentName()
//            );
//        }
//
//        return departmentRepository.save(department);
//    }

    @Override
    @Transactional
    public Department createDepartment(Department department) {

        logger.info(
                "Creating department: {}",
                department.getDepartmentName()
        );

        if (departmentRepository.existsByDepartmentName(
                department.getDepartmentName())) {

            logger.warn(
                    "Duplicate department detected: {}",
                    department.getDepartmentName()
            );

            throw new DuplicateDepartmentException(
                    "Department already exists: "
                            + department.getDepartmentName()
            );
        }

        Department savedDepartment =
                departmentRepository.save(department);

        logger.info(
                "Department created successfully with ID: {}",
                savedDepartment.getDepartmentId()
        );

        return savedDepartment;
    }

    @Override
    public List<Department> getAllDepartments() {

        return departmentRepository.findAll();
    }

    @Override
    public Department getDepartmentById(Long id) {

        return departmentRepository.findById(id)
                .orElseThrow(() ->
                        new DepartmentNotFoundException(
                                "Department not found with ID: " + id
                        )
                );
    }

    @Override
    @Transactional
    public Department updateDepartment(
            Long id,
            Department updatedDepartment) {

        Department existingDepartment = getDepartmentById(id);

        String newName = updatedDepartment.getDepartmentName();

        if (!existingDepartment.getDepartmentName().equals(newName)
                && departmentRepository.existsByDepartmentName(newName)) {

            throw new DuplicateDepartmentException(
                    "Department already exists: " + newName
            );
        }

        existingDepartment.setDepartmentName(newName);

        existingDepartment.setDescription(
                updatedDepartment.getDescription()
        );

        return departmentRepository.save(existingDepartment);
    }

    @Override
    @Transactional
    public void deleteDepartment(Long id) {

        Department department = getDepartmentById(id);

        departmentRepository.delete(department);
    }
}
