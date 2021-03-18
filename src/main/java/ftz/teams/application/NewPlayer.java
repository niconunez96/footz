package ftz.teams.application;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public final class NewPlayer{
    private String identification;
    private String name;
    private Integer shirtNumber;
    private String email;
}
