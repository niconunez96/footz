package ftz.teams.infrastructure;

import ftz.teams.domain.*;
import org.springframework.stereotype.Repository;
import shared.infrastructure.jpa.JPARepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PlayerMySQLRepository extends JPARepository<Player, PlayerId> implements PlayerRepository {

    public PlayerMySQLRepository(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    @Override
    public List<Player> findByTeamId(TeamId id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Team team = entityManager.find(Team.class, id);
        return new ArrayList<>(team.teamPlayerInfos());
    }

    @Override
    protected Class<Player> getEntityClass() {
        return Player.class;
    }
}
