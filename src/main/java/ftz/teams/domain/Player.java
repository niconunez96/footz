package ftz.teams.domain;

import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "players")
@EqualsAndHashCode(of = {"id", "email", "identification"})
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
