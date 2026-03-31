package team7.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import team7.entities.Mezzo;
import team7.entities.PeriodoStatoMezzo;
import team7.enumm.StatoMezzo;
import team7.exeption.NonTrovato;

import java.time.LocalDate;
import java.util.List;
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
        // e creare automaticamente il primo periodo iniziale
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

    public void update(Mezzo mezzo) {
        // per aggiornare un mezzo già esistente nel database
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

        // se il mezzo è già in questo stato non faccio niente
        if (mezzo.getStatoAttuale() == nuovoStato) {
            transaction.commit();
            return;
        }

        // cerco il periodo attivo aperto del mezzo
        List<PeriodoStatoMezzo> periodiAttivi = em.createQuery(
                "SELECT p FROM PeriodoStatoMezzo p WHERE p.mezzo.id = :mezzoId AND p.dataFine IS NULL",
                PeriodoStatoMezzo.class
        ).setParameter("mezzoId", mezzo.getId()).getResultList();

        // chiudo il periodo attivo, se esiste
        if (!periodiAttivi.isEmpty()) {
            PeriodoStatoMezzo periodoAttivo = periodiAttivi.get(0);
            periodoAttivo.setDataFine(dataCambio);
        }

        // aggiorno lo stato attuale del mezzo
        mezzo.setStatoAttuale(nuovoStato);
        em.merge(mezzo);

        // creo il nuovo periodo di stato
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