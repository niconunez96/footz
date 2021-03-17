package ftz.domain;

import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "teams")
@ToString(exclude = "id")
public class Team {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String name;

    public Team(){}

    public Team(String name) {
        this.name = name;
    }

    public String name(){ return name; }
}
