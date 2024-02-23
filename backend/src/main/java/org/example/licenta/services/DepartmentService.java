package org.example.licenta.services;

import org.example.licenta.db.entities.DepartmentEntity;
import org.example.licenta.db.repositories.DepartmentRepository;
import org.example.licenta.dto.DepartmentDto;
import org.example.licenta.exceptions.DepartmentAlreadyExistsException;
import org.example.licenta.exceptions.DepartmentNotFoundException;
import org.example.licenta.mappers.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    private final DepartmentMapper departmentMapper;

    public DepartmentService(DepartmentMapper departmentMapper) {
        this.departmentMapper = departmentMapper;
    }

    public List<DepartmentDto> getDepartments() throws DepartmentNotFoundException {
        if (departmentRepository.findAll().isEmpty()) {
            throw new DepartmentNotFoundException("No departments found");
        }
        else {
            List<DepartmentEntity> departments = departmentRepository.findAll();
            return departments.stream().map(departmentMapper::toDto).collect(Collectors.toList());
        }
    }

    public DepartmentDto getDepartmentById(String id) throws DepartmentNotFoundException {
        Optional<DepartmentEntity> department = departmentRepository.findById(id);
        if (department.isEmpty()) {
            throw new DepartmentNotFoundException("Department not found");
        }
        return departmentMapper.toDto(department.get());
    }

    public void deleteDepartment(String id) throws DepartmentNotFoundException {
        if (departmentRepository.existsById(id)) {
            departmentRepository.deleteById(id);
        }
        else {
            throw new DepartmentNotFoundException("Department not found");
        }
    }

    public void createDepartment(DepartmentDto departmentDto) throws DepartmentAlreadyExistsException {
        if (departmentRepository.existsById(departmentDto.getDepartmentId())) {
            throw new DepartmentAlreadyExistsException("Department already exists");
        }
        else {
            DepartmentEntity departmentEntity = departmentMapper.toEntity(departmentDto);
            departmentRepository.save(departmentEntity);
        }
    }

    public DepartmentDto updateDepartment(DepartmentDto departmentDto, String id) throws DepartmentNotFoundException {
        if (!departmentRepository.existsById(id)) {
            throw new DepartmentNotFoundException("Department not found");
        }
        else {
            DepartmentEntity departmentEntity = departmentRepository.findById(id).get();
            departmentEntity.setDepartmentName(departmentDto.getDepartmentName());
            departmentRepository.save(departmentEntity);
            return departmentMapper.toDto(departmentEntity);
        }
    }
}
