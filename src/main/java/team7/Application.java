package team7;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import team7.dao.*;
import team7.entities.*;
import team7.enumm.StatoMezzo;
import team7.enumm.TipoAbbonamento;
import team7.enumm.TipoMezzo;
import team7.exeption.NonTrovato;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import static java.lang.Integer.parseInt;
// aggiungere trim() allo scanner...chiedere raffa o fede

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
        //per utente
        UtenteDAO utenteDAO = new UtenteDAO(em);
        //da qua salviamo utenti, mezzi, tratte, percorrenze


        Scanner scanner = new Scanner(System.in);
        int scelta;
        //Utente crea
        Utente u1 = new Utente("Mario", "Rossi");
        Utente u2 = new Utente("Maurizio", "Verdi");
        Utente u3 = new Utente("Massimo", "Bianchi");

        utenteDAO.save(u1);
        utenteDAO.save(u2);
        utenteDAO.save(u3);//senza tessera

        //tessera
        Tessera t1 = utenteDAO.creaNuovaTessera(u1);
        Tessera t2 = utenteDAO.creaNuovaTessera(u2);
        //t1 salvato in data odierna
        t2.setDataDiScadenza(LocalDate.of(2025, 10, 14));
        utenteDAO.saveTessera(t2);

        //         EMISSIONE BIGLIETTO
        Rivenditore r1 = new RivenditoreAutorizzato("Bar Roma");
        Rivenditore r2 = new RivenditoreAutorizzato("Bar Coop");

        rivenditoreDAO.save(r1);
        rivenditoreDAO.save(r2);

        Biglietto b1 = rivenditoreDAO.emettiBiglietto(r1);
        Biglietto b2 = rivenditoreDAO.emettiBiglietto(r1);
        Biglietto b3 = rivenditoreDAO.emettiBiglietto(r2);
        Biglietto b4 = rivenditoreDAO.emettiBiglietto(r2);

        Abbonamento a1 = rivenditoreDAO.emettiAbbonamento(r1, TipoAbbonamento.ANNUALE, t1);
        Abbonamento a2 = rivenditoreDAO.emettiAbbonamento(r1, TipoAbbonamento.MENSILE, t1);
        Abbonamento a3 = rivenditoreDAO.emettiAbbonamento(r1, TipoAbbonamento.SETTIMANALE, t2);
        Abbonamento a4 = rivenditoreDAO.emettiAbbonamento(r2, TipoAbbonamento.ANNUALE, t1);
        Abbonamento a5 = rivenditoreDAO.emettiAbbonamento(r2, TipoAbbonamento.MENSILE, t1);
        Abbonamento a6 = rivenditoreDAO.emettiAbbonamento(r2, TipoAbbonamento.SETTIMANALE, t2);

        System.out.println("Biglietti dal primo rivenditore");
        System.out.println(b1);
        System.out.println(b2);

        System.out.println("Biglitti dal secondo riovenditore");
        System.out.println(b3);
        System.out.println(b4);

        System.out.println("Abbonamenti dal primo rivenditore");
        System.out.println(a1);
        System.out.println(a2);
        System.out.println(a3);

        System.out.println("Abbonamenti dal secondo rivenditore");
        System.out.println(a4);
        System.out.println(a5);
        System.out.println(a6);

        //tratte
        Tratta tratta1 = new Tratta("Termini", "Ostia Lido", 28);
        Tratta tratta2 = new Tratta("Piazza Venezia", "EUR Magliana", 10);
        Tratta tratta3 = new Tratta("San Pietro", "Colosseo", 5);
        Tratta tratta4 = new Tratta("Ponte Milvio", "Trastevere", 8);
        //salviamo
        trattaDAO.salvaTratta(tratta1);
        trattaDAO.salvaTratta(tratta2);
        trattaDAO.salvaTratta(tratta3);
        trattaDAO.salvaTratta(tratta4);
        //percorrenze
        // prima devo creare i mezzi
        Mezzo mezzo1 = new Mezzo("BUS-CAN-01", TipoMezzo.BUS, StatoMezzo.IN_SERVIZIO, 45);
        mezzoDAO.save(mezzo1);
        Mezzo mezzo2 = new Mezzo("BUS-CAN-02", TipoMezzo.BUS, StatoMezzo.IN_SERVIZIO, 20);
        mezzoDAO.save(mezzo1);
        Mezzo mezzo3 = new Mezzo("TRAM-MAD-03", TipoMezzo.TRAM, StatoMezzo.IN_SERVIZIO, 120);
        mezzoDAO.save(mezzo1);
        Mezzo mezzo4 = new Mezzo("BUS-MAD-04", TipoMezzo.TRAM, StatoMezzo.IN_SERVIZIO, 200);
        mezzoDAO.save(mezzo1);
        Mezzo mezzo5 = new Mezzo("BUS-CAN-05", TipoMezzo.BUS, StatoMezzo.IN_MANUTENZIONE, 40);
        mezzoDAO.save(mezzo1);

        // - registra una percorrenza di un mezzo su una tratta
        LocalDateTime datapartenza = LocalDateTime.now();
        LocalDateTime dataarrivo = datapartenza.plusMinutes(28);
        //ora vado a inserire i dati nella percorrenza
        Percorrenza percorrenza1 = new Percorrenza(mezzo1, tratta1, datapartenza, dataarrivo);
        percorrenzaDAO.salvapERCORRENZA(percorrenza1);
        //rimetto la tempistica di arrivo per percorrenza2
         dataarrivo = datapartenza.plusMinutes(10);
        Percorrenza percorrenza2 = new Percorrenza(mezzo2, tratta2, datapartenza, dataarrivo);
        percorrenzaDAO.salvapERCORRENZA(percorrenza2);
        //percorrenza3
        dataarrivo = datapartenza.plusMinutes(5);
        Percorrenza percorrenza3 = new Percorrenza(mezzo3, tratta2, datapartenza, dataarrivo);
        percorrenzaDAO.salvapERCORRENZA(percorrenza3);
        //percorrenza4
        dataarrivo = datapartenza.plusMinutes(8);
        Percorrenza percorrenza4 = new Percorrenza(mezzo4, tratta2, datapartenza, dataarrivo);
        percorrenzaDAO.salvapERCORRENZA(percorrenza4);


        try {
            do {
                System.out.println("MENU ");
                System.out.println("1 Registrati");
                System.out.println("2 Compra Biglietto");
                System.out.println("3 Compra Abbonamento ");
                System.out.println("4 Valida Abbonamento");
                System.out.println("5 Rivenditore");
                System.out.println("6 Test MezzoDAO");
                System.out.println("7 Test PeriodoStatoMezzoDAO");
                System.out.println("8 Vedi Tratta");
                System.out.println("9 Vedi Percorrenza");
                System.out.println("10 Controllo amministratore");
                System.out.println("0 Esci");

                scelta = parseInt(scanner.nextLine());

                switch (scelta) {
                    case 1:
                        // Registrazione
                        System.out.println(" Registrazione avviata");
                        System.out.println("Fornire Nome");
                        String nome = scanner.nextLine();
                        System.out.println("Fornire Cognome");
                        String cognome = scanner.nextLine();
                        Utente ucrea = new Utente(nome, cognome);
                        utenteDAO.save(ucrea);
                        break;

                    case 2:
                        // Emissione biglietti da rivenditore

                        break;

                    case 3:
                        // Compra Abbonamento
                        //se la tessera è scaduta ..dire di rinnovarla

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

                        try {
                            Mezzo bus64 = mezzoDAO.findByCodice("BUS-64");
                            if (bus64 == null) {
                                bus64 = new Mezzo("BUS-64", TipoMezzo.BUS, StatoMezzo.IN_SERVIZIO, 80);
                                mezzoDAO.save(bus64);
                                System.out.println("Creato: " + bus64);
                            } else {
                                System.out.println("BUS-64 esiste già:");
                                System.out.println(bus64);
                            }

                            Mezzo tram12 = mezzoDAO.findByCodice("TRAM-12");
                            if (tram12 == null) {
                                tram12 = new Mezzo("TRAM-12", TipoMezzo.TRAM, StatoMezzo.IN_SERVIZIO, 120);
                                mezzoDAO.save(tram12);
                                System.out.println("Creato: " + tram12);
                            } else {
                                System.out.println("TRAM-12 esiste già:");
                                System.out.println(tram12);
                            }

                            Mezzo bus78 = mezzoDAO.findByCodice("BUS-78");
                            if (bus78 == null) {
                                bus78 = new Mezzo("BUS-78", TipoMezzo.BUS, StatoMezzo.IN_MANUTENZIONE, 50);
                                mezzoDAO.save(bus78);
                                System.out.println("Creato: " + bus78);
                            } else {
                                System.out.println("BUS-78 esiste già:");
                                System.out.println(bus78);
                            }

                            // creo lo storico
                            if (periodoDAO.trovaStoricoPerMezzo(bus64.getId().toString()).size() == 1) {
                                mezzoDAO.cambiaStato(
                                        bus64.getId().toString(),
                                        StatoMezzo.IN_MANUTENZIONE,
                                        LocalDate.now().minusDays(3)
                                );

                                mezzoDAO.cambiaStato(
                                        bus64.getId().toString(),
                                        StatoMezzo.IN_SERVIZIO,
                                        LocalDate.now().minusDays(1)
                                );

                                System.out.println("Storico BUS-64 aggiornato con un periodo di manutenzione.");
                            }

                            // prendo una tratta già esistente, altrimenti la creo
                            Tratta trattaTest;
                            List<Tratta> tratte = trattaDAO.TrovaTutteLeTratte();

                            if (tratte.isEmpty()) {
                                trattaTest = new Tratta("Deposito", "Centro", 30);
                                trattaDAO.salvaTratta(trattaTest);
                                System.out.println("Creata tratta di test: " + trattaTest);
                            } else {
                                trattaTest = tratte.get(0);
                                System.out.println("Uso tratta già esistente: " + trattaTest);
                            }

                            // collegamento mezzo-tratta tramite percorrenza
                            Percorrenza percorrenzaTest = new Percorrenza(
                                    bus64,
                                    trattaTest,
                                    LocalDateTime.now(),
                                    LocalDateTime.now().plusMinutes(35)
                            );
                            percorrenzaDAO.salvapERCORRENZA(percorrenzaTest);

                            System.out.println("BUS-64 assegnato alla tratta tramite percorrenza:");
                            System.out.println(percorrenzaTest);

                            System.out.println("Mezzi di tipo BUS:");
                            mezzoDAO.findByTipo(TipoMezzo.BUS).forEach(System.out::println);

                            System.out.println("Mezzi in manutenzione:");
                            mezzoDAO.findByStato(StatoMezzo.IN_MANUTENZIONE).forEach(System.out::println);

                        } catch (Exception e) {
                            System.out.println("Errore: " + e.getMessage());
                            e.printStackTrace();
                        }

                        break;

                    case 7:
                        // TEST PERIODOSTATOMEZZODAO

                        try {
                            System.out.println("STORICO MEZZO");

                            Mezzo mezzoTest = mezzoDAO.findByCodice("BUS-64");

                            if (mezzoTest == null) {
                                System.out.println("Mezzo BUS-64 non trovato. Esegui prima il case 6.");
                                break;
                            }

                            System.out.println("Mezzo trovato:");
                            System.out.println(mezzoTest);

                            System.out.println("Storico completo del mezzo:");
                            periodoDAO.trovaStoricoPerMezzo(mezzoTest.getId().toString())
                                    .forEach(System.out::println);

                            System.out.println("Periodi di manutenzione:");
                            if (periodoDAO.trovaManutenzioniPerMezzo(mezzoTest.getId().toString()).isEmpty()) {
                                System.out.println("Nessun periodo di manutenzione trovato.");
                            } else {
                                periodoDAO.trovaManutenzioniPerMezzo(mezzoTest.getId().toString())
                                        .forEach(System.out::println);
                            }

                            System.out.println("Periodo attivo attuale:");
                            System.out.println(periodoDAO.trovaPeriodoAttivoPerMezzo(mezzoTest.getId().toString()));

                        } catch (Exception e) {
                            System.out.println("Errore: " + e.getMessage());
                            e.printStackTrace();
                        }

                        break;

                    case 8:
                        System.out.println("Menu delle Tratte ");
                        System.out.println("1 Trova tratta per id");
                        System.out.println("2 Mostra tutte le tratte");
                        System.out.println("3 Cancella tratta per id ");
                        System.out.println("4 Conta quante volte quella tratta e' stata percorsa");

                        int sceltaTratta = parseInt(scanner.nextLine());
                 switch (sceltaTratta){
                 case 1:
                 try{
                  System.out.println("Inserisci id della tratta:");
                   String idtratta = scanner.nextLine();
                    Tratta trovato= trattaDAO.trovaPerID(idtratta);
                     System.out.println(trovato);
                   }catch(NonTrovato e){
                    System.out.println("Errore: tratta non trovata!" +e.getMessage());
                     }
                   break;
                  case 2:
                  List<Tratta> listatratte =trattaDAO.TrovaTutteLeTratte();
                   System.out.println("--- ELENCO TRATTE ---");
                  listatratte.forEach(System.out::println);
                      break;
                  case 3:
                    try{
                   System.out.println("Inserisci id della tratta per cancellarla:");
                   String idtrattaD = scanner.nextLine();
                   Tratta trovato= trattaDAO.cancellaTratta(idtrattaD);
                   System.out.println(trovato);
                   }catch(NonTrovato e){
                   System.out.println("Errore: tratta non trovata!" +e.getMessage());
                   }
                  break;
                  case 4:
                   try {
                    System.out.println("Inserisci ID tratta da contare:");
                    String idDaContare = scanner.nextLine();
                    Tratta trattaDaContare = trattaDAO.trovaPerID(idDaContare);
                    long count = trattaDAO.ContaVolteperTratta(trattaDaContare);
                   System.out.println("La tratta " + trattaDaContare.getZonaPartenza() + " - " + trattaDaContare.getZonaFinale() + " è stata percorsa " + count + " volte.");
                    } catch (NonTrovato e) {
                  System.out.println("Errore: tratta non trovata!"+ e.getMessage());
                   }
                      break;
                       default:
                         System.out.println("Errore....Scelta sbagliata");
                     }
                        break;
                    case 9:
                        System.out.println("Menu delle Percorrenze ");
                        System.out.println("1 Trova percorrenze per tratta  ");
                        System.out.println("2 Trova percorrenze per mezzo");
                        System.out.println("3 Trova percorrenze per periodo");
                        System.out.println("4 Calcola tempo medio per tratta");
                        System.out.println("5 Calcola tempo medio per tratta e mezzo");
                        System.out.println("6 Calcola tempo tra percorrenze");

                        int sceltaPercorrenza = parseInt(scanner.nextLine());

                        switch (sceltaPercorrenza) {
                            case 1:
                                try {
                                    System.out.println("Fornire id della tratta:");
                                    String trattaId = scanner.nextLine();

                                    List<Percorrenza> listapercorrenze = percorrenzaDAO.TrovaTratta(trattaId);
                                    System.out.println("lista delle percorrenze trovate");
                                    listapercorrenze.forEach(System.out::println);

                                } catch (Exception e) {
                                    System.out.println("Errore: " + e.getMessage());
                                }
                                break;

                            case 2:
                                try {
                                    System.out.println("Fornire id del mezzo:");
                                    String mezzoId = scanner.nextLine();

                                    List<Percorrenza> listapercorrenze2 = percorrenzaDAO.trovaPerMezzo(mezzoId);

                                    System.out.println("lista delle percorrenze trovate");
                                    listapercorrenze2.forEach(System.out::println);

                                } catch (Exception e) {
                                    System.out.println("Errore: " + e.getMessage());
                                }
                                break;

                            case 3:
                                try {
                                    System.out.println("Inserisci data/ora inizio (es: 2026-04-02T08:00):");
                                    LocalDateTime inizio = LocalDateTime.parse(scanner.nextLine());

                                    System.out.println("Inserisci data/ora fine (es: 2026-04-02T18:00):");
                                    LocalDateTime fine = LocalDateTime.parse(scanner.nextLine());

                                    List<Percorrenza> lista = percorrenzaDAO.trovaPerPeriodo(inizio, fine);

                                    System.out.println("Perccorenze trovate per perioddo");
                                    lista.forEach(System.out::println);

                                } catch (Exception e) {
                                    System.out.println("Errore: " + e.getMessage());
                                }
                                break;

                            case 4:
                                try {
                                    System.out.println("Fornire id della tratta:");
                                    String trattaId = scanner.nextLine();

                                    Double media = percorrenzaDAO.CalcolaTempoMedio(trattaId);

                                    System.out.println("Tempo medio della tratta: " + media);

                                } catch (Exception e) {
                                    System.out.println("Errore: " + e.getMessage());
                                }
                                break;

                            case 5:
                                try {
                                    System.out.println("Fornire id della tratta:");
                                    String trattaId = scanner.nextLine();

                                    System.out.println("Fornire id del mezzo:");
                                    String mezzoId = scanner.nextLine();

                                    Double media = percorrenzaDAO.CalcolaTempoMedioperMEzzo(trattaId, mezzoId);

                                    System.out.println("Tempo medio della tratta per mezzo: " + media);

                                } catch (Exception e) {
                                    System.out.println("Errore: " + e.getMessage());
                                }
                                break;

                            case 6:
                                try {
                                    System.out.println("Fornire id della percorrenza:");
                                    String idPercorrenza = scanner.nextLine();
                                    Double tempo = percorrenzaDAO.CalcolaTempotraPercorrenze(UUID.fromString(idPercorrenza));
                                    System.out.println("Tempo calcolato: " + tempo);
                                } catch (Exception e) {
                                    System.out.println("Errore: " + e.getMessage());
                                }
                                break;

                            default:
                                System.out.println("Errore....Scelta sbagliata");
                        }
                        break;
                    case 10:

                        int passcode = 5432;
                        System.out.println("Inserisci il passcode:");
                        int pass = scanner.nextInt();
                        scanner.nextLine();
                        if (passcode != pass) {
                            System.out.println(" passcode errata! ");
                            break;
                        }
                        try {
                            int sceltaInserimento;
                            do {
                                System.out.println("MENU INSERIMENTO");
                                System.out.println("1  Salva mezzo");
                                System.out.println("2  Salva tratta");
                                System.out.println("3  Salva percorrenza");
                                System.out.println("4  Salva rivenditore");
                                System.out.println("5  Totale biglietti/abbonamenti emessi per tempo");
                                System.out.println("6  Totale biglietti/abbonamenti emessi per mezzo");
                                System.out.println("7  Storico Manutenzione");
                                System.out.println("8  Calcola il tempo medio effettivo di percorrenza di una tratta da parte di un mezzo");

                                System.out.println("0  Torna indietro");

                                sceltaInserimento = scanner.nextInt();

                                switch (sceltaInserimento) {
                                    case 1:
                                        System.out.println("Inserisci codice del mezzo:");
                                        String codice = scanner.nextLine().trim();

                                        System.out.println("Inserisci 1 se il mezzo è un Tram o 2 se è un Bus:");

                                        if (!scanner.hasNextInt()) {
                                            System.out.println("Input non valido!");
                                            scanner.nextLine();
                                            break;
                                        }

                                        int sceltaTipo = scanner.nextInt();
                                        scanner.nextLine();

                                        TipoMezzo tipo;

                                        if (sceltaTipo < 1 || sceltaTipo > 2) {
                                            System.out.println("Numero digitato errato... ripetere operazione");
                                            break;
                                        }

                                        if (sceltaTipo == 1) {
                                            tipo = TipoMezzo.TRAM;
                                        } else {
                                            tipo = TipoMezzo.BUS;
                                        }

                                        int capienza = 0;
                                        boolean valida = false;

                                        while (!valida) {
                                            System.out.println("Inserisci capienza:");

                                            if (!scanner.hasNextInt()) {
                                                System.out.println("Inserisci un numero valido!");
                                                scanner.nextLine();
                                                continue;
                                            }

                                            capienza = scanner.nextInt();
                                            scanner.nextLine();

                                            if (tipo == TipoMezzo.TRAM && (capienza < 50 || capienza > 300)) {
                                                System.out.println("Capienza non valida per tram (50-300)");
                                            } else if (tipo == TipoMezzo.BUS && (capienza < 20 || capienza > 50)) {
                                                System.out.println("Capienza non valida per bus (20-120)");
                                            } else {
                                                valida = true;
                                            }
                                        }

                                        Mezzo mezzoNuovo = new Mezzo(codice, tipo, StatoMezzo.IN_SERVIZIO, capienza);
                                        mezzoDAO.save(mezzoNuovo);

                                        System.out.println("Mezzo salvato:");
                                        System.out.println(mezzoNuovo);
                                        break;
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
                                             nome = scanner.nextLine();

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
