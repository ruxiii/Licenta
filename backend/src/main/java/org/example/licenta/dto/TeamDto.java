package org.example.licenta.dto;

import lombok.Data;

import java.util.List;

@Data
public class TeamDto {
    private String teamId;

    private DepartmentDto departmentDto;

    private List<UserDto> users;
}
