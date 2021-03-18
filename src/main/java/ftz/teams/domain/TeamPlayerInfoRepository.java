package ftz.teams.domain;

import java.util.List;

public interface TeamPlayerInfoRepository {

    List<TeamPlayerInfo> findByTeamId(TeamId id);
}
