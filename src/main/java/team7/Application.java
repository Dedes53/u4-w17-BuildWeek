package team7;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import team7.dao.*;
import team7.entities.*;
import team7.enumm.StatoMezzo;
import team7.enumm.TipoMezzo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("team7");

    public static void main(String[] args) {

        // EntityManagerFactory emf = Persistence.createEntityManagerFactory("team7");
        EntityManager em = emf.createEntityManager();

        //per mezzo
        MezzoDAO mezzoDAO = new MezzoDAO(em);
        PeriodoStatoMezzoDAO periodoDAO = new PeriodoStatoMezzoDAO(em);

        //per tratta e per percorrenza
        TrattaDAO trattaDAO = new TrattaDAO(em);
        PercorrenzaDAO percorrenzaDAO = new PercorrenzaDAO(em);
        //per rivenditore
        RivenditoreDAO rivenditoreDAO = new RivenditoreDAO(em);

        //raffi

        Scanner scanner = new Scanner(System.in);
        int scelta;

        try {
            do {
                System.out.println("MENU ");
                System.out.println("1 Test UtenteDAO");
                System.out.println("2 Test TesseraDAO");
                System.out.println("3 Test AbbonamentoDAO");
                System.out.println("4 Test BigliettoDAO");
                System.out.println("5 Test RivenditoreDAO");
                System.out.println("6 Test MezzoDAO");
                System.out.println("7 Test PeriodoStatoMezzoDAO");
                System.out.println("8 Test TrattaDAO");
                System.out.println("9 Test PercorrenzaDAO");
                System.out.println("0 Esci");

                scelta = scanner.nextInt();

                switch (scelta) {
                    case 1:
                        // TEST UTENTEDAO

                        break;

                    case 2:
                        // TEST TESSERADAO

                        break;

                    case 3:
                        // TEST ABBONAMENTODAO

                        break;

                    case 4:
                        // TEST BIGLIETTODAO

                        break;

                    case 5:
                        // TEST RIVENDITOREDAO

                        break;

                    case 6:
                        // TEST MEZZODAO
                        // - crea un autobus
                        // - crea un tram
                        // - assegna un mezzo a una tratta
                        // - aggiorna stato attuale del mezzo
                        // - cerca mezzi per tipo
                        // - cerca mezzi per stato
                        break;

                    case 7:
                        // TEST STATO MEZZODAO

                        break;

                    case 8:
                        // - crea una tratta
                        Tratta trattaTest = new Tratta("Centro", "Stazione", 45);
                        trattaDAO.salvaTratta(trattaTest);
                        System.out.println("Tratta salvata:");
                        System.out.println("ID: " + trattaTest.getId());
                        System.out.println("Partenza: " + trattaTest.getZonaPartenza());
                        System.out.println("Arrivo: " + trattaTest.getZonaFinale());
                        System.out.println("Durata: " + trattaTest.getTempoPercorrenzaFormattato());

                        // - cerca tratta per id
                        Tratta trattaTrovata = trattaDAO.trovaPerID(trattaTest.getId().toString());
                        System.out.println("Tratta trovata per ID:");
                        System.out.println("ID: " + trattaTrovata.getId());
                        System.out.println("Partenza: " + trattaTrovata.getZonaPartenza());
                        System.out.println("Arrivo: " + trattaTrovata.getZonaFinale());
                        System.out.println("Durata: " + trattaTrovata.getTempoPercorrenzaFormattato());

                        // - stampa tutte le tratte
                        System.out.println("Tutte le tratte:");
                        trattaDAO.TrovaTutteLeTratte().forEach(t ->
                                System.out.println(t.getId() + " - " + t.getZonaPartenza() + " -> " + t.getZonaFinale() + " - " + t.getTempoPercorrenzaFormattato()));

                        // - cerca tratte per partenza
                        // TODO: da fare quando hai un metodo tipo trovaPerZonaPartenza(String zonaPartenza)

                        // - cerca tratte per capolinea
                        // TODO: da fare quando hai un metodo tipo trovaPerZonaFinale(String zonaFinale)

                        // - aggiorna tempo previsto
                        // TODO: da fare quando hai un metodo update nel TrattaDAO
                        break;

                    case 9:
                        // TEST PERCORRENZADAO
                        // - registra una percorrenza di un mezzo su una tratta
                        // - salva tempo effettivo di percorrenza
                        // - conta quante volte un mezzo ha percorso una tratta
                        // - calcola il tempo medio effettivo di percorrenza
                        // - stampa le percorrenze di un mezzo
                        // - stampa le percorrenze di una tratta
                        // TEST PERCORRENZADAO

                        // - creo una tratta di appoggio
                        Tratta trattaPercorrenza = new Tratta("Deposito", "Capolinea Nord", 35);
                        trattaDAO.salvaTratta(trattaPercorrenza);

                        // - creo un mezzo di appoggio
                        Mezzo mezzoPercorrenza = new Mezzo("BUS-PERC-01", TipoMezzo.BUS, StatoMezzo.IN_SERVIZIO, 85);
                        mezzoDAO.save(mezzoPercorrenza);

                        // - registra una percorrenza di un mezzo su una tratta
                        LocalDateTime datapartenza = LocalDateTime.now();
                        LocalDateTime dataarrivo = datapartenza.plusMinutes(42);

                        Percorrenza percorrenzaTest = new Percorrenza(mezzoPercorrenza, trattaPercorrenza, datapartenza, dataarrivo);

                        percorrenzaDAO.salvapERCORRENZA(percorrenzaTest);
                        System.out.println("Percorrenza salvata:");
                        System.out.println("ID: " + percorrenzaTest.getId());
                        System.out.println("Mezzo: " + percorrenzaTest.getMezzo().getId());
                        System.out.println("Tratta: " + percorrenzaTest.getTratta().getId());
                        System.out.println("Partenza: " + percorrenzaTest.getDataOraPartenza());
                        System.out.println("Arrivo: " + percorrenzaTest.getDataOraArrivo());
                        System.out.println("Tempo effettivo: " + percorrenzaTest.getTempoEffettivoPercorrenza().toMinutes() + " minuti");
                        System.out.println("Scostamento: " + percorrenzaTest.getScostamentoRispettoAlPrevisto());

                        // - salva tempo effettivo di percorrenza
                        System.out.println("Tempo effettivo registrato correttamente.");

                        // - conta quante volte un mezzo ha percorso una tratta
                        // al momento, se non hai un count dedicato, stampi la size della lista
                        int numeroPercorrenze = percorrenzaDAO
                                .trovaPerMezzo(mezzoPercorrenza.getId().toString())
                                .size();
                        System.out.println("Numero percorrenze del mezzo: " + numeroPercorrenze);

                        // - calcola il tempo medio effettivo di percorrenza
                        Double mediaTratta = percorrenzaDAO.CalcolaTempoMedio(trattaPercorrenza.getId().toString());
                        System.out.println("Tempo medio effettivo della tratta: " + mediaTratta);

                        Double mediaTrattaMezzo = percorrenzaDAO.CalcolaTempoMedioperMEzzo(
                                trattaPercorrenza.getId().toString(),
                                mezzoPercorrenza.getId().toString()
                        );
                        System.out.println("Tempo medio effettivo della tratta per il mezzo: " + mediaTrattaMezzo);

                        // - stampa le percorrenze di un mezzo
                        System.out.println("Percorrenze del mezzo:");
                        percorrenzaDAO.trovaPerMezzo(mezzoPercorrenza.getId().toString()).forEach(System.out::println);

                        // - stampa le percorrenze di una tratta
                        System.out.println("Percorrenze della tratta:");
                        percorrenzaDAO.TrovaTratta(trattaPercorrenza.getId().toString()).forEach(System.out::println);

                        break;
                    case 10:
                        try {
                            int sceltaInserimento;
                            do {
                                System.out.println("MENU INSERIMENTO");
                                System.out.println("1  Salva mezzo");
                                System.out.println("2  Salva tratta");
                                System.out.println("3  Salva percorrenza");
                                System.out.println("4  Salva rivenditore");
                                System.out.println("0  Torna indietro");

                                sceltaInserimento = scanner.nextInt();

                                switch (sceltaInserimento) {
                                    case 1:
                                        System.out.println("Inserisci codice del mezzo:");
                                        String codice = scanner.nextLine();

                                        System.out.println("Inserisci 1 se il mezzo è un Tram o 2 se è un Bus:");
                                        int sceltaTipo = scanner.nextInt();
                                        scanner.nextLine();

                                        TipoMezzo tipo;
                                        if (sceltaTipo < 1 || sceltaTipo > 2) {
                                            System.out.println("numero digitatato errato...ripetere operazione");
                                            break;
                                        }
                                        if (sceltaTipo == 1) {
                                            tipo = TipoMezzo.TRAM;
                                        } else {
                                            tipo = TipoMezzo.BUS;
                                        }

                                        System.out.println("Inserisci capienza del mezzo:");
                                        int capienza = scanner.nextInt();
                                        scanner.nextLine();

                                        Mezzo mezzoNuovo = new Mezzo(codice, tipo, StatoMezzo.IN_SERVIZIO, capienza);

                                        mezzoDAO.save(mezzoNuovo);

                                        System.out.println("Mezzo salvato:");
                                        System.out.println(mezzoNuovo);
                                    case 2:
                                        // chiedi dati tratta
                                        System.out.println("Inserisci zona di partenza:");
                                        String partenza = scanner.nextLine();

                                        System.out.println("Inserisci zona di arrivo:");
                                        String arrivo = scanner.nextLine();

                                        System.out.println("Inserisci durata in minuti:");
                                        int minuti = scanner.nextInt();

                                        Tratta nuovaTratta = new Tratta(partenza, arrivo, minuti);

                                        trattaDAO.salvaTratta(nuovaTratta);

                                        System.out.println("Tratta salvata:");
                                        System.out.println(nuovaTratta);
                                        break;
                                    case 3:
                                        // chiedi dati percorrenza
                                        try {
                                            System.out.println("Inserisci id del mezzo:");
                                            String idmezzo = scanner.nextLine();

                                            System.out.println("Inserisci id della tratta:");
                                            String idTratta = scanner.nextLine();

                                            Mezzo mezzo = mezzoDAO.findById(idmezzo);
                                            Tratta tratta = trattaDAO.trovaPerID(idTratta);

                                            System.out.println("Inserisci anno partenza:");
                                            int annoPartenza = scanner.nextInt();

                                            System.out.println("Inserisci mese partenza:");
                                            int mesePartenza = scanner.nextInt();

                                            System.out.println("Inserisci giorno partenza:");
                                            int giornoPartenza = scanner.nextInt();

                                            System.out.println("Inserisci ora partenza:");
                                            int oraPartenza = scanner.nextInt();

                                            System.out.println("Inserisci minuti partenza:");
                                            int minutiPartenza = scanner.nextInt();

                                            System.out.println("Inserisci anno arrivo:");
                                            int annoArrivo = scanner.nextInt();

                                            System.out.println("Inserisci mese arrivo:");
                                            int meseArrivo = scanner.nextInt();

                                            System.out.println("Inserisci giorno arrivo:");
                                            int giornoArrivo = scanner.nextInt();

                                            System.out.println("Inserisci ora arrivo:");
                                            int oraArrivo = scanner.nextInt();

                                            System.out.println("Inserisci minuti arrivo:");
                                            int minutiArrivo = scanner.nextInt();
                                            scanner.nextLine();

                                            LocalDateTime partenzap = LocalDateTime.of(
                                                    annoPartenza, mesePartenza, giornoPartenza, oraPartenza, minutiPartenza
                                            );

                                            LocalDateTime arrivop = LocalDateTime.of(
                                                    annoArrivo, meseArrivo, giornoArrivo, oraArrivo, minutiArrivo
                                            );

                                            Percorrenza percorrenzaNuova = new Percorrenza(mezzo, tratta, partenzap, arrivop);

                                            percorrenzaDAO.salvapERCORRENZA(percorrenzaNuova);

                                            System.out.println("Percorrenza salvata:");
                                            System.out.println(percorrenzaNuova);

                                        } catch (Exception e) {
                                            System.out.println("Errore inserimento percorrenza: " + e.getMessage());
                                        }
                                        break;
                                    case 4:
                                        try {
                                            System.out.println("Inserisci 1 per Rivenditore Autorizzato o 2 per Distributore Automatico:");
                                            int sceltaRiv = scanner.nextInt();
                                            scanner.nextLine();

                                            System.out.println("Inserisci nome attività:");
                                            String nome = scanner.nextLine();

                                            if (sceltaRiv == 1) {
                                                // Rivenditore autorizzato
                                                RivenditoreAutorizzato riv = new RivenditoreAutorizzato(nome);

                                                rivenditoreDAO.save(riv);

                                                System.out.println("Rivenditore autorizzato salvato:");
                                                System.out.println(riv);

                                            } else if (sceltaRiv == 2) {
                                                // Distributore automatico
                                                System.out.println("Inserisci 1 se attivo, 2 se fuori servizio:");
                                                int stato = scanner.nextInt();
                                                scanner.nextLine();

                                                boolean attivo = stato == 1;

                                                DistributoreAutomatico dist = new DistributoreAutomatico(nome, attivo);

                                                rivenditoreDAO.save(dist);

                                                System.out.println("Distributore automatico salvato:");
                                                System.out.println(dist);

                                            } else {
                                                System.out.println("Scelta non valida.");
                                            }

                                        } catch (Exception e) {
                                            System.out.println("Errore inserimento rivenditore: " + e.getMessage());
                                        }
                                        break;
                                    case 0:
                                        System.out.println("Ritorno al menu principale");
                                        break;
                                    default:
                                        System.out.println("Scelta non valida");
                                }
                            } while (sceltaInserimento != 0);
                        } catch (Exception e) {
                            System.out.println("Errore nel menu inserimento: " + e.getMessage());
                        }
                        break;

                    case 0:
                        System.out.println("Uscita dal programma.");
                        break;

                    default:
                        System.out.println("Scelta non valida.");
                }


            } while (scelta != 0);
        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
            e.printStackTrace();
        }

        scanner.close();
        em.close();
        emf.close();
    }

}


/*
        try {
            System.out.println(" INIZIO TEST MEZZI ");

            // creo un mezzo
            Mezzo bus1 = new Mezzo("BUS-01", TipoMezzo.BUS, StatoMezzo.IN_SERVIZIO, 80);

            // salvo il mezzo e creo automaticamente anche il primo periodo iniziale
            mezzoDAO.save(bus1);
            System.out.println("Mezzo salvato:");
            System.out.println(bus1);

            // passaggio automatico in manutenzione
            mezzoDAO.cambiaStato(bus1.getId().toString(), StatoMezzo.IN_MANUTENZIONE, LocalDate.now());

            System.out.println("Mezzo aggiornato in manutenzione:");
            System.out.println(mezzoDAO.findById(bus1.getId().toString()));

            // ritorno automatico in servizio
            mezzoDAO.cambiaStato(bus1.getId().toString(), StatoMezzo.IN_SERVIZIO, LocalDate.now().plusDays(2));

            System.out.println("Mezzo tornato in servizio:");
            System.out.println(mezzoDAO.findById(bus1.getId().toString()));



        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
            e.printStackTrace();
        }
*/