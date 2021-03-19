package ftz.teams.infrastructure;

import config.DatabaseConfigTesting;
import config.RepositoryConfigTesting;
import ftz.teams.domain.PlayerMetadata;
import ftz.teams.domain.Team;
import ftz.teams.domain.TeamId;
import ftz.teams.domain.TeamPlayerInfo;
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
    TeamPlayerInfoMySQLRepository teamPlayerInfoMySQLRepository;

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

        Set<TeamPlayerInfo> teamPlayerInfos = new HashSet<>() {{
            add(new TeamPlayerInfo(playerMetadata, 1));
            add(new TeamPlayerInfo(playerMetadata, 10));
        }};
        TeamId id = new TeamId();
        Team team = new Team(id, "Test team", teamPlayerInfos);

        teamRepository.store(team);
        List<TeamPlayerInfo> teamPlayerInfosSaved = teamPlayerInfoMySQLRepository.findByTeamId(id);
        assertThat(teamPlayerInfosSaved.size()).isEqualTo(2);
    }
}
