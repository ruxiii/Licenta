package org.example.licenta;

import org.example.licenta.db.entities.DepartmentEntity;
import org.example.licenta.db.repositories.DepartmentRepository;
import org.example.licenta.dto.DepartmentDto;
import org.example.licenta.exceptions.DepartmentAlreadyExistsException;
import org.example.licenta.exceptions.DepartmentNotFoundException;
import org.example.licenta.services.DepartmentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DepartmentServiceTest {

    private DepartmentService departmentService;

    @Mock
    private DepartmentRepository departmentRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        departmentRepository = Mockito.mock(DepartmentRepository.class);
        departmentService = new DepartmentService(departmentRepository);
    }

    private DepartmentEntity createDepartmentEntity(){
        DepartmentEntity departmentEntity = new DepartmentEntity();
        departmentEntity.setDepartmentId("HR");
        departmentEntity.setDepartmentName("Human Resources");
        return departmentEntity;
    }

    private DepartmentDto createDepartmentDto(){
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setDepartmentId("HR");
        departmentDto.setDepartmentName("Human Resources");
        return departmentDto;
    }

    @Test
    public void getDepartmentsTest_no_Departments_found(){
        when(departmentRepository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(NullPointerException.class, () -> departmentService.getDepartments());
    }

    @Test
    public void getDepartmentByIdTest_departments_found(){
        DepartmentEntity departmentEntity =  createDepartmentEntity();
        List<DepartmentEntity> departmentEntities = new ArrayList<>();
        departmentEntities.add(departmentEntity);

        when(departmentRepository.findAll()).thenReturn(departmentEntities);

        departmentService.getDepartments();

        Assertions.assertEquals(departmentService.getDepartments().size(), 1);
        Assertions.assertEquals(departmentService.getDepartments().get(0).getDepartmentId(), "HR");
        Assertions.assertEquals(departmentService.getDepartments().get(0).getDepartmentName(), "Human Resources");
    }

    @Test
    public void getDepartmentByIdTest_department_not_found() {
        when(departmentRepository.findById("HR")).thenReturn(Optional.empty());
        assertThrows(DepartmentNotFoundException.class, () -> departmentService.getDepartmentById("HR"));
    }

    @Test
    public void getDepartmentByIdTest_department_found() throws DepartmentNotFoundException {
        DepartmentEntity departmentEntity =  createDepartmentEntity();

        when(departmentRepository.findById(any())).thenReturn(Optional.of(departmentEntity));

        departmentService.getDepartmentById(any());

        assertEquals(departmentService.getDepartmentById(any()).getDepartmentId(), "HR");
        assertEquals(departmentService.getDepartmentById(any()).getDepartmentName(), "Human Resources");
    }

    @Test
    public void deleteDepartmentTest_department_not_found() {
        when(departmentRepository.existsById(any())).thenReturn(false);
        assertThrows(DepartmentNotFoundException.class, () -> departmentService.deleteDepartment(any()));
    }

    @Test
    public void deleteDepartmentTest_department_found() throws DepartmentNotFoundException {
        when(departmentRepository.existsById(any())).thenReturn(true);

        departmentService.deleteDepartment(any());

        Mockito.verify(departmentRepository, Mockito.times(1)).deleteById(any());
    }

    @Test
    public void createDepartmentTest_department_already_exists() {
        DepartmentDto departmentDto = createDepartmentDto();

        when(departmentRepository.existsById("HR")).thenReturn(true);

        assertThrows(DepartmentAlreadyExistsException.class, () -> departmentService.createDepartment(departmentDto));
    }

    @Test
    public void createDepartmentTest_department_does_not_exist() throws DepartmentAlreadyExistsException {
        DepartmentDto departmentDto = createDepartmentDto();

        when(departmentRepository.existsById("HR")).thenReturn(false);

        departmentService.createDepartment(departmentDto);

        Mockito.verify(departmentRepository, Mockito.times(1)).save(any());
    }

    @Test
    public void updateDepartmentTest_department_not_found() {
        DepartmentDto departmentDto = createDepartmentDto();

        when(departmentRepository.existsById("HR")).thenReturn(false);

        assertThrows(DepartmentNotFoundException.class, () -> departmentService.updateDepartment(departmentDto, "HR"));
    }

    @Test
    public void updateDepartmentTest_department_found() throws DepartmentNotFoundException {
        DepartmentDto departmentDto = createDepartmentDto();

        DepartmentEntity departmentEntity = createDepartmentEntity();

        when(departmentRepository.existsById("HR")).thenReturn(true);
        when(departmentRepository.findById("HR")).thenReturn(Optional.of(departmentEntity));

        departmentService.updateDepartment(departmentDto, "HR");

        Mockito.verify(departmentRepository, Mockito.times(1)).save(any());
    }
}
