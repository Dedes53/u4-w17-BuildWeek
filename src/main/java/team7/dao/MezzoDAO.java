package team7.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import team7.entities.Mezzo;
import team7.entities.PeriodoStatoMezzo;
import team7.enumm.StatoMezzo;
import team7.enumm.TipoMezzo;
import team7.exeption.NonTrovato;

import java.time.LocalDate;
import java.util.List;
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

        PeriodoStatoMezzo periodoIniziale = new PeriodoStatoMezzo(
                mezzo,
                mezzo.getStatoAttuale(),
                LocalDate.now(),
                null
        );
        em.persist(periodoIniziale);

        transaction.commit();
    }

    public Mezzo findById(String id) {
        Mezzo trovato = em.find(Mezzo.class, UUID.fromString(id));
        if (trovato == null) throw new NonTrovato(id);
        return trovato;
    }

    // cerca un mezzo tramite codiceMezzo
    public Mezzo findByCodice(String codiceMezzo) {
        List<Mezzo> risultati = em.createQuery(
                "SELECT m FROM Mezzo m WHERE m.codiceMezzo = :codice",
                Mezzo.class
        ).setParameter("codice", codiceMezzo).getResultList();

        if (risultati.isEmpty()) {
            return null;
        }

        return risultati.get(0);
    }

    public List<Mezzo> findByTipo(TipoMezzo tipoMezzo) {
        return em.createQuery(
                "SELECT m FROM Mezzo m WHERE m.tipoMezzo = :tipo",
                Mezzo.class
        ).setParameter("tipo", tipoMezzo).getResultList();
    }

    public List<Mezzo> findByStato(StatoMezzo stato) {
        return em.createQuery(
                "SELECT m FROM Mezzo m WHERE m.statoAttuale = :stato",
                Mezzo.class
        ).setParameter("stato", stato).getResultList();
    }

    public void update(Mezzo mezzo) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.merge(mezzo);
        transaction.commit();
    }

    public void cambiaStato(String id, StatoMezzo nuovoStato, LocalDate dataCambio) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Mezzo mezzo = em.find(Mezzo.class, UUID.fromString(id));

        if (mezzo == null) {
            transaction.rollback();
            throw new NonTrovato(id);
        }

        if (mezzo.getStatoAttuale() == nuovoStato) {
            transaction.commit();
            return;
        }

        List<PeriodoStatoMezzo> periodiAttivi = em.createQuery(
                "SELECT p FROM PeriodoStatoMezzo p WHERE p.mezzo.id = :mezzoId AND p.dataFine IS NULL",
                PeriodoStatoMezzo.class
        ).setParameter("mezzoId", mezzo.getId()).getResultList();

        if (!periodiAttivi.isEmpty()) {
            PeriodoStatoMezzo periodoAttivo = periodiAttivi.get(0);
            periodoAttivo.setDataFine(dataCambio);
        }

        mezzo.setStatoAttuale(nuovoStato);
        em.merge(mezzo);

        PeriodoStatoMezzo nuovoPeriodo = new PeriodoStatoMezzo(
                mezzo,
                nuovoStato,
                dataCambio,
                null
        );
        em.persist(nuovoPeriodo);

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

    // cancella tutti i mezzi
    public void deleteAll() {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        em.createQuery("DELETE FROM Mezzo").executeUpdate();

        transaction.commit();
    }
}