package ftz.tournament.domain;

import ftz.teams.domain.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static ftz.tournament.domain.Phase.SEMIFINAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class TestTournament {

    private Match match1;
    private Match match2;
    private Match match3;
    private Match match4;

    @BeforeEach
    public void setUpMethod(){
        Team winneMatch1 = new Team();
        Team team2 = new Team();
        Team winnerMatch2 = new Team();
        Team team4 = new Team();
        Team winnerMatch3 = new Team();
        Team team6 = new Team();
        Team winnerMatch4 = new Team();
        Team team8 = new Team();
        this.match1 = new Match(LocalDate.now(), Phase.QUARTERS, winneMatch1, team2);
        this.match2 = new Match(LocalDate.now(), Phase.QUARTERS, winnerMatch2, team4);
        this.match3 = new Match(LocalDate.now(), Phase.QUARTERS, winnerMatch3, team6);
        this.match4 = new Match(LocalDate.now(), Phase.QUARTERS, winnerMatch4, team8);
        this.match1.recordMatchResult(2, 1);
        this.match2.recordMatchResult(2, 1);
        this.match3.recordMatchResult(2, 1);
        this.match4.recordMatchResult(2, 1);
    }

    @Test
    public void itShouldCreateNewPlayoffsShouldCreateMatchesWithPhaseSpecified(){
        Set<Match> newMatches = new HashSet<>(){{
            add(match1);
            add(match2);
            add(match3);
            add(match4);
        }};

        Tournament tournament = Tournament.builder().matches(newMatches).build();

        tournament.createNewPlayoffs(LocalDate.now());

        Set<Match> matches = tournament.matches();

        assertThat(matches).map(Match::phase).containsAnyOf(SEMIFINAL);
        assertThat(matches).filteredOn(match -> match.phase().equals(SEMIFINAL)).size().isEqualTo(2);
    }

    @Test
    public void itShouldEnrollANewTeam(){
        Tournament tournament = new Tournament();

        tournament.enrollTeam(mock(Team.class));
        tournament.enrollTeam(mock(Team.class));
        tournament.enrollTeam(mock(Team.class));

        assertThat(tournament.enrollments().size()).isEqualTo(3);
    }

    @Test
    public void itShouldStartTournamentWithTeamsEnrolled(){
        Team team1 = mock(Team.class);
        Team team2 = mock(Team.class);
        Team team3 = mock(Team.class);
        Team team4 = mock(Team.class);
        Tournament tournament = Tournament.builder()
                .enrollments(new HashSet<>(){{
                    add(new Enrollment(team1));
                    add(new Enrollment(team2));
                    add(new Enrollment(team3));
                    add(new Enrollment(team4));
                }})
                .build();

        tournament.startTournament();

        assertThat(tournament.matches().size()).isEqualTo(2);
    }

    @Test
    public void itShouldCreateNewPlayoffsWithWinnersFromTheLastPhase(){
        Set<Match> newMatches = new HashSet<>(){{
            add(match1);
            add(match2);
            add(match3);
            add(match4);
        }};

        Tournament tournament = Tournament.builder().matches(newMatches).build();

        tournament.createNewPlayoffs(LocalDate.now());

        Set<Match> matches = tournament.matches();
        Set<Team> winners = matches.stream()
                .filter(match -> match.phase().equals(SEMIFINAL))
                .flatMap(match -> match.teams().stream())
                .collect(Collectors.toSet());
        assertThat(winners)
                .contains(
                        match1.winner(),
                        match2.winner(),
                        match3.winner(),
                        match4.winner()
                );
    }
}
