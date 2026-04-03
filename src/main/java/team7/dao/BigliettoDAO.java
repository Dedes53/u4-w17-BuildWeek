package team7.dao;

import jakarta.persistence.EntityManager;
import team7.entities.Biglietto;
import team7.entities.Mezzo;
import team7.entities.Rivenditore;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class BigliettoDAO {

    private EntityManager em;

    public BigliettoDAO(EntityManager em) {
        this.em = em;
    }

    public void saved(Biglietto b) {
        em.getTransaction().begin();
        em.persist(b);
        em.getTransaction().commit();
    }

    public Biglietto findById(Long id) {
        return em.find(Biglietto.class, id);
    }

    public List<Biglietto> findAll() {
        return em.createQuery("SELECT b FROM Biglietto b", Biglietto.class).getResultList();
    }

    public void vidimaBiglietto(Biglietto b, Mezzo mezzo) {
        em.getTransaction().begin();
        b.setVidimato(true);
        b.setDataDiVidimazione(LocalDateTime.now());
        b.setMezzoVidimazione(mezzo);
        em.merge(b);
        em.getTransaction().commit();
    }


    public List<Biglietto> getVidimatiPerPeriodo(LocalDateTime inizio, LocalDateTime fine) {
        return em.createQuery("SELECT b FROM Biglietto b WHERE b.vidimato = true AND b.dataDiVidimazione BETWEEN :start AND :end", Biglietto.class).setParameter("start", inizio).setParameter("end", fine).getResultList();
    }


    public Long countBigliettiPerPeriodo(LocalDate inizio, LocalDate fine) {
        return em.createQuery("SELECT COUNT(b) FROM Biglietto b WHERE b.dataEmissione BETWEEN :start AND :end", Long.class).setParameter("start", inizio).setParameter("end", fine).getSingleResult();
    }


    public List<Biglietto> getBigliettiEmessiPeriodo(LocalDate inizio, LocalDate fine) {
        return em.createQuery("SELECT b FROM Biglietto b WHERE b.dataEmissione BETWEEN :start AND :end", Biglietto.class).setParameter("start", inizio).setParameter("end", fine).getResultList();
    }

    public List<Biglietto> findByRivenditore(Rivenditore rivenditore) {
        return em.createQuery("SELECT a FROM Biglietto a WHERE a.rivenditore = :r", Biglietto.class).setParameter("r", rivenditore).getResultList();
    }
    public List<Biglietto> findByMezzoVidimazione(Long mezzoId) {
        return em.createQuery("SELECT b FROM Biglietto b WHERE b.vidimato = true AND b.mezzoVidimazione.id = :IDmezzo", Biglietto.class).setParameter("IDmezzo", mezzoId).getResultList();
    }
    public List<Biglietto> findByCodiceMezzo(String codice) {
        return em.createQuery(
                        "SELECT b FROM Biglietto b WHERE b.vidimato = true AND b.mezzoVidimazione.codice = :codice", Biglietto.class).setParameter("codice", codice).getResultList();
    }
}