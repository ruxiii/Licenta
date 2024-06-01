package org.example.licenta.services;

import org.example.licenta.db.entities.DepartmentEntity;
import org.example.licenta.db.repositories.DepartmentRepository;
import org.example.licenta.dto.DepartmentDto;
import org.example.licenta.exceptions.DepartmentAlreadyExistsException;
import org.example.licenta.exceptions.DepartmentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<DepartmentDto> getDepartments() throws NullPointerException {
        if (departmentRepository.findAll().isEmpty()) {
            throw new NullPointerException("No departments found");
        }
        else {
            List<DepartmentEntity> departments = departmentRepository.findAll();
            List<DepartmentDto> departmentDtos = new ArrayList<>();
            for (DepartmentEntity departmentEntity : departments) {
                DepartmentDto departmentDto = new DepartmentDto();
                departmentDto.setDepartmentId(departmentEntity.getDepartmentId());
                departmentDto.setDepartmentName(departmentEntity.getDepartmentName());
                departmentDtos.add(departmentDto);
            }
            return departmentDtos;
        }
    }

    public DepartmentDto getDepartmentById(String id) throws DepartmentNotFoundException {
        Optional<DepartmentEntity> department = departmentRepository.findById(id);
        if (department.isPresent()) {
            DepartmentDto departmentDto = new DepartmentDto();
            departmentDto.setDepartmentId(department.get().getDepartmentId());
            departmentDto.setDepartmentName(department.get().getDepartmentName());
            return departmentDto;
        } else {
            throw new DepartmentNotFoundException("Department not found");
        }
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
            DepartmentEntity departmentEntity = new DepartmentEntity();
            departmentEntity.setDepartmentId(departmentDto.getDepartmentId().toUpperCase());
            departmentEntity.setDepartmentName(departmentDto.getDepartmentName());
            departmentRepository.save(departmentEntity);
        }
    }

    public DepartmentDto updateDepartment(DepartmentDto departmentDto, String id) throws DepartmentNotFoundException {
        if (!departmentRepository.existsById(id)) {
            throw new DepartmentNotFoundException("Department not found");
        }
        else {
            DepartmentEntity departmentEntity = departmentRepository.findById(id).get();
            departmentEntity.setDepartmentId(departmentDto.getDepartmentId());
            departmentEntity.setDepartmentName(departmentDto.getDepartmentName());
            departmentRepository.save(departmentEntity);

            DepartmentDto updatedDepartmentDto = new DepartmentDto();
            updatedDepartmentDto.setDepartmentId(departmentEntity.getDepartmentId());
            updatedDepartmentDto.setDepartmentName(departmentEntity.getDepartmentName());
            return updatedDepartmentDto;
        }
    }
}
