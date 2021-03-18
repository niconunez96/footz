package ftz.teams.infrastructure;

import config.DatabaseConfigTesting;
import config.RepositoryConfigTesting;
import ftz.teams.domain.Player;
import ftz.teams.domain.PlayerId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import server.FtzApplication;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ContextConfiguration(classes = {DatabaseConfigTesting.class, RepositoryConfigTesting.class})
public class TestPlayerMySQLRepository {

    @Autowired
    PlayerMySQLRepository playerRepository;

    @Test
    public void itShouldSavePlayerIntoDatabase() {
        PlayerId id = new PlayerId();
        Player player = new Player(id, "john@mail.com", "1234", "john");

        playerRepository.store(player);

        Player playedSaved = playerRepository.findOne(id).get();

        assertThat(playedSaved)
                .isEqualTo(new Player(id, "john@mail.com", "1234", "john"));
    }

    @Test
    public void itShouldReturnEmptyPlayerIfPlayerIdDoesNotExist() {
        Optional<Player> player = playerRepository.findOne(new PlayerId());

        assertThat(player.isEmpty()).isTrue();
    }

    @Test
    public void itShouldReturnPlayerWithSameIdentificationAndEmail() {
        PlayerId id = new PlayerId();
        Player player = new Player(id, "john@mail.com", "1234", "john");
        playerRepository.store(player);

        Optional<Player> playerStored = playerRepository.findOne("1234", "john@mail.com");

        assertThat(playerStored.isPresent()).isTrue();
    }

    @Test
    public void itShouldReturnEmptyPlayerIfIdentificationAndEmailDoesNotExist() {
        Optional<Player> playerStored = playerRepository.findOne("-1", "john@mail.com");

        assertThat(playerStored.isEmpty()).isTrue();
    }
}
