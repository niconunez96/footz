package ftz.teams.domain;

import ftz.teams.domain.Team;

import java.util.Optional;

public interface TeamRepository {

    Optional<Team> findOne(TeamId id);

    void store(Team team);
}
