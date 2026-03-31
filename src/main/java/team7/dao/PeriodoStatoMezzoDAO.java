package team7.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import team7.entities.PeriodoStatoMezzo;
import java.util.UUID;

public class PeriodoStatoMezzoDAO {

    private final EntityManager em;

    public PeriodoStatoMezzoDAO(EntityManager em) {
        // costruttore riceve l'EntityManager e lo assegna alla variabile em
        this.em = em;
    }

    public void save(PeriodoStatoMezzo periodo) {
        // per salvare un nuovo periodo di stato mezzo nel database
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(periodo);
        transaction.commit();
    }

    public PeriodoStatoMezzo findById(UUID id) {
        // per cercare un periodo di stato mezzo tramite il suo id.
        return em.find(PeriodoStatoMezzo.class, id);
    }
}