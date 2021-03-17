package ftz.teams.application;

import ftz.teams.domain.Team;
import ftz.teams.domain.TeamCreatedEvent;
import ftz.teams.domain.TeamId;
import ftz.teams.domain.TeamRepository;
import org.springframework.stereotype.Service;
import shared.domain.EventBus;

@Service
public class TeamCreator {

    private TeamRepository teamRepository;
    private EventBus eventBus;

    public TeamCreator(TeamRepository teamRepository, EventBus eventBus){
        this.teamRepository = teamRepository;
        this.eventBus = eventBus;
    }

    public void createTeam(String name){
        TeamId teamId = new TeamId();
        Team newTeam = new Team(teamId, name);
        teamRepository.store(newTeam);

        eventBus.publish(new TeamCreatedEvent(teamId.getValue().toString(), name));
    }
}
