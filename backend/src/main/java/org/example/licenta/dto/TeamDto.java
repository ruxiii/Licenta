package org.example.licenta.dto;

import lombok.Data;
import org.example.licenta.db.entities.enums.DepartmentTeams;

import java.util.List;

@Data
public class TeamDto {
    private DepartmentTeams teamId;

    private DepartmentDto departmentDto;

    private List<UserDto> users;
}
