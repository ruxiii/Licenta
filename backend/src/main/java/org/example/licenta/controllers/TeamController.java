package org.example.licenta.controllers;

import org.example.licenta.dto.TeamDto;
import org.example.licenta.exceptions.DepartmentNotFoundException;
import org.example.licenta.exceptions.TeamAlreadyExistsException;
import org.example.licenta.exceptions.TeamNotFoundException;
import org.example.licenta.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping("/teams")
    public List<TeamDto> getTeams() throws TeamNotFoundException {
        return teamService.getTeams();
    }

    @GetMapping("/teams/{id}")
    public TeamDto getTeamById(@PathVariable String id) throws TeamNotFoundException {
        return teamService.getTeamById(id);
    }

    @DeleteMapping("/teams/{id}/delete")
    public void deleteTeam(@PathVariable String id) throws TeamNotFoundException {
        teamService.deleteTeam(id);
    }

    @PostMapping("/teams/create")
    public void createTeam(@RequestBody TeamDto teamDto) throws TeamAlreadyExistsException, DepartmentNotFoundException {
        teamService.createTeam(teamDto);
    }
}
