package repositories.abstrato;

import entities.abstrato.mediador.Pedido;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class PedidoRepository {
    private EntityManager em;

    public PedidoRepository(EntityManager em) {
        this.em = em;
    }

    public void save(Pedido pedido) {
        em.getTransaction().begin();
        em.persist(pedido);
        em.getTransaction().commit();
    }

    public Pedido findById(Long id) {
        return em.find(Pedido.class, id);
    }

    public List<Pedido> findAll() {
        TypedQuery<Pedido> query = em.createQuery("SELECT p FROM Pedido p", Pedido.class);
        return query.getResultList();
    }
}
