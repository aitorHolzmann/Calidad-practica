package repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import model.User;

/**
 * Implementación con JPA/Hibernate puro (sin Spring).
 * Se le inyecta el EntityManager para poder controlar su ciclo de vida
 * fácilmente desde el integration test (Testcontainers).
 */
public class JpaUserRepository implements UserRepository {

    private final EntityManager em;

    public JpaUserRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public void save(User user) {
        em.getTransaction().begin();
        try {
            em.persist(user);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            // si la constraint UNIQUE de la DB rechaza el insert,
            // hacemos rollback y limpiamos el persistence context
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.clear();
            throw e;
        }
    }

    @Override
    public User findByEmail(String email) {
        TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u WHERE u.email = :email", User.class);
        query.setParameter("email", email);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
