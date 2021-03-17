package shared;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import java.util.Optional;

public abstract class JPARepository<T, ID> {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    protected Optional<T> findById(ID id){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        T entity = entityManager.find(getEntityClass(), id);
        return Optional.ofNullable(entity);
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
