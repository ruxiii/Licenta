package org.example.licenta.services;

import org.example.licenta.db.entities.DepartmentEntity;
import org.example.licenta.db.entities.TeamEntity;
import org.example.licenta.db.repositories.DepartmentRepository;
import org.example.licenta.db.repositories.TeamRepository;
import org.example.licenta.dto.TeamDto;
import org.example.licenta.exceptions.DepartmentNotFoundException;
import org.example.licenta.exceptions.TeamAlreadyExistsException;
import org.example.licenta.exceptions.TeamNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    public List<TeamDto> getTeams() throws TeamNotFoundException {
        if (teamRepository.findAll().isEmpty()) {
            throw new TeamNotFoundException("No teams found");
        }
        else {
            List<TeamEntity> teams = teamRepository.findAll();
            List<TeamDto> teamDtos = new ArrayList<>();
            for (TeamEntity teamEntity : teams) {
                DepartmentEntity departmentEntity = teamEntity.getDepartmentEntity();
                String departmentId = departmentEntity.getDepartmentId();
                TeamDto teamDto = new TeamDto();
                teamDto.setTeamId(teamEntity.getTeamId());
                teamDto.setTeamName(teamEntity.getTeamName());
                teamDto.setDepartmentId(departmentId);
                teamDtos.add(teamDto);
            }
            return teamDtos;
        }
    }

    public TeamDto getTeamById(String id) throws TeamNotFoundException {
        Optional<TeamEntity> team = teamRepository.findById(id);
        if (team.isEmpty()) {
            throw new TeamNotFoundException("Team not found");
        }
        else {
            TeamEntity teamEntity = team.get();
            DepartmentEntity departmentEntity = teamEntity.getDepartmentEntity();
            String departmentId = departmentEntity.getDepartmentId();
            TeamDto teamDto = new TeamDto();
            teamDto.setTeamId(teamEntity.getTeamId());
            teamDto.setTeamName(teamEntity.getTeamName());
            teamDto.setDepartmentId(departmentId);
            return teamDto;
        }
    }

    public void deleteTeam(String id) throws TeamNotFoundException {
        if (teamRepository.existsById(id)) {
            teamRepository.deleteById(id);
        }
        else {
            throw new TeamNotFoundException("Team not found");
        }
    }

    public void createTeam(TeamDto teamDto) throws TeamAlreadyExistsException, DepartmentNotFoundException {
        if (teamRepository.existsById(teamDto.getTeamId())) {
            throw new TeamAlreadyExistsException("Team already exists");
        }
        else {
            if (!departmentRepository.existsById(teamDto.getDepartmentId())) {
                throw new DepartmentNotFoundException("Department not found");
            }
            else {
                DepartmentEntity departmentEntity = departmentRepository.findById(teamDto.getDepartmentId()).get();
                TeamEntity teamEntity = new TeamEntity();
                teamEntity.setTeamId(teamDto.getTeamId().toUpperCase());
                teamEntity.setTeamName(teamDto.getTeamName());
                teamEntity.setDepartmentEntity(departmentEntity);
                teamRepository.save(teamEntity);
            }
        }
    }

    public TeamDto updateTeam(String id, TeamDto teamDto) throws TeamNotFoundException, DepartmentNotFoundException {
        if (!teamRepository.existsById(id)) {
            throw new TeamNotFoundException("Team not found");
        } else {
            if (!departmentRepository.existsById(teamDto.getDepartmentId())) {
                throw new DepartmentNotFoundException("Department not found");
            } else {
                DepartmentEntity departmentEntity = departmentRepository.findById(teamDto.getDepartmentId()).get();
                TeamEntity teamEntity = teamRepository.findById(id).get();
                teamEntity.setTeamName(teamDto.getTeamName());
                teamEntity.setTeamName(teamDto.getTeamName());
                teamEntity.setDepartmentEntity(departmentEntity);
                teamRepository.save(teamEntity);

                TeamDto updatedTeamDto = new TeamDto();
                updatedTeamDto.setTeamId(teamEntity.getTeamId());
                updatedTeamDto.setTeamName(teamEntity.getTeamName());
                updatedTeamDto.setDepartmentId(departmentEntity.getDepartmentId());
                return updatedTeamDto;
            }
        }
    }
}
