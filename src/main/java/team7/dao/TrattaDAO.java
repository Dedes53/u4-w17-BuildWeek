package team7.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import team7.entities.Tratta;
import team7.exeption.NonTrovato;

import java.util.List;
import java.util.UUID;

public class TrattaDAO {

    private final EntityManager em;
    public TrattaDAO(EntityManager em){
        this.em=em;
    }

    //  salva la tratta
    public void salvaTratta(Tratta nuovatratta){
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(nuovatratta);
        transaction.commit();
        System.out.println("La Tratta " + nuovatratta.getZonaPartenza() +"-" +nuovatratta.getZonaFinale() + " è stato salvata correttamente!");
    }
    //  trova la tratta per ID
    public Tratta trovaPerID(String id){
        Tratta trovato= em.find(Tratta.class,UUID.fromString(id));
        if(trovato== null) throw new NonTrovato(id);
        return trovato;
    }
    //  ritorna tutte le tratte
    public List<Tratta> TrovaTutteLeTratte(){
    TypedQuery<Tratta> query = em.createQuery("SELECT t FROM Tratta t", Tratta.class);
    return query.getResultList();
     }
//  elimina per id
public void cancellaTratta(String id) {
    EntityTransaction transaction = em.getTransaction();
    try {
        transaction.begin();

        Query query = em.createQuery("DELETE FROM Tratta t WHERE t.id = :id");
        query.setParameter("id", UUID.fromString(id));

        int righeEliminate = query.executeUpdate();
        transaction.commit();
        if (righeEliminate == 0) {
            throw new NonTrovato(id);
        }

        System.out.println("Tratta cancellata!");
    } catch (Exception e) {
        if (transaction.isActive()) transaction.rollback();
        throw e;
    }
}
// questa dovrebbe fa il conteggio delle volte che si percorre una tratta
//pero cosi conto solo quante righe ci sono..dovrei penso contare con il titolo di viaggio
    // forse ...SELECT COUNT(t) FROM TitoloVIaggio t WHERE t=:tratta
public long ContaVolteperTratta(Tratta tratta){
    TypedQuery<Long> query = em.createQuery("SELECT COUNT(t) FROM Tratta t WHERE t=:tratta", Long.class);
    query.setParameter("tratta",tratta);
    return query.getSingleResult();
}

}
