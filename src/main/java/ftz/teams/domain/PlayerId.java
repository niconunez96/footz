package ftz.teams.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class PlayerId implements Serializable {

    @Column(name = "player_id")
    private UUID id;

    public PlayerId(){
        this.id = UUID.randomUUID();
    }

}
