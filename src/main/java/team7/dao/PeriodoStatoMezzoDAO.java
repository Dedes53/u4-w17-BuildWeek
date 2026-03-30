package team7.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import team7.entities.PeriodoStatoMezzo;

import java.util.UUID;

public class PeriodoStatoMezzoDAO {

    private final EntityManager em;

    public PeriodoStatoMezzoDAO(EntityManager em) {
        this.em = em;
    }

    public void save(PeriodoStatoMezzo periodo) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(periodo);
        transaction.commit();
    }

    public PeriodoStatoMezzo findById(UUID id) {
        return em.find(PeriodoStatoMezzo.class, id);
    }
}