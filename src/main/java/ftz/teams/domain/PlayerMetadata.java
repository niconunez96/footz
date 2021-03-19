package ftz.teams.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "players_metadata")
@EqualsAndHashCode(of = {"email", "identification"})
public class PlayerMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column
    private String email;
    @Column
    private String identification;
    @Column
    private String name;

    public PlayerMetadata(){}

    public PlayerMetadata(String email, String identification, String name) {
        this.email = email;
        this.identification = identification;
        this.name = name;
    }

    public String email() {
        return email;
    }

    public String identification(){ return identification; }

    public String name(){ return name; }
}
