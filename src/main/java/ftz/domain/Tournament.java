package ftz.domain;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Tournament {

    private Set<Match> matches;

    public Tournament(){}

    public Tournament(Set<Match> matches){
        this.matches = matches;
    }

    public Set<Match> matches(){
        return matches;
    }

    private Phase lastPhasePlayed() {
        return matches.stream()
                .filter(Match::hasEnded)
                .max(Comparator.comparing(Match::phase))
                .map(Match::phase)
                .orElse(Phase.SIXTEENTHS);
    }

    private Set<Team> obtainWinnersFromPhase(Phase phase) {
        return matches.stream()
                .filter(match -> match.phase().equals(phase))
                .map(Match::winner)
                .collect(Collectors.toSet());
    }

    public void createNewPlayoffs(Date newPlayoffDate, Phase phase) {
        List<Team> winners = new ArrayList<>(obtainWinnersFromPhase(lastPhasePlayed()));
        Set<Match> newMatches = Stream.iterate(0, n -> n + 2)
                .limit(winners.size() / 2)
                .map(index -> new Match(newPlayoffDate, phase, winners.get(index), winners.get(index + 1)))
                .collect(Collectors.toSet());
        matches.addAll(newMatches);
    }

    public void startTournament(Set<Team> teamsToEnroll){
        List<Team> teams = new ArrayList<>(teamsToEnroll);
        this.matches = Stream.iterate(0, n -> n + 2)
                .limit(teams.size() / 2)
                .map(index -> new Match(new Date(), Phase.SIXTEENTHS, teams.get(index), teams.get(index + 1)))
                .collect(Collectors.toSet());
    }
}
