package ftz.teams.infrastructure;

import ftz.teams.domain.Team;
import ftz.teams.domain.TeamId;
import ftz.teams.domain.TeamPlayerInfo;
import ftz.teams.domain.TeamPlayerInfoRepository;
import org.springframework.stereotype.Repository;
import shared.infrastructure.jpa.JPARepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TeamPlayerInfoMySQLRepository extends JPARepository<TeamPlayerInfo, Long> implements TeamPlayerInfoRepository {

    public TeamPlayerInfoMySQLRepository(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    @Override
    public List<TeamPlayerInfo> findByTeamId(TeamId id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Team team = entityManager.find(Team.class, id);
        return new ArrayList<>(team.teamPlayerInfos());
    }

    @Override
    protected Class<TeamPlayerInfo> getEntityClass() {
        return TeamPlayerInfo.class;
    }
}
