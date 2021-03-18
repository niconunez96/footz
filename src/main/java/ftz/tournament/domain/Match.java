package ftz.tournament.domain;

import ftz.teams.domain.Team;

import java.util.Date;

public class Match {

    private Phase phase;
    private Date date;
    private Team local;
    private Team visitor;
    private Integer goalsLocal = null;
    private Integer goalsVisitor = null;

    public Match(Date date, Phase phase, Team local, Team visitor) {
        this.date = date;
        this.phase = phase;
        this.local = local;
        this.visitor = visitor;
    }

    public Date date() {
        return date;
    }

    public Phase phase() {
        return phase;
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
