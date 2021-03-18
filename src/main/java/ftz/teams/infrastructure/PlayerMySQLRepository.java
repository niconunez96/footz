package ftz.teams.infrastructure;

import ftz.teams.domain.Player;
import ftz.teams.domain.PlayerId;
import ftz.teams.domain.PlayerRepository;
import org.springframework.stereotype.Repository;
import shared.infrastructure.jpa.JPARepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Optional;

@Repository
public class PlayerMySQLRepository extends JPARepository<Player, PlayerId> implements PlayerRepository {
    @Override
    public Optional<Player> findOne(String identification, String email) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder criteria = entityManager.getCriteriaBuilder();
        CriteriaQuery<Player> query = criteria.createQuery(Player.class);
        Root<Player> table = query.from(Player.class);
        Predicate predicate = criteria.and(
                criteria.equal(table.get("identification"), identification),
                criteria.equal(table.get("email"), email)
        );
        query.where(predicate);
        try{
            return Optional.of(entityManager.createQuery(query).getSingleResult());
        }catch(NoResultException exc){
            return Optional.empty();
        }
    }

    @Override
    public Optional<Player> findOne(PlayerId id) {
        return this.findById(id);
    }

    @Override
    public void store(Player player) {
        this.persist(player);
    }

    @Override
    protected Class<Player> getEntityClass() {
        return Player.class;
    }
}
