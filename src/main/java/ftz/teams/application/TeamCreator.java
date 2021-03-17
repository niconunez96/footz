package ftz.teams.application;

import ftz.teams.domain.Team;
import ftz.teams.domain.TeamId;
import ftz.teams.domain.TeamRepository;
import org.springframework.stereotype.Service;

@Service
public class TeamCreator {

    private TeamRepository teamRepository;

    public TeamCreator(TeamRepository teamRepository){
        this.teamRepository = teamRepository;
    }

    public void createTeam(String name){
        Team newTeam = new Team(new TeamId(), name);
        teamRepository.store(newTeam);
    }
}
