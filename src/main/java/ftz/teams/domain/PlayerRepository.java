package ftz.teams.domain;

import java.util.List;

public interface PlayerRepository {

    List<Player> findByTeamId(TeamId id);
}
