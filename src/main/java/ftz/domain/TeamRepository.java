package ftz.domain;

import java.util.Optional;

public interface TeamRepository {

    Optional<Team> findOne(Long id);

    void store(Team team);
}
