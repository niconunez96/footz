package ftz.tournament.domain;

import ftz.teams.domain.Team;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Match {

    private Phase phase;
    private LocalDate date;
    private Team local;
    private Team visitor;
    private Integer goalsLocal = null;
    private Integer goalsVisitor = null;

    public Match(LocalDate date, Phase phase, Team local, Team visitor) {
        this.date = date;
        this.phase = phase;
        this.local = local;
        this.visitor = visitor;
    }

    public LocalDate date() {
        return date;
    }

    public Phase phase() {
        return phase;
    }

    public Set<Team> teams(){
        return new HashSet<>(){{
            add(local);
            add(visitor);
        }};
    }
    public Team winner() {
        if (!hasEnded())
            throw new RuntimeException("No winner until match ends");
        else
            return goalsLocal > goalsVisitor ? local : visitor;
    }

    public void recordMatchResult(Integer goalsLocal, Integer goalsVisitor) {
        this.goalsLocal = goalsLocal;
        this.goalsVisitor = goalsVisitor;
    }

    public boolean hasEnded() {
        return goalsLocal != null && goalsVisitor != null;
    }
}
