package ftz.teams.domain;

import java.util.Optional;

public interface PlayerMetadataRepository {

    Optional<PlayerMetadata> findOne(String identification, String email);

    Optional<PlayerMetadata> findOne(Long id);

    void store(PlayerMetadata playerMetadata);
}
