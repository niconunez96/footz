package ftz.teams.infrastructure;

import config.DatabaseConfigTesting;
import config.RepositoryConfigTesting;
import ftz.teams.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ContextConfiguration(classes = {DatabaseConfigTesting.class, RepositoryConfigTesting.class})
public class TestTeamMySQLRepository {

    @Autowired
    TeamMySQLRepository teamRepository;
    @Autowired
    PlayerMetadataMySQLRepository playerRepository;
    @Autowired
    PlayerMySQLRepository teamPlayerInfoMySQLRepository;

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

    private PlayerMetadata givenAPlayer() {
        PlayerMetadata playerMetadata = new PlayerMetadata("john@mail.com", "12345", "John");
        playerRepository.store(playerMetadata);
        return playerMetadata;
    }

    @Test
    public void itShouldStoreTeamPlayerInfosWithTeam() {
        PlayerMetadata playerMetadata = givenAPlayer();

        Set<Player> players = new HashSet<>() {{
            add(new Player(new PlayerId(), playerMetadata, 1));
            add(new Player(new PlayerId(), playerMetadata, 10));
        }};
        TeamId id = new TeamId();
        Team team = new Team(id, "Test team", players);

        teamRepository.store(team);
        List<Player> playerInfosSaved = teamPlayerInfoMySQLRepository.findByTeamId(id);
        assertThat(playerInfosSaved.size()).isEqualTo(2);
    }
}
