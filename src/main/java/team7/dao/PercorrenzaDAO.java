package team7.dao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.time.Duration;
import team7.entities.Percorrenza;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class PercorrenzaDAO {
    private final EntityManager em;

    public PercorrenzaDAO(EntityManager em) {
        this.em = em;
    }

    // salva
    public void salvapERCORRENZA(Percorrenza p) {
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(p);
            transaction.commit();
            System.out.println("Percorrenza salvata!");
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            throw e;
        }
    }

    // trova per tratta id
    public List<Percorrenza> TrovaTratta(String trattaId) {
        TypedQuery<Percorrenza> query = em.createQuery("SELECT p FROM Percorrenza p WHERE p.tratta.id = :trattaId", Percorrenza.class);
        query.setParameter("trattaId", UUID.fromString(trattaId));
        return query.getResultList();
    }

    // Trova per mezzo
    public List<Percorrenza> trovaPerMezzo(String mezzoId) {
        TypedQuery<Percorrenza> query = em.createQuery("SELECT p FROM Percorrenza p WHERE p.mezzo.id = :mezzoId", Percorrenza.class);
        query.setParameter("mezzoId", UUID.fromString(mezzoId));
        return query.getResultList();
    }
// Trova per perioddo
    public List<Percorrenza> trovaPerPeriodo(LocalDateTime inizio, LocalDateTime fine){
        TypedQuery<Percorrenza> query = em.createQuery("SELECT p FROM Percorrenza p WHERE p.dataOraPartenza >= :inizio AND p.dataOraPartenza <= :fine", Percorrenza.class);
        return query.getResultList();
    }
    // calcola tempo medio
    public Double CalcolaTempoMedio(String trattaId) {
        TypedQuery<Double> query = em.createQuery("SELECT AVG(p.tempoEffettivoMinuti) FROM Percorrenza p WHERE p.tratta.id = :trattaId", Double.class);
        query.setParameter("trattaId", UUID.fromString(trattaId));
        return query.getSingleResult();
    }
    // calcola tempo medio in base al mezzo
    public Double CalcolaTempoMedioperMEzzo(String trattaId,String mezzoId) {
        TypedQuery<Double> query = em.createQuery("SELECT AVG(p.tempoEffettivoPercorrenza) FROM Percorrenza p WHERE p.tratta.id = :trattaId AND p.mezzo.id=:mezzoId", Double.class);
        query.setParameter("trattaId", UUID.fromString(trattaId));
        query.setParameter("mezzoId", UUID.fromString(mezzoId));
        return query.getSingleResult();
    }
    // calcola tempo tra una percorrenza e l'altra
    //testare
    public Double CalcolaTempotraPercorrenze(UUID idP) {
        TypedQuery<Double> query = em.createQuery("SELECT (p.tempoEffettivoPercorrenza - p.dataOraPartenza ) FROM Percorrenza p WHERE p.id=:id ", Double.class);
        query.setParameter("id",idP);
        return query.getSingleResult();
    }
}
