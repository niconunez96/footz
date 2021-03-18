package ftz.tournament.domain;

import ftz.teams.domain.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class TestMatch {

    @Test
    public void itMatchHasNotEndedWhenThereIsNoResultRecorded() {
        Team team1 = new Team();
        Team team2 = new Team();
        Match match = new Match(new Date(), Phase.FINAL, team1, team2);

        assertFalse(match.hasEnded());
    }

    @Test
    public void itMatchHasEndedWhenThereIsAResult() {
        Team team1 = new Team();
        Team team2 = new Team();
        Match match = new Match(new Date(), Phase.FINAL, team1, team2);

        match.recordMatchResult(2, 1);

        assertTrue(match.hasEnded());
    }

    @Test
    public void itShouldRaiseMatchDidNotEndedWhenThereIsNoWinner(){
        Team team1 = new Team();
        Team team2 = new Team();
        Match match = new Match(new Date(), Phase.FINAL, team1, team2);

        assertThrows(RuntimeException.class, match::winner);
    }

    @Test
    public void itShouldReturnLocalTeamWhenLocalHasMoreGoalsThanVisitor(){
        Team team1 = new Team();
        Team team2 = new Team();
        Match match = new Match(new Date(), Phase.FINAL, team1, team2);

        match.recordMatchResult(2, 1);

        assertEquals(team1, match.winner());
    }
}
