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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        if (id != null ? !id.equals(player.id) : player.id != null) return false;
        if (email != null ? !email.equals(player.email) : player.email != null) return false;
        return identification != null ? identification.equals(player.identification) : player.identification == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (identification != null ? identification.hashCode() : 0);
        return result;
    }

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
