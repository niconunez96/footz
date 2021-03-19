package ftz.teams.domain;

import javax.persistence.*;

@Entity
@Table(name = "team_player_infos")
public class TeamPlayerInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @OneToOne
    @JoinColumn(name = "player_id")
    private PlayerMetadata playerMetadata;
    @Column
    private Integer shirtNumber;

    public TeamPlayerInfo() {
    }

    public TeamPlayerInfo(PlayerMetadata playerMetadata, Integer shirtNumber) {
        this.playerMetadata = playerMetadata;
        this.shirtNumber = shirtNumber;
    }

    public String email() {
        return playerMetadata.email();
    }
}
