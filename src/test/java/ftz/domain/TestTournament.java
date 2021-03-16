package ftz.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

public class TestTournament {

    private Match match1;
    private Match match2;
    private Match match3;
    private Match match4;

    @BeforeEach
    public void setUpMethod(){
        Team team1 = new Team();
        Team team2 = new Team();
        Team team3 = new Team();
        Team team4 = new Team();
        Team team5 = new Team();
        Team team6 = new Team();
        Team team7 = new Team();
        Team team8 = new Team();
        this.match1 = new Match(new Date(), Phase.QUARTERS, team1, team2);
        this.match2 = new Match(new Date(), Phase.QUARTERS, team3, team4);
        this.match3 = new Match(new Date(), Phase.QUARTERS, team5, team6);
        this.match4 = new Match(new Date(), Phase.QUARTERS, team7, team8);
        this.match1.recordMatchResult(2, 1);
        this.match2.recordMatchResult(2, 1);
        this.match3.recordMatchResult(2, 1);
        this.match4.recordMatchResult(2, 1);
    }

    @Test
    public void testCreateNewPlayoffsShouldCreateMatchesWithPhaseSpecified(){
        Set<Match> newMatches = new HashSet<>(){{
            add(match1);
            add(match2);
            add(match3);
            add(match4);
        }};

        Tournament tournament = new Tournament(newMatches);

        tournament.createNewPlayoffs(new Date(), Phase.SEMIFINAL);

        Set<Match> matches = tournament.matches();

        assertThat(matches).map(Match::phase).containsAnyOf(Phase.SEMIFINAL);
    }

    @Test
    public void testCreateNewPlayoffsShouldCreateMatchesWithWinnersFromTheLastPhase(){

    }
}
