package ftz.teams.domain;

import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "teams")
@ToString(exclude = "id")
public class Team {

    @EmbeddedId
    private TeamId id;

    @Column
    private String name;

    public Team(){}

    public Team(TeamId id, String name) {

        this.name = name;
        this.id = id;
    }

    public String name(){ return name; }

    public TeamId id(){ return id; }
}
