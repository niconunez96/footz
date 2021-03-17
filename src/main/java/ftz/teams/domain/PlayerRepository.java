package ftz.teams.domain;

import java.util.Optional;

public interface PlayerRepository {

    Optional<Player> findOne(String identification, String email);

    Optional<Player> findOne(PlayerId id);

    void store(Player player);
}
