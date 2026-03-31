package team7.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import team7.entities.Mezzo;
import team7.exeption.NonTrovato;

import java.util.UUID;

public class MezzoDAO {

    private final EntityManager em;
    // variabile che contiene l'EntityManager.
    public MezzoDAO(EntityManager em) {
        // costruttore che riceve l'EntityManager e lo assegna alla variabile em
        this.em = em;
    }

    public void save(Mezzo mezzo) {
        // per salvare un nuovo mezzo nel database
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(mezzo);
        transaction.commit();
    }

    public Mezzo findById(String id) {
       Mezzo trovato=em.find(Mezzo.class,UUID.fromString(id));
        if(trovato== null) throw new NonTrovato(id);
        return trovato;
    }

    public void update(Mezzo mezzo) {
        // per aggiornare un mezzo già esistente nel database
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.merge(mezzo);
        transaction.commit();
    }

    public void delete(UUID id) {
        // per eliminare un mezzo dal database usando il suo id

        Mezzo mezzo = em.find(Mezzo.class, id);
        if (mezzo != null) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.remove(mezzo);
            transaction.commit();
        }
    }
}