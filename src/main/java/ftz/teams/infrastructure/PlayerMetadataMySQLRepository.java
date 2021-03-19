package ftz.teams.infrastructure;

import ftz.teams.domain.PlayerMetadata;
import ftz.teams.domain.PlayerId;
import ftz.teams.domain.PlayerMetadataRepository;
import org.springframework.stereotype.Repository;
import shared.infrastructure.jpa.JPARepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Optional;

@Repository
public class PlayerMetadataMySQLRepository extends JPARepository<PlayerMetadata, Long> implements PlayerMetadataRepository {

    public PlayerMetadataMySQLRepository(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    @Override
    public Optional<PlayerMetadata> findOne(String identification, String email) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder criteria = entityManager.getCriteriaBuilder();
        CriteriaQuery<PlayerMetadata> query = criteria.createQuery(PlayerMetadata.class);
        Root<PlayerMetadata> table = query.from(PlayerMetadata.class);
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
    public Optional<PlayerMetadata> findOne(Long id) {
        return this.findById(id);
    }

    @Override
    public void store(PlayerMetadata playerMetadata) {
        this.persist(playerMetadata);
    }

    @Override
    protected Class<PlayerMetadata> getEntityClass() {
        return PlayerMetadata.class;
    }
}
