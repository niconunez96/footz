package ftz.teams.domain;

import java.util.Optional;

public interface TeamRepository {

    Optional<Team> findOne(TeamId id);

    void store(Team team);
}
