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
    private Player player;
    @Column
    private Integer shirtNumber;

    public TeamPlayerInfo() {
    }

    public TeamPlayerInfo(Player player, Integer shirtNumber) {
        this.player = player;
        this.shirtNumber = shirtNumber;
    }

    public String email() {
        return player.email();
    }
}
