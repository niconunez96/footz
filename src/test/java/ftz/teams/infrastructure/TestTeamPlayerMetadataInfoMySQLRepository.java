package ftz.teams.infrastructure;

import config.DatabaseConfigTesting;
import config.RepositoryConfigTesting;
import ftz.teams.domain.*;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import javax.persistence.EntityManagerFactory;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
@ContextConfiguration(classes = {DatabaseConfigTesting.class, RepositoryConfigTesting.class})
public class TestTeamPlayerMetadataInfoMySQLRepository {

    @Autowired
    PlayerMySQLRepository teamPlayerInfoMySQLRepository;
    @Autowired
    PlayerMetadataRepository playerMetadataRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Before
    public void setUp(){
        entityManagerFactory.createEntityManager()
                .createNativeQuery("DELETE FROM players_metadata")
                .executeUpdate();
    }

    private PlayerMetadata givenAPlayer(){
        PlayerMetadata playerMetadata = new PlayerMetadata("test@mail.com", "1", "john");
        playerMetadataRepository.store(playerMetadata);
        return playerMetadata;
    }

    @Test
    public void itShouldReturnTeamPlayerInfosRelatedWithTeamIdProvided(){
        Set<Player> players = new HashSet<>(){{
            add(new Player(new PlayerId(), givenAPlayer(), 10));
            add(new Player(new PlayerId(), givenAPlayer(), 2));
            add(new Player(new PlayerId(), givenAPlayer(), 3));
        }};
        TeamId teamId = new TeamId();
        Team team = new Team(teamId, "Test team", players);
        teamRepository.store(team);

        List<Player> playerSaved = teamPlayerInfoMySQLRepository.findByTeamId(teamId);

        Assertions.assertThat(playerSaved.size()).isEqualTo(3);
    }
}
