package repositories.fisico.estrutural;

import entities.fisico.estrutural.Abrigo;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class AbrigoRepository {
    private EntityManager em;

    public AbrigoRepository(EntityManager em) {
        this.em = em;
    }

    public void save(Abrigo abrigo) {
        em.getTransaction().begin();
        em.persist(abrigo);
        em.getTransaction().commit();
    }

    public void update(Abrigo abrigo) {
        em.getTransaction().begin();
        em.merge(abrigo);
        em.getTransaction().commit();
    }

    public void delete(Long id) {
        Abrigo abrigo = em.find(Abrigo.class, id);
        if (abrigo != null) {
            em.getTransaction().begin();
            em.remove(abrigo);
            em.getTransaction().commit();
        }
    }

    public Abrigo findById(Long id) {
        return em.find(Abrigo.class, id);
    }

    public List<Abrigo> findAll() {
        TypedQuery<Abrigo> query = em.createQuery("SELECT a FROM Abrigo a", Abrigo.class);
        return query.getResultList();
    }
}