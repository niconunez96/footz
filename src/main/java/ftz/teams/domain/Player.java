package ftz.teams.domain;

import javax.persistence.*;

@Entity
@Table(name = "players")
public class Player {

    @EmbeddedId
    private PlayerId id;
    @Column
    private String email;
    @Column
    private String identification;
    @Column
    private String name;

    public Player(){}

    public Player(PlayerId id, String email, String identification, String name) {
        this.id = id;
        this.email = email;
        this.identification = identification;
        this.name = name;
    }

    public String email() {
        return email;
    }
}
