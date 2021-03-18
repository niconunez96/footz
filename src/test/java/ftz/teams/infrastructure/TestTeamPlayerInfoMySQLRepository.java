package ftz.teams.infrastructure;

import config.DatabaseConfigTesting;
import config.RepositoryConfigTesting;
import ftz.teams.domain.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
@ContextConfiguration(classes = {DatabaseConfigTesting.class, RepositoryConfigTesting.class})
public class TestTeamPlayerInfoMySQLRepository {

    @Autowired
    TeamPlayerInfoMySQLRepository teamPlayerInfoMySQLRepository;
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    TeamRepository teamRepository;

    private Player givenAPlayer(){
        Player player = new Player(new PlayerId(), "test@mail.com", "1", "john");
        playerRepository.store(player);
        return player;
    }

    @Test
    public void itShouldReturnTeamPlayerInfosRelatedWithTeamIdProvided(){
        Set<TeamPlayerInfo> teamPlayerInfos = new HashSet<>(){{
            add(new TeamPlayerInfo(givenAPlayer(), 10));
            add(new TeamPlayerInfo(givenAPlayer(), 2));
            add(new TeamPlayerInfo(givenAPlayer(), 3));
        }};
        TeamId teamId = new TeamId();
        Team team = new Team(teamId, "Test team", teamPlayerInfos);
        teamRepository.store(team);

        List<TeamPlayerInfo> teamPlayerInfoSaved = teamPlayerInfoMySQLRepository.findByTeamId(teamId);

        Assertions.assertThat(teamPlayerInfoSaved.size()).isEqualTo(3);
    }
}
