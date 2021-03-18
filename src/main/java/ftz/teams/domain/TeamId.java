package ftz.teams.domain;

import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@EqualsAndHashCode(of = "id")
public class TeamId implements Serializable {

    @Column(name = "team_id")
    private final UUID id;

    public TeamId(){
        this.id = UUID.randomUUID();
    }

    public TeamId(UUID id){
        this.id = id;
    }

    public UUID getValue() {
        return id;
    }

    public static TeamId fromString(String id){
        return new TeamId(UUID.fromString(id));
    }

    @Override
    public String toString(){
        return id.toString();
    }
}
