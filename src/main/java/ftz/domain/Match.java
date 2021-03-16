package ftz.domain;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Match match = (Match) o;

        if (phase != match.phase) return false;
        if (!local.equals(match.local)) return false;
        return visitor.equals(match.visitor);
    }

    @Override
    public int hashCode() {
        int result = phase.hashCode();
        result = 31 * result + local.hashCode();
        result = 31 * result + visitor.hashCode();
        return result;
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
