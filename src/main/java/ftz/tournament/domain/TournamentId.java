package ftz.tournament.domain;

import java.util.UUID;

public class TournamentId {

    private UUID id;

    public TournamentId(){}

    @Override
    public String toString(){
        return id.toString();
    }

}
