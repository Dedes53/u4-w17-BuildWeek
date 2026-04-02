package team7.dao;

import jakarta.persistence.EntityManager;
import team7.entities.Abbonamento;
import team7.entities.Rivenditore;
import team7.entities.Tessera;

import java.time.LocalDate;
import java.util.List;

public class AbbonamentoDAO {

    private EntityManager em;

    public AbbonamentoDAO(EntityManager em) {
        this.em = em;
    }


    public void saved(Abbonamento abbonamento) {
        em.getTransaction().begin();
        em.persist(abbonamento);
        em.getTransaction().commit();
    }


    public Abbonamento findById(Long id) {
        return em.find(Abbonamento.class, id);
    }


    public List<Abbonamento> findAll() {
        return em.createQuery("SELECT a FROM Abbonamento a", Abbonamento.class).getResultList();
    }


    public List<Abbonamento> getAbbonamentiAttiviPeriodo(LocalDate inizio, LocalDate fine) {
        return em.createQuery("SELECT a FROM Abbonamento a WHERE a.dataInizio <= :end AND a.dataFine >= :start", Abbonamento.class).setParameter("start", inizio).setParameter("end", fine).getResultList();
    }


    public Long countAbbonamentiPeriodo(LocalDate inizio, LocalDate fine) {
        return em.createQuery("SELECT COUNT(a) FROM Abbonamento a WHERE a.dataEmissione BETWEEN :start AND :end", Long.class).setParameter("start", inizio).setParameter("end", fine).getSingleResult();
    }
    public List<Abbonamento> findByRivenditore(Rivenditore rivenditore) {
        return em.createQuery("SELECT a FROM Abbonamento a WHERE a.rivenditore = :r", Abbonamento.class).setParameter("r", rivenditore).getResultList();
    }
}
