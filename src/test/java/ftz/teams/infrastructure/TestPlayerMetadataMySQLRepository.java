package ftz.teams.infrastructure;

import config.DatabaseConfigTesting;
import config.RepositoryConfigTesting;
import ftz.teams.domain.PlayerMetadata;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import javax.persistence.EntityManagerFactory;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ContextConfiguration(classes = {DatabaseConfigTesting.class, RepositoryConfigTesting.class})
public class TestPlayerMetadataMySQLRepository {

    @Autowired
    PlayerMetadataMySQLRepository playerMetadataRepository;
    @Autowired
    static EntityManagerFactory entityManagerFactory;

    @Before
    public void setUp(){
        entityManagerFactory.createEntityManager()
                .createNativeQuery("DELETE FROM players_metadata")
                .executeUpdate();
    }

    @Test
    public void itShouldSavePlayerIntoDatabase() {
        PlayerMetadata playerMetadata = new PlayerMetadata("john@mail.com", "1234", "john");

        playerMetadataRepository.store(playerMetadata);

        PlayerMetadata playedSaved = playerMetadataRepository.findOne(1L).get();

        assertThat(playedSaved.email()).isEqualTo("john@mail.com");
        assertThat(playedSaved.identification()).isEqualTo("1234");
        assertThat(playedSaved.name()).isEqualTo("john");
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
