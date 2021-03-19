package ftz.teams.infrastructure;

import config.DatabaseConfigTesting;
import config.RepositoryConfigTesting;
import ftz.teams.domain.PlayerMetadata;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ContextConfiguration(classes = {DatabaseConfigTesting.class, RepositoryConfigTesting.class})
public class TestPlayerMetadataMySQLRepository {

    @Autowired
    PlayerMetadataMySQLRepository playerMetadataRepository;

    @Test
    public void itShouldSavePlayerIntoDatabase() {
        PlayerMetadata playerMetadata = new PlayerMetadata("john@mail.com", "1234", "john");
        PlayerMetadata playerSaved = playerMetadataRepository.store(playerMetadata);

        PlayerMetadata playedFound = playerMetadataRepository.findOne(playerSaved.id()).get();

        assertThat(playedFound.email()).isEqualTo("john@mail.com");
        assertThat(playedFound.identification()).isEqualTo("1234");
        assertThat(playedFound.name()).isEqualTo("john");
    }

    @Test
    public void itShouldReturnEmptyPlayerIfPlayerIdDoesNotExist() {
        Optional<PlayerMetadata> player = playerMetadataRepository.findOne(-1L);

        assertThat(player.isEmpty()).isTrue();
    }

    @Test
    public void itShouldReturnPlayerWithSameIdentificationAndEmail() {
        PlayerMetadata playerMetadata = new PlayerMetadata("john@mail.com", "1234", "john");
        playerMetadataRepository.store(playerMetadata);

        Optional<PlayerMetadata> playerStored = playerMetadataRepository.findOne("1234", "john@mail.com");

        assertThat(playerStored.isPresent()).isTrue();
    }

    @Test
    public void itShouldReturnEmptyPlayerIfIdentificationAndEmailDoesNotExist() {
        Optional<PlayerMetadata> playerStored = playerMetadataRepository.findOne("-1", "john@mail.com");

        assertThat(playerStored.isEmpty()).isTrue();
    }
}
