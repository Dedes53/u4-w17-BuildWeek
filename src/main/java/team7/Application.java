package team7;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import team7.dao.MezzoDAO;
import team7.dao.PeriodoStatoMezzoDAO;
import team7.entities.Mezzo;
import team7.entities.PeriodoStatoMezzo;
import team7.enumm.StatoMezzo;
import team7.enumm.TipoMezzo;

import java.time.LocalDate;

public class Application {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("team7");
        EntityManager em = emf.createEntityManager();

        MezzoDAO mezzoDAO = new MezzoDAO(em);
        PeriodoStatoMezzoDAO periodoDAO = new PeriodoStatoMezzoDAO(em);

        try {
            System.out.println("=== INIZIO TEST MEZZI ===");

            // 1) creo un mezzo
            Mezzo bus1 = new Mezzo("BUS-01", TipoMezzo.BUS, StatoMezzo.IN_SERVIZIO, 80);

            // 2) lo salvo nel database
            mezzoDAO.save(bus1);
            System.out.println("Mezzo salvato:");
            System.out.println(bus1);

            // 3) simulo il passaggio in manutenzione
            bus1.setStatoAttuale(StatoMezzo.IN_MANUTENZIONE);
            mezzoDAO.update(bus1);

            // 4) salvo lo storico del periodo di manutenzione
            PeriodoStatoMezzo periodoManutenzione = new PeriodoStatoMezzo(
                    bus1,
                    StatoMezzo.IN_MANUTENZIONE,
                    LocalDate.now(),
                    null
            );

            periodoDAO.save(periodoManutenzione);

            System.out.println("\nMezzo aggiornato:");
            System.out.println(bus1);

            System.out.println("\nPeriodo di stato salvato:");
            System.out.println(periodoManutenzione);

            // 5) opzionale: simulo il ritorno in servizio
            periodoManutenzione.setDataFine(LocalDate.now().plusDays(2));

            bus1.setStatoAttuale(StatoMezzo.IN_SERVIZIO);
            mezzoDAO.update(bus1);

            PeriodoStatoMezzo nuovoPeriodoServizio = new PeriodoStatoMezzo(
                    bus1,
                    StatoMezzo.IN_SERVIZIO,
                    LocalDate.now().plusDays(2),
                    null
            );

            periodoDAO.save(nuovoPeriodoServizio);

            System.out.println("\nMezzo tornato in servizio:");
            System.out.println(bus1);

            System.out.println("\nNuovo periodo di servizio salvato:");
            System.out.println(nuovoPeriodoServizio);

            System.out.println("\n=== FINE TEST ===");

        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}