package ftz.teams.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class TeamId implements Serializable {

    @Column(name = "team_id")
    private UUID id;

    public TeamId(){
        this.id = UUID.randomUUID();
    }

    public TeamId(UUID id){
        this.id = id;
    }

    public UUID getValue() {
        return id;
    }
}
