package team7.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import team7.entities.*;
import team7.enumm.TipoAbbonamento;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


public class RivenditoreDAO {

    private static EntityManagerFactory emf;
    private final EntityManager em;

    //   costruttore
    public RivenditoreDAO(EntityManager em) {
        this.em = em;
    }


    //   query di ricerca distributori attivi
    static List<DistributoreAutomatico> distributoriAttivi() {
        EntityManager em = emf.createEntityManager();
        List<DistributoreAutomatico> res = null;
        try {
            res = em.createNamedQuery("distributori_automatici_attivi", DistributoreAutomatico.class).getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

    static List<DistributoreAutomatico> distributoriDisattivati() {
        EntityManager em = emf.createEntityManager();
        List<DistributoreAutomatico> res = null;
        try {
            res = em.createNamedQuery("distributori_automatici_disattivati", DistributoreAutomatico.class).getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return res;
    }


    //   EMETTI TITOLI DI VIAGGIO (biglietti e abbonamenti)
    public Biglietto emettiBiglietto(Rivenditore r) {

        EntityTransaction t = em.getTransaction();
        Biglietto b = new Biglietto(r);

        // controllo per il distributore automatico
        if (distributoreAttivo(r)) {
            try {
                t.begin();
                em.merge(r); // serve per sincronizzare il rivenditore sul db e quello java
                em.persist(b);
                t.commit();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return b;
        } else {
            System.out.println("Il distributore automatico non è momentaneamente fuori uso");
            return null;
        }
    }

    public Abbonamento emettiAbbonamento(Rivenditore r, TipoAbbonamento tipoAbbonamento, Tessera tessera) {

        //controllo il distributore automatico
        if (distributoreAttivo(r)) {

            // controllo  al scadenza della tessera
            if (scadenzaTessera(tessera)) {
                System.out.println("Impossibile emettere l'abbonamento richiesto. \n" +
                        "La tessera dell'utente " + tessera.getUtente().getNome() + " " + tessera.getUtente().getCognome() + " è scaduta in data " + tessera.getDataDiScadenza());
                return null;
            } else {
                EntityTransaction t = em.getTransaction();
                Abbonamento a = new Abbonamento(r, tipoAbbonamento, tessera);
                try {
                    t.begin();
                    em.merge(r);
                    em.persist(a);
                    t.commit();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                return a;
            }

        } else {
            System.out.println("Il distributore sutomatico è momentaneamente fuori uso");
            return null;
        }

    }

    public Boolean scadenzaTessera(Tessera t) {
        return t.getDataDiScadenza().isBefore(LocalDate.now());
    }

    public Boolean distributoreAttivo(Rivenditore r) {
        if (r instanceof DistributoreAutomatico d) return d.isAttivo();
        else return true;
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


}
