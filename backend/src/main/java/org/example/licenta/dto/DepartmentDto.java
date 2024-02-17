package org.example.licenta.dto;

import lombok.Data;

import java.util.List;

@Data
public class DepartmentDto {
    private String departmentId;

    private String departmentName;

    private MapDto mapDto;

    private List<TeamDto> teams;
}
