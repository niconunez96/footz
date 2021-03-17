package ftz.teams.domain;

import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "teams")
@ToString(exclude = "id")
public class Team {

    @EmbeddedId
    private TeamId id;
    @Column
    private String name;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "team_id")
    private Set<TeamPlayerInfo> teamPlayerInfos;

    public Team(){}

    public Team(TeamId id, String name, Set<TeamPlayerInfo> teamPlayerInfos) {
        this.id = id;
        this.name = name;
        this.teamPlayerInfos = teamPlayerInfos;
    }

    public String name(){ return name; }

    public TeamId id(){ return id; }
}
