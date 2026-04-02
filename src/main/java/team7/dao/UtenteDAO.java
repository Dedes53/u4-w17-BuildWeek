package team7.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NamedQuery;
import team7.entities.Abbonamento;
import team7.entities.Tessera;
import team7.entities.Utente;
import team7.exeption.NonTrovato;

import java.time.LocalDate;
import java.util.UUID;

@NamedQuery(
        name = "abbonamento_attivo_utente",
        query = "select a from Abbonamento a where a.tessera.utente.id = :utenteId and a.dataFine >= :oggi"
)
public class UtenteDAO {

    private static EntityManagerFactory emf;
    private final EntityManager em;

    public UtenteDAO(EntityManager em) {
        this.em = em;
    }

    //   metodi di Utente
    public static Abbonamento controllaAbbonamento(Utente u) {
        //utente -> Tessera -> Abbonamento
        EntityManager em = emf.createEntityManager();
        Abbonamento res = null;
        try {
            res = em.createNamedQuery("abbonamento_attivo_utente", Abbonamento.class)
                    .setParameter("utenteId", u.getId())
                    .setParameter("oggi", LocalDate.now())
                    .getSingleResult();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

    public void save(Utente u) {
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            em.persist(u);
            t.commit();
            System.out.println("Utente " + u.getNome() + " " + u.getCognome() + " salvato con successo");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Utente findByID(UUID id) {
        Utente u = em.find(Utente.class, id);
        if (u == null) throw new NonTrovato("Non è stato possibile trovare alcun utente corrispondente all'id: " + id);
        return u;
    }

    public void remove(UUID id) {
        EntityTransaction t = em.getTransaction();
        Utente u = this.findByID(id);
        try {
            t.begin();
            em.remove(u);
            t.commit();
            System.out.println("L'utente " + u.getNome() + " " + u.getCognome() + " è stato rimosso");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //   TESSERA
    public void saveTessera(Tessera tessera) {
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            em.persist(tessera);
            t.commit();
            System.out.println("Creazione tessera n." + tessera.getId() + " Per l'utente " + tessera.getUtente().getNome() + " " + tessera.getUtente().getCognome());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Tessera creaNuovaTessera(Utente u) {
        Tessera t = new Tessera(u);
        this.saveTessera(t);
        return t;
    }
}
