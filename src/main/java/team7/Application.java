package team7;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import team7.dao.*;
import team7.entities.*;
import team7.enumm.StatoMezzo;
import team7.enumm.TipoAbbonamento;
import team7.enumm.TipoMezzo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("team7");

    private static final List<Rivenditore> rivenditori = new ArrayList<>();

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();

        //DAO Titoli di viaggio

        BigliettoDAO bigliettoDAO = new BigliettoDAO(em);
        AbbonamentoDAO abbonamentoDAO = new AbbonamentoDAO(em);

        // DAO mezzi e storico
        MezzoDAO mezzoDAO = new MezzoDAO(em);
        PeriodoStatoMezzoDAO periodoDAO = new PeriodoStatoMezzoDAO(em);

        // DAO tratte e percorrenze
        TrattaDAO trattaDAO = new TrattaDAO(em);
        PercorrenzaDAO percorrenzaDAO = new PercorrenzaDAO(em);

        // DAO rivenditori e utenti
        RivenditoreDAO rivenditoreDAO = new RivenditoreDAO(em);
        UtenteDAO utenteDAO = new UtenteDAO(em);

        Scanner scanner = new Scanner(System.in);
        int scelta;

        // Dati di test già presenti nel progetto
        Utente u1 = new Utente("Mario", "Rossi");
        Utente u2 = new Utente("Maurizio", "Verdi");
        Utente u3 = new Utente("Massimo", "Bianchi");

        utenteDAO.save(u1);
        utenteDAO.save(u2);
        utenteDAO.save(u3);

        Tessera t1 = utenteDAO.creaNuovaTessera(u1);
        Tessera t2 = utenteDAO.creaNuovaTessera(u2);
        t2.setDataDiScadenza(LocalDate.of(2025, 10, 14));
        utenteDAO.saveTessera(t2);

        Rivenditore r1 = new RivenditoreAutorizzato("Bar Roma");
        Rivenditore r2 = new RivenditoreAutorizzato("Bar Coop");
        Rivenditore r3 = new DistributoreAutomatico("Bar Arcobaleno", false);
        Rivenditore r4 = new DistributoreAutomatico("Automatico FS", true);

        rivenditoreDAO.save(r1);
        rivenditoreDAO.save(r2);
        rivenditoreDAO.save(r3);
        rivenditoreDAO.save(r4);

        rivenditori.add(r1);
        rivenditori.add(r2);
        rivenditori.add(r3);
        rivenditori.add(r4);

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

        System.out.println("Biglietti dal secondo rivenditore");
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

        try {
            do {
                System.out.println("\nMENU");
                System.out.println("1 Registrati");
                System.out.println("2 Compra Biglietto");
                System.out.println("3 Compra Abbonamento");
                System.out.println("4 Valida Abbonamento");
                System.out.println("5 Rivenditore");
                System.out.println("6 Gestione mezzi");
                System.out.println("7 Storico mezzi");
                System.out.println("8 Test TrattaDAO");
                System.out.println("9 Test PercorrenzaDAO");
                System.out.println("10 Controllo amministratore");
                System.out.println("0 Esci");

                scelta = leggiIntero(scanner, "Scelta: ");

                switch (scelta) {
                    case 1:
                        System.out.println("Registrazione avviata");
                        System.out.print("Fornire Nome: ");
                        String nome = scanner.nextLine().trim();
                        System.out.print("Fornire Cognome: ");
                        String cognome = scanner.nextLine().trim();

                        Utente utenteCreato = new Utente(nome, cognome);
                        utenteDAO.save(utenteCreato);
                        System.out.println("Utente registrato: " + utenteCreato);
                        break;

                    case 2:
                        // Emissione biglietti da rivenditore
                        System.out.println("Funzionalità non ancora implementata.");
                        // compraBiglietto
                        Scanner s = new Scanner(System.in);

                        System.out.println("Per procedere all'acquisto di un biglietto selezionare prima il rivenditore:\n" +
                                "1 - Bar Coop\n" +
                                "2 - Bar Roma\n" +
                                "3 - Automatico Bar Arcobaleno\n" +
                                "4 - Automatico Stazione FS\n" +
                                "5 - Indietro");

                        int riv = s.nextInt();
                        Rivenditore r = switch (riv) {
                            case 1 -> r1;
                            case 2 -> r2;
                            case 3 -> r3;
                            case 4 -> r4;
                            case 5 -> r1; //trovare il modod di tornare indietro
                            default -> null;
                        };
                        if (r == null) System.out.println("Valore inserito non riconosciuto, si prega di riprovare");


                        break;


                    case 3:
                        // Compra Abbonamento
                        // se la tessera è scaduta, dire di rinnovarla
                        System.out.println("Funzionalità non ancora implementata.");
                        break;

                    case 4:
                        // TEST BIGLIETTODAO
                        System.out.println("Funzionalità non ancora implementata.");
                        break;

                    case 5:
                        // TEST RIVENDITOREDAO
                        System.out.println("Funzionalità non ancora implementata.");
                        break;

                    /*
                    VECCHIO CASE 6 - TEST MEZZODAO

                    case 6:
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

                            List<PeriodoStatoMezzo> storicoBus64 = periodoDAO.trovaStoricoPerMezzo(bus64.getId().toString());

                            if (storicoBus64.size() == 1) {
                                LocalDate dataInizioPrimoPeriodo = storicoBus64.get(0).getDataInizio();
                                LocalDate dataInizioManutenzione = dataInizioPrimoPeriodo.plusDays(2);
                                LocalDate dataRitornoServizio = dataInizioPrimoPeriodo.plusDays(4);

                                mezzoDAO.cambiaStato(
                                        bus64.getId().toString(),
                                        StatoMezzo.IN_MANUTENZIONE,
                                        dataInizioManutenzione
                                );

                                mezzoDAO.cambiaStato(
                                        bus64.getId().toString(),
                                        StatoMezzo.IN_SERVIZIO,
                                        dataRitornoServizio
                                );

                                System.out.println("Storico BUS-64 aggiornato con un periodo di manutenzione.");
                            }

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

                            LocalDateTime partenzaTest = LocalDateTime.now();
                            Percorrenza percorrenzaTest = new Percorrenza(
                                    bus64,
                                    trattaTest,
                                    partenzaTest,
                                    partenzaTest.plusMinutes(35)
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
                    */
                    case 6:
                        menuGestioneMezzi(scanner, mezzoDAO, trattaDAO, percorrenzaDAO);
                        break;

                    /*
                    VECCHIO CASE 7 - TEST PERIODOSTATOMEZZODAO

                    case 7:
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

                            List<PeriodoStatoMezzo> manutenzioni = periodoDAO.trovaManutenzioniPerMezzo(mezzoTest.getId().toString());

                            System.out.println("Periodi di manutenzione:");
                            if (manutenzioni.isEmpty()) {
                                System.out.println("Nessun periodo di manutenzione trovato.");
                            } else {
                                manutenzioni.forEach(System.out::println);
                            }

                            System.out.println("Periodo attivo attuale:");
                            System.out.println(periodoDAO.trovaPeriodoAttivoPerMezzo(mezzoTest.getId().toString()));

                        } catch (Exception e) {
                            System.out.println("Errore: " + e.getMessage());
                            e.printStackTrace();
                        }
                        break;
                    */
                    case 7:
                        menuStoricoMezzi(scanner, mezzoDAO, periodoDAO);
                        break;

                    case 8:
                        Tratta trattaTest = new Tratta("Centro", "Stazione", 45);
                        trattaDAO.salvaTratta(trattaTest);
                        System.out.println("Tratta salvata:");
                        System.out.println("ID: " + trattaTest.getId());
                        System.out.println("Partenza: " + trattaTest.getZonaPartenza());
                        System.out.println("Arrivo: " + trattaTest.getZonaFinale());
                        System.out.println("Durata: " + trattaTest.getTempoPercorrenzaFormattato());

                        Tratta trattaTrovata = trattaDAO.trovaPerID(trattaTest.getId().toString());
                        System.out.println("Tratta trovata per ID:");
                        System.out.println("ID: " + trattaTrovata.getId());
                        System.out.println("Partenza: " + trattaTrovata.getZonaPartenza());
                        System.out.println("Arrivo: " + trattaTrovata.getZonaFinale());
                        System.out.println("Durata: " + trattaTrovata.getTempoPercorrenzaFormattato());

                        System.out.println("Tutte le tratte:");
                        trattaDAO.TrovaTutteLeTratte().forEach(t ->
                                System.out.println(t.getId() + " - " + t.getZonaPartenza() + " -> " + t.getZonaFinale() + " - " + t.getTempoPercorrenzaFormattato()));
                        break;

                    case 9:
                        Tratta trattaPercorrenza = new Tratta("Deposito", "Capolinea Nord", 35);
                        trattaDAO.salvaTratta(trattaPercorrenza);

                        Mezzo mezzoPercorrenza = new Mezzo("BUS-PERC-01", TipoMezzo.BUS, StatoMezzo.IN_SERVIZIO, 85);
                        mezzoDAO.save(mezzoPercorrenza);

                        LocalDateTime dataPartenza = LocalDateTime.now();
                        LocalDateTime dataArrivo = dataPartenza.plusMinutes(42);

                        Percorrenza percorrenzaTest = new Percorrenza(mezzoPercorrenza, trattaPercorrenza, dataPartenza, dataArrivo);

                        percorrenzaDAO.salvapERCORRENZA(percorrenzaTest);
                        System.out.println("Percorrenza salvata:");
                        System.out.println("ID: " + percorrenzaTest.getId());
                        System.out.println("Mezzo: " + percorrenzaTest.getMezzo().getId());
                        System.out.println("Tratta: " + percorrenzaTest.getTratta().getId());
                        System.out.println("Partenza: " + percorrenzaTest.getDataOraPartenza());
                        System.out.println("Arrivo: " + percorrenzaTest.getDataOraArrivo());
                        System.out.println("Tempo effettivo: " + percorrenzaTest.getTempoEffettivoPercorrenza().toMinutes() + " minuti");
                        System.out.println("Scostamento: " + percorrenzaTest.getScostamentoRispettoAlPrevisto());

                        System.out.println("Tempo effettivo registrato correttamente.");

                        int numeroPercorrenze = percorrenzaDAO
                                .trovaPerMezzo(mezzoPercorrenza.getId().toString())
                                .size();
                        System.out.println("Numero percorrenze del mezzo: " + numeroPercorrenze);

                        Double mediaTratta = percorrenzaDAO.CalcolaTempoMedio(trattaPercorrenza.getId().toString());
                        System.out.println("Tempo medio effettivo della tratta: " + mediaTratta);

                        Double mediaTrattaMezzo = percorrenzaDAO.CalcolaTempoMedioperMEzzo(
                                trattaPercorrenza.getId().toString(),
                                mezzoPercorrenza.getId().toString()
                        );
                        System.out.println("Tempo medio effettivo della tratta per il mezzo: " + mediaTrattaMezzo);

                        System.out.println("Percorrenze del mezzo:");
                        percorrenzaDAO.trovaPerMezzo(mezzoPercorrenza.getId().toString()).forEach(System.out::println);

                        System.out.println("Percorrenze della tratta:");
                        percorrenzaDAO.TrovaTratta(trattaPercorrenza.getId().toString()).forEach(System.out::println);
                        break;

                    case 10:
                        int passcode = 5432;
                        int pass = leggiIntero(scanner, "Inserisci il passcode: ");

                        if (passcode != pass) {
                            System.out.println("Passcode errato!");
                            break;
                        }

                        try {
                            int sceltaInserimento;
                            do {
                                System.out.println("\nMENU INSERIMENTO");
                                System.out.println("1  Salva mezzo");
                                System.out.println("2  Salva tratta");
                                System.out.println("3  Salva percorrenza");
                                System.out.println("4  Salva rivenditore");
                                System.out.println("5  Totale biglietti/abbonamenti emessi per tempo");
                                System.out.println("6  Totale biglietti vidimati per mezzo");
                                System.out.println("7  Storico manutenzione");
                                System.out.println("8  Calcola il tempo medio effettivo di percorrenza di una tratta da parte di un mezzo");
                                System.out.println("9  Gestione mezzi");
                                System.out.println("10 Storico mezzi");
                                System.out.println("0  Torna indietro");

                                sceltaInserimento = leggiIntero(scanner, "Scelta: ");

                                switch (sceltaInserimento) {
                                    case 1:
                                        salvaMezzoDaScanner(scanner, mezzoDAO);
                                        break;

                                    case 2:
                                        salvaTrattaDaScanner(scanner, trattaDAO);
                                        break;

                                    case 3:
                                        salvaPercorrenzaDaScanner(scanner, mezzoDAO, trattaDAO, percorrenzaDAO);
                                        break;

                                    case 4:
                                        salvaRivenditoreDaScanner(scanner, rivenditoreDAO);
                                        break;

                                    case 5:
                                        try {
                                            System.out.println("Vuoi controllare i biglietti o gli abbonamenti?");
                                            String tipo = scanner.nextLine().trim().toLowerCase();

                                            System.out.println("Inserisci data inizio ANNO-MESE-GIORNO");
                                            LocalDate inizio = LocalDate.parse(scanner.nextLine().trim());

                                            System.out.println("Inserisci data fine ANNO-MESE-GIORNO");
                                            LocalDate fine = LocalDate.parse(scanner.nextLine().trim());

                                            if (tipo.equals("biglietti")) {

                                                Long totaleBiglietti = bigliettoDAO.countBigliettiPerPeriodo(inizio, fine);
                                                System.out.println("Totale biglietti emessi nel periodo " + inizio +"-" + fine +":" + totaleBiglietti);

                                            } else if (tipo.equals("abbonamenti")) {

                                                Long totaleAbbonamenti = abbonamentoDAO.countAbbonamentiPeriodo(inizio, fine);
                                                System.out.println("Totale abbonamenti emessi nel periodo " + inizio +"-" + fine +":" +
                                                        totaleAbbonamenti);

                                            } else {
                                                System.out.println("Scelta non valida, controlla di aver scritto 'biglietto' o 'abbonamento'.");
                                            }

                                        } catch (Exception e) {
                                            System.out.println("Errore: " + e.getMessage());
                                        }
                                        break;

                                    case 6:
                                        try {
                                            System.out.println("Inserisci il codice del mezzo:");
                                            String codice = scanner.nextLine().trim();
                                            List<Biglietto> biglietti = bigliettoDAO.findByCodiceMezzo(codice);

                                            if (biglietti.isEmpty()) {
                                                System.out.println("Nessun biglietto vidimato su questo mezzo.");
                                            } else {
                                                System.out.println("Biglietti vidimati sul mezzo " + codice + ":");
                                                biglietti.forEach(System.out::println);
                                                }
                                        } catch (Exception e) {
                                            System.out.println("Errore: " + e.getMessage());
                                        }
                                        break;
                                    case 7:
                                        menuStoricoMezzi(scanner, mezzoDAO, periodoDAO);
                                        break;

                                    case 8:
                                        System.out.println("Funzionalità non ancora implementata.");
                                        break;

                                    case 9:
                                        menuGestioneMezzi(scanner, mezzoDAO, trattaDAO, percorrenzaDAO);
                                        break;

                                    case 10:
                                        menuStoricoMezzi(scanner, mezzoDAO, periodoDAO);
                                        break;

                                    case 0:
                                        System.out.println("Ritorno al menu principale.");
                                        break;

                                    default:
                                        System.out.println("Scelta non valida.");
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

    private static int leggiIntero(Scanner scanner, String messaggio) {
        while (true) {
            try {
                System.out.print(messaggio);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Inserisci un numero valido.");
            }
        }
    }

    private static LocalDate leggiData(Scanner scanner, String messaggio) {
        while (true) {
            try {
                System.out.print(messaggio);
                return LocalDate.parse(scanner.nextLine().trim());
            } catch (DateTimeParseException e) {
                System.out.println("Formato data non valido. Usa yyyy-MM-dd");
            }
        }
    }

    private static LocalDateTime leggiDataOra(Scanner scanner, String messaggio) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        while (true) {
            try {
                System.out.print(messaggio);
                return LocalDateTime.parse(scanner.nextLine().trim(), formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Formato data/ora non valido. Usa yyyy-MM-dd HH:mm");
            }
        }
    }

    private static void stampaListaMezzi(List<Mezzo> mezzi) {
        if (mezzi.isEmpty()) {
            System.out.println("Nessun mezzo trovato.");
            return;
        }

        mezzi.forEach(System.out::println);
    }

    private static void salvaMezzoDaScanner(Scanner scanner, MezzoDAO mezzoDAO) {
        try {
            System.out.print("Inserisci codice del mezzo: ");
            String codice = scanner.nextLine().trim().toUpperCase();

            if (codice.isEmpty()) {
                System.out.println("Il codice non può essere vuoto.");
                return;
            }

            if (mezzoDAO.findByCodice(codice) != null) {
                System.out.println("Esiste già un mezzo con questo codice.");
                return;
            }

            System.out.println("Inserisci 1 se il mezzo è un Tram o 2 se è un Bus:");
            int sceltaTipo = leggiIntero(scanner, "Scelta: ");

            TipoMezzo tipo;
            if (sceltaTipo == 1) {
                tipo = TipoMezzo.TRAM;
            } else if (sceltaTipo == 2) {
                tipo = TipoMezzo.BUS;
            } else {
                System.out.println("Numero digitato errato. Ripetere operazione.");
                return;
            }

            int capienza;
            while (true) {
                capienza = leggiIntero(scanner, "Inserisci capienza: ");

                if (tipo == TipoMezzo.TRAM && (capienza < 50 || capienza > 300)) {
                    System.out.println("Capienza non valida per tram (50-300)");
                } else if (tipo == TipoMezzo.BUS && (capienza < 20 || capienza > 120)) {
                    System.out.println("Capienza non valida per bus (20-120)");
                } else {
                    break;
                }
            }

            Mezzo mezzoNuovo = new Mezzo(codice, tipo, StatoMezzo.IN_SERVIZIO, capienza);
            mezzoDAO.save(mezzoNuovo);

            System.out.println("Mezzo salvato:");
            System.out.println(mezzoNuovo);
        } catch (Exception e) {
            System.out.println("Errore salvataggio mezzo: " + e.getMessage());
        }
    }

    private static void salvaTrattaDaScanner(Scanner scanner, TrattaDAO trattaDAO) {
        try {
            System.out.print("Inserisci zona di partenza: ");
            String partenza = scanner.nextLine().trim();

            System.out.print("Inserisci zona di arrivo: ");
            String arrivo = scanner.nextLine().trim();

            int minuti = leggiIntero(scanner, "Inserisci durata in minuti: ");

            Tratta nuovaTratta = new Tratta(partenza, arrivo, minuti);
            trattaDAO.salvaTratta(nuovaTratta);

            System.out.println("Tratta salvata:");
            System.out.println(nuovaTratta);
        } catch (Exception e) {
            System.out.println("Errore inserimento tratta: " + e.getMessage());
        }
    }

    private static void salvaPercorrenzaDaScanner(
            Scanner scanner,
            MezzoDAO mezzoDAO,
            TrattaDAO trattaDAO,
            PercorrenzaDAO percorrenzaDAO
    ) {
        try {
            System.out.print("Inserisci id del mezzo: ");
            String idMezzo = scanner.nextLine().trim();

            System.out.print("Inserisci id della tratta: ");
            String idTratta = scanner.nextLine().trim();

            Mezzo mezzo = mezzoDAO.findById(idMezzo);
            Tratta tratta = trattaDAO.trovaPerID(idTratta);

            LocalDateTime partenza = leggiDataOra(scanner, "Partenza (yyyy-MM-dd HH:mm): ");
            LocalDateTime arrivo = leggiDataOra(scanner, "Arrivo (yyyy-MM-dd HH:mm): ");

            Percorrenza percorrenzaNuova = new Percorrenza(mezzo, tratta, partenza, arrivo);
            percorrenzaDAO.salvapERCORRENZA(percorrenzaNuova);

            System.out.println("Percorrenza salvata:");
            System.out.println(percorrenzaNuova);
        } catch (Exception e) {
            System.out.println("Errore inserimento percorrenza: " + e.getMessage());
        }
    }

    private static void salvaRivenditoreDaScanner(Scanner scanner, RivenditoreDAO rivenditoreDAO) {
        try {
            System.out.println("Inserisci 1 per Rivenditore Autorizzato o 2 per Distributore Automatico:");
            int sceltaRiv = leggiIntero(scanner, "Scelta: ");

            System.out.print("Inserisci nome attività: ");
            String nome = scanner.nextLine().trim();
                                            System.out.println("Inserisci nome attività:");
                                            nome = scanner.nextLine();

            if (sceltaRiv == 1) {
                RivenditoreAutorizzato riv = new RivenditoreAutorizzato(nome);
                rivenditoreDAO.save(riv);

                System.out.println("Rivenditore autorizzato salvato:");
                System.out.println(riv);
            } else if (sceltaRiv == 2) {
                System.out.println("Inserisci 1 se attivo, 2 se fuori servizio:");
                int stato = leggiIntero(scanner, "Scelta: ");

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
    }

    private static void menuGestioneMezzi(
            Scanner scanner,
            MezzoDAO mezzoDAO,
            TrattaDAO trattaDAO,
            PercorrenzaDAO percorrenzaDAO
    ) {
        int sceltaMezzo;

        do {
            System.out.println("\n--- GESTIONE MEZZI ---");
            System.out.println("1. Salva mezzo");
            System.out.println("2. Cerca mezzo per codice");
            System.out.println("3. Cambia stato mezzo");
            System.out.println("4. Cerca mezzi per tipo");
            System.out.println("5. Cerca mezzi per stato");
            System.out.println("6. Assegna mezzo a una tratta");
            System.out.println("0. Torna indietro");

            sceltaMezzo = leggiIntero(scanner, "Scelta: ");

            try {
                switch (sceltaMezzo) {
                    case 1:
                        salvaMezzoDaScanner(scanner, mezzoDAO);
                        break;

                    case 2:
                        System.out.print("Inserisci codice mezzo: ");
                        String codiceRicerca = scanner.nextLine().trim().toUpperCase();

                        Mezzo mezzoTrovato = mezzoDAO.findByCodice(codiceRicerca);

                        if (mezzoTrovato == null) {
                            System.out.println("Mezzo non trovato.");
                        } else {
                            System.out.println("Mezzo trovato:");
                            System.out.println(mezzoTrovato);
                        }
                        break;

                    case 3:
                        System.out.print("Inserisci codice mezzo: ");
                        String codiceCambio = scanner.nextLine().trim().toUpperCase();

                        Mezzo mezzoDaCambiare = mezzoDAO.findByCodice(codiceCambio);

                        if (mezzoDaCambiare == null) {
                            System.out.println("Mezzo non trovato.");
                            break;
                        }

                        System.out.println("Nuovo stato: 1 = IN_SERVIZIO, 2 = IN_MANUTENZIONE");
                        int sceltaStato = leggiIntero(scanner, "Scelta: ");

                        StatoMezzo nuovoStato;
                        if (sceltaStato == 1) {
                            nuovoStato = StatoMezzo.IN_SERVIZIO;
                        } else if (sceltaStato == 2) {
                            nuovoStato = StatoMezzo.IN_MANUTENZIONE;
                        } else {
                            System.out.println("Scelta non valida.");
                            break;
                        }

                        LocalDate dataCambio = leggiData(scanner, "Data cambio stato (yyyy-MM-dd): ");

                        mezzoDAO.cambiaStato(
                                mezzoDaCambiare.getId().toString(),
                                nuovoStato,
                                dataCambio
                        );

                        System.out.println("Stato aggiornato correttamente.");
                        System.out.println(mezzoDAO.findById(mezzoDaCambiare.getId().toString()));
                        break;

                    case 4:
                        System.out.println("Tipo mezzo: 1 = BUS, 2 = TRAM");
                        int tipoRicerca = leggiIntero(scanner, "Scelta: ");

                        if (tipoRicerca == 1) {
                            stampaListaMezzi(mezzoDAO.findByTipo(TipoMezzo.BUS));
                        } else if (tipoRicerca == 2) {
                            stampaListaMezzi(mezzoDAO.findByTipo(TipoMezzo.TRAM));
                        } else {
                            System.out.println("Scelta non valida.");
                        }
                        break;

                    case 5:
                        System.out.println("Stato mezzo: 1 = IN_SERVIZIO, 2 = IN_MANUTENZIONE");
                        int statoRicerca = leggiIntero(scanner, "Scelta: ");

                        if (statoRicerca == 1) {
                            stampaListaMezzi(mezzoDAO.findByStato(StatoMezzo.IN_SERVIZIO));
                        } else if (statoRicerca == 2) {
                            stampaListaMezzi(mezzoDAO.findByStato(StatoMezzo.IN_MANUTENZIONE));
                        } else {
                            System.out.println("Scelta non valida.");
                        }
                        break;

                    case 6:
                        System.out.print("Inserisci codice mezzo: ");
                        String codicePercorrenza = scanner.nextLine().trim().toUpperCase();

                        Mezzo mezzoPercorrenza = mezzoDAO.findByCodice(codicePercorrenza);
                        if (mezzoPercorrenza == null) {
                            System.out.println("Mezzo non trovato.");
                            break;
                        }

                        List<Tratta> tratteDisponibili = trattaDAO.TrovaTutteLeTratte();
                        if (tratteDisponibili.isEmpty()) {
                            System.out.println("Nessuna tratta presente. Creane prima una.");
                            break;
                        }

                        System.out.println("Tratte disponibili:");
                        for (Tratta tratta : tratteDisponibili) {
                            System.out.println(tratta.getId() + " - " + tratta.getZonaPartenza() + " -> " + tratta.getZonaFinale()
                                    + " - " + tratta.getTempoPercorrenzaFormattato());
                        }

                        System.out.print("Inserisci id tratta: ");
                        String idTratta = scanner.nextLine().trim();

                        Tratta tratta = trattaDAO.trovaPerID(idTratta);
                        LocalDateTime partenza = leggiDataOra(scanner, "Partenza (yyyy-MM-dd HH:mm): ");
                        int minutiEffettivi = leggiIntero(scanner, "Durata effettiva in minuti: ");

                        Percorrenza nuovaPercorrenza = new Percorrenza(
                                mezzoPercorrenza,
                                tratta,
                                partenza,
                                partenza.plusMinutes(minutiEffettivi)
                        );

                        percorrenzaDAO.salvapERCORRENZA(nuovaPercorrenza);
                        System.out.println("Percorrenza salvata:");
                        System.out.println(nuovaPercorrenza);
                        break;

                    case 0:
                        System.out.println("Ritorno al menu precedente.");
                        break;

                    default:
                        System.out.println("Scelta non valida.");
                }
            } catch (Exception e) {
                System.out.println("Errore gestione mezzi: " + e.getMessage());
            }

        } while (sceltaMezzo != 0);
    }

    private static void menuStoricoMezzi(
            Scanner scanner,
            MezzoDAO mezzoDAO,
            PeriodoStatoMezzoDAO periodoDAO
    ) {
        int sceltaStorico;

        do {
            System.out.println("\n--- STORICO MEZZI ---");
            System.out.println("1. Mostra storico completo mezzo");
            System.out.println("2. Mostra manutenzioni mezzo");
            System.out.println("3. Mostra periodo attivo attuale");
            System.out.println("0. Torna indietro");

            sceltaStorico = leggiIntero(scanner, "Scelta: ");

            try {
                switch (sceltaStorico) {
                    case 1:
                        System.out.print("Inserisci codice mezzo: ");
                        String codiceStorico = scanner.nextLine().trim().toUpperCase();

                        Mezzo mezzoStorico = mezzoDAO.findByCodice(codiceStorico);
                        if (mezzoStorico == null) {
                            System.out.println("Mezzo non trovato.");
                            break;
                        }

                        List<PeriodoStatoMezzo> storico = periodoDAO.trovaStoricoPerMezzo(mezzoStorico.getId().toString());
                        if (storico.isEmpty()) {
                            System.out.println("Nessuno storico trovato.");
                        } else {
                            storico.forEach(System.out::println);
                        }
                        break;

                    case 2:
                        System.out.print("Inserisci codice mezzo: ");
                        String codiceManut = scanner.nextLine().trim().toUpperCase();

                        Mezzo mezzoManut = mezzoDAO.findByCodice(codiceManut);
                        if (mezzoManut == null) {
                            System.out.println("Mezzo non trovato.");
                            break;
                        }

                        List<PeriodoStatoMezzo> manutenzioni = periodoDAO.trovaManutenzioniPerMezzo(mezzoManut.getId().toString());
                        if (manutenzioni.isEmpty()) {
                            System.out.println("Nessun periodo di manutenzione trovato.");
                        } else {
                            manutenzioni.forEach(System.out::println);
                        }
                        break;

                    case 3:
                        System.out.print("Inserisci codice mezzo: ");
                        String codiceAttivo = scanner.nextLine().trim().toUpperCase();

                        Mezzo mezzoAttivo = mezzoDAO.findByCodice(codiceAttivo);
                        if (mezzoAttivo == null) {
                            System.out.println("Mezzo non trovato.");
                            break;
                        }

                        PeriodoStatoMezzo periodoAttivo = periodoDAO.trovaPeriodoAttivoPerMezzo(mezzoAttivo.getId().toString());
                        if (periodoAttivo == null) {
                            System.out.println("Nessun periodo attivo trovato.");
                        } else {
                            System.out.println(periodoAttivo);
                        }
                        break;

                    case 0:
                        System.out.println("Ritorno al menu precedente.");
                        break;

                    default:
                        System.out.println("Scelta non valida.");
                }
            } catch (Exception e) {
                System.out.println("Errore storico mezzi: " + e.getMessage());
            }

        } while (sceltaStorico != 0);
    }
}
//    static void compraBiglietto() {
//        Scanner s = new Scanner(System.in);
//
//        for (int i = 0; i < rivenditori.size(); i++) {
//            System.out.println((i + 1) + " - " + rivenditori.get(i).getNomeAttivita());
//        }
//        int riv = s.nextInt();
//        Rivenditore r = switch (riv) {
/// /            case 1 -> rivenditori.get(riv - 1);
/// /            case 2 -> r2;
/// /            case 3 -> r3;
/// /            case 4 -> r4;
/// /            case 5 -> r1; //trovare il modod di tornare indietro
/// /            default -> null;
//        };
//        if (r == null) System.out.println("Valore inserito non riconosciuto, si prega di riprovare");
//    }
//
//
