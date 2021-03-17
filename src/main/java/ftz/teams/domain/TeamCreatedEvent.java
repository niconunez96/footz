package ftz.teams.domain;

import shared.domain.DomainEvent;

public class TeamCreatedEvent extends DomainEvent {

    private String teamName;

    public TeamCreatedEvent(String aggregateId, String teamName){
        super(aggregateId);
        this.teamName = teamName;
    }

    public String getTeamName(){
        return teamName;
    }
}
