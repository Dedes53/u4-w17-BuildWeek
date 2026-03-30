package team7.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import team7.entities.Mezzo;

import java.util.UUID;

public class MezzoDAO {

    private final EntityManager em;

    public MezzoDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Mezzo mezzo) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(mezzo);
        transaction.commit();
    }

    public Mezzo findById(UUID id) {
        return em.find(Mezzo.class, id);
    }

    public void update(Mezzo mezzo) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.merge(mezzo);
        transaction.commit();
    }

    public void delete(UUID id) {
        Mezzo mezzo = em.find(Mezzo.class, id);
        if (mezzo != null) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.remove(mezzo);
            transaction.commit();
        }
    }
}