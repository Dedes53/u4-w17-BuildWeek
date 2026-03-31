package team7.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import team7.entities.Rivenditore;
import team7.entities.TitoloViaggio;

import java.util.UUID;

public class RivenditoreDAO {

    private final EntityManager em;
    private EntityManagerFactory emf;

    public RivenditoreDAO(EntityManager em) {
        this.em = em;
    }

    //   metodiDAO
    public void save(Rivenditore r) {
        try {
            EntityTransaction t = em.getTransaction();
            t.begin();
            em.persist(r);
            t.commit();
            System.out.println("Il rivenditore " + r.getNomeAttivita() + " è stato aggiunto alla lista dei punti vendita");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Rivenditore findByID(UUID id) {
        Rivenditore r = em.find(Rivenditore.class, id);
        if (r == null) System.out.println("Nessun rivenditore corrispondente trovato");
        return r;
    }


    public void remove(UUID id) {
        try {
            EntityTransaction t = em.getTransaction();
            t.begin();
            Rivenditore r = this.findByID(id);
            em.remove(r);
            t.commit();
            System.out.println("L'attività " + r.getNomeAttivita() + " è stata rimossa dai punti vendita");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public TitoloViaggio emettiTitoloDiViaggio(Rivenditore r, TitoloViaggio t) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Rivenditore rGestito = em.merge(r);
            t.setRivenditore(rGestito);
            em.persist(t);
            transaction.commit();
            return t;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            em.close();
        }
    }
}
