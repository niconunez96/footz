package shared.infrastructure.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

public abstract class JPARepository<T, ID> {

    protected EntityManagerFactory entityManagerFactory;

    public JPARepository(EntityManagerFactory entityManagerFactory){
        this.entityManagerFactory = entityManagerFactory;
    }

    protected Optional<T> findById(ID id){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        T entity = entityManager.find(getEntityClass(), id);
        return Optional.ofNullable(entity);
    }

    protected List<T> findAll(){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder criteria = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = criteria.createQuery(getEntityClass());
        query.from(getEntityClass());
        return entityManager.createQuery(query).getResultList();
    }

    @Transactional
    protected void persist(T entity){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            entityManager.persist(entity);
            transaction.commit();
        }catch(PersistenceException exc){
            transaction.rollback();
        }finally {
            entityManager.close();
        }
    }

    protected abstract Class<T> getEntityClass();
}
