package ftz.teams.infrastructure;

import config.DatabaseConfigTesting;
import config.RepositoryConfigTesting;
import ftz.teams.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import server.FtzApplication;

import javax.persistence.EntityManagerFactory;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ContextConfiguration(classes = {DatabaseConfigTesting.class, RepositoryConfigTesting.class})
public class TestTeamMySQLRepository {

    @Autowired
    TeamMySQLRepository teamRepository;
    @Autowired
    PlayerMySQLRepository playerRepository;
    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Test
    public void itShouldSaveTeamIntoDatabase() {
        TeamId id = new TeamId();
        Team team = new Team(id, "Test team", new HashSet<>());

        teamRepository.store(team);

        Team teamSaved = teamRepository.findOne(id).get();
        assertThat(teamSaved.name()).isEqualTo("Test team");
    }

    @Test
    public void itShouldReturnEmptyWhenTeamIdDoesNotExist() {
        Optional<Team> teamSaved = teamRepository.findOne(new TeamId());

        assertThatThrownBy(teamSaved::get).isInstanceOf(NoSuchElementException.class);
    }

    private Player givenAPlayer() {
        PlayerId id = new PlayerId();
        Player player = new Player(id, "john@mail.com", "12345", "John");
        playerRepository.store(player);
        return player;
    }

    @Test
    public void itShouldStoreTeamPlayerInfosWithTeam() {
        Player player = givenAPlayer();

        Set<TeamPlayerInfo> teamPlayerInfos = new HashSet<>() {{
            add(new TeamPlayerInfo(player, 1));
            add(new TeamPlayerInfo(player, 10));
        }};
        TeamId id = new TeamId();
        Team team = new Team(id, "Test team", teamPlayerInfos);

        teamRepository.store(team);

        List teamPlayerInfosSaved = entityManagerFactory.createEntityManager()
                .createNativeQuery("SELECT * from team_player_infos").getResultList();
        assertThat(teamPlayerInfosSaved.size()).isEqualTo(2);
    }
}
