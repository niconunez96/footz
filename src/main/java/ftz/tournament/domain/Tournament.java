package ftz.tournament.domain;

import ftz.teams.domain.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.Clock;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ftz.tournament.domain.Phase.*;

@Builder
@AllArgsConstructor
public class Tournament {

    private TournamentId id;
    private Set<Enrollment> enrollments;
    private Set<Match> matches;

    public Tournament(){
        enrollments = new HashSet<>();
        matches = new HashSet<>();
    }

    public Tournament(TournamentId id){
        this.id = id;
    }

    public Set<Match> matches(){
        return matches;
    }

    public Set<Enrollment> enrollments(){ return enrollments; }

    private Phase lastPhasePlayed() {
        return matches.stream()
                .filter(Match::hasEnded)
                .max(Comparator.comparing(Match::phase))
                .map(Match::phase)
                .orElse(SIXTEENTHS);
    }

    private Set<Team> obtainWinnersFromPhase(Phase phase) {
        return matches.stream()
                .filter(match -> match.phase().equals(phase))
                .map(Match::winner)
                .collect(Collectors.toSet());
    }

    public void createNewPlayoffs(LocalDate newPlayoffDate) {
        Phase lastPhasePlayed = lastPhasePlayed();

        List<Team> winners = new ArrayList<>(obtainWinnersFromPhase(lastPhasePlayed));
        Set<Match> newMatches = Stream.iterate(0, n -> n + 2)
                .limit(winners.size() / 2)
                .map(index -> new Match(newPlayoffDate, lastPhasePlayed.nextPhase(), winners.get(index), winners.get(index + 1)))
                .collect(Collectors.toSet());
        matches.addAll(newMatches);
    }

    public void startTournament(){
        List<Team> teams = enrollments.stream().map(Enrollment::team).collect(Collectors.toList());
        this.matches = Stream.iterate(0, n -> n + 2)
                .limit(teams.size() / 2)
                .map(index -> new Match(LocalDate.now(Clock.systemUTC()), SIXTEENTHS, teams.get(index), teams.get(index + 1)))
                .collect(Collectors.toSet());
    }

    public void enrollTeam(Team team){
        this.enrollments.add(new Enrollment(team));
    }
}
