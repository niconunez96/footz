package ftz.tournament.infrastructure;

import ftz.teams.domain.Team;
import ftz.teams.domain.TeamId;
import ftz.teams.infrastructure.TeamMySQLRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import server.FtzApplication;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ContextConfiguration(classes = FtzApplication.class)
public class TestTeamRepository {

    @Autowired
    TeamMySQLRepository teamRepository;

    @Test
    public void itShouldSaveTeamIntoDatabase(){
        Team team = new Team(new TeamId(), "Test team");

        teamRepository.store(team);

        Team teamSaved = teamRepository.findOne(1L).get();
        assertThat(teamSaved.name()).isEqualTo("Test team");
    }

    @Test
    public void itShouldReturnEmptyWhenTeamIdDoesNotExist(){
        Optional<Team> teamSaved = teamRepository.findOne(-1L);

        Assertions.assertThatThrownBy(teamSaved::get).isInstanceOf(NoSuchElementException.class);
    }
}
