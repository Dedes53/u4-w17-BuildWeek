package team7.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import team7.entities.Mezzo;
import team7.entities.PeriodoStatoMezzo;
import team7.enumm.StatoMezzo;

import java.util.List;
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

    public List<PeriodoStatoMezzo> trovaStoricoPerMezzo(String mezzoId) {
        return em.createQuery(
                "SELECT p FROM PeriodoStatoMezzo p WHERE p.mezzo.id = :mezzoId ORDER BY p.dataInizio",
                PeriodoStatoMezzo.class
        ).setParameter("mezzoId", UUID.fromString(mezzoId)).getResultList();
    }

    public List<PeriodoStatoMezzo> trovaManutenzioniPerMezzo(String mezzoId) {
        return em.createQuery(
                        "SELECT p FROM PeriodoStatoMezzo p WHERE p.mezzo.id = :mezzoId AND p.stato = :stato ORDER BY p.dataInizio",
                        PeriodoStatoMezzo.class
                )
                .setParameter("mezzoId", UUID.fromString(mezzoId))
                .setParameter("stato", StatoMezzo.IN_MANUTENZIONE)
                .getResultList();
    }

    public PeriodoStatoMezzo trovaPeriodoAttivoPerMezzo(String mezzoId) {
        List<PeriodoStatoMezzo> risultati = em.createQuery(
                "SELECT p FROM PeriodoStatoMezzo p WHERE p.mezzo.id = :mezzoId AND p.dataFine IS NULL",
                PeriodoStatoMezzo.class
        ).setParameter("mezzoId", UUID.fromString(mezzoId)).getResultList();

        if (risultati.isEmpty()) {
            return null;
        }

        return risultati.get(0);
    }

    public List<PeriodoStatoMezzo> trovaMezziinManutenzione() {
        return em.createQuery(
                        "SELECT p FROM PeriodoStatoMezzo p " +
                                "WHERE p.stato = :stato AND p.dataFine IS NULL " +
                                "ORDER BY p.dataInizio",
                        PeriodoStatoMezzo.class
                ).setParameter("stato", StatoMezzo.IN_MANUTENZIONE)
                .getResultList();
    }

    public List<PeriodoStatoMezzo> trovaMezziinServizio() {
        return em.createQuery(
                        "SELECT p FROM PeriodoStatoMezzo p " +
                                "WHERE p.stato = :stato AND p.dataFine IS NULL " +
                                "ORDER BY p.dataInizio",
                        PeriodoStatoMezzo.class
                ).setParameter("stato", StatoMezzo.IN_SERVIZIO)   // oppure ATTIVO, in base al tuo enum
                .getResultList();
    }

    public List<PeriodoStatoMezzo> trovaStoricoMezzi() {
        return em.createQuery(
                        "SELECT p FROM PeriodoStatoMezzo p " +
                                "WHERE p.mezzo.id IN (" +
                                "   SELECT DISTINCT pm.mezzo.id FROM PeriodoStatoMezzo pm " +
                                "   WHERE pm.stato = :statoManutenzione" +
                                ") " +
                                "AND p.mezzo.statoAttuale = :statoAttuale " +
                                "ORDER BY p.mezzo.codiceMezzo, p.dataInizio", PeriodoStatoMezzo.class)
                .setParameter("statoManutenzione", StatoMezzo.IN_MANUTENZIONE)
                .setParameter("statoAttuale", StatoMezzo.IN_SERVIZIO)   // oppure ATTIVO
                .getResultList();
    }


}