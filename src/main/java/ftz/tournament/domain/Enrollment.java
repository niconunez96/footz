package ftz.tournament.domain;

import ftz.teams.domain.Team;

public class Enrollment {

    Team team;

    public Enrollment(Team team){
        this.team = team;
    }
    public Team team(){ return team; }
}
