package org.example.licenta.controllers;

import org.example.licenta.dto.DepartmentDto;
import org.example.licenta.exceptions.DepartmentAlreadyExistsException;
import org.example.licenta.exceptions.DepartmentNotFoundException;
import org.example.licenta.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/departments")
    public List<DepartmentDto> getDepartments() throws DepartmentNotFoundException {
        return departmentService.getDepartments();
    }

    @GetMapping("/departments/{id}")
    public DepartmentDto getDepartmentById(@PathVariable String id) throws DepartmentNotFoundException {
        return departmentService.getDepartmentById(id);
    }

    @DeleteMapping("/departments/{id}/delete")
    public void deleteDepartment(@PathVariable String id) throws DepartmentNotFoundException {
        departmentService.deleteDepartment(id);
    }

    @PostMapping("/departments/create")
    public void createDepartment(@RequestBody DepartmentDto departmentDto) throws DepartmentAlreadyExistsException {
        departmentService.createDepartment(departmentDto);
    }

    @PutMapping("/departments/{id}/update")
    public DepartmentDto updateDepartment(@RequestBody DepartmentDto departmentDto, @PathVariable String id) throws DepartmentNotFoundException {
        return departmentService.updateDepartment(departmentDto, id);
    }
}
