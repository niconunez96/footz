package ftz.teams.domain;

import javax.persistence.*;

@Entity
@Table(name = "players")
public class Player {

    @EmbeddedId
    private PlayerId id;
    @ManyToOne
    @JoinColumn(name = "player_metadata_id")
    private PlayerMetadata playerMetadata;
    @Column
    private Integer shirtNumber;

    public Player() {
    }

    public Player(PlayerId id, PlayerMetadata playerMetadata, Integer shirtNumber) {
        this.id = id;
        this.playerMetadata = playerMetadata;
        this.shirtNumber = shirtNumber;
    }

    public String email() {
        return playerMetadata.email();
    }
}
