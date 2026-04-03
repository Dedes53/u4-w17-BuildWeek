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
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import static java.lang.Integer.parseInt;

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("team7");
    private static final List<Rivenditore> rivenditori = new ArrayList<>();
    // EntityManagerFactory emf = Persistence.createEntityManagerFactory("team7");
    static EntityManager em = emf.createEntityManager();
    // DAO rivenditori e utenti
    static UtenteDAO utenteDAO = new UtenteDAO(em);
    static RivenditoreDAO rivenditoreDAO = new RivenditoreDAO(em);
    static Utente u3 = new Utente("Massimo", "Bianchi"); // per test metodo crea abbonamento

    public static void main(String[] args) {

        //DAO Titoli di viaggio

        BigliettoDAO bigliettoDAO = new BigliettoDAO(em);
        AbbonamentoDAO abbonamentoDAO = new AbbonamentoDAO(em);

        // DAO mezzi e storico

        //per mezzo
        MezzoDAO mezzoDAO = new MezzoDAO(em);
        PeriodoStatoMezzoDAO periodoDAO = new PeriodoStatoMezzoDAO(em);

        // DAO tratte e percorrenze
        TrattaDAO trattaDAO = new TrattaDAO(em);
        PercorrenzaDAO percorrenzaDAO = new PercorrenzaDAO(em);


        //da qua salviamo utenti, mezzi, tratte, percorrenze


        Scanner scanner = new Scanner(System.in);
        int scelta;

        // Dati di test già presenti nel progetto
        Utente u1 = new Utente("Mario", "Rossi");
        Utente u2 = new Utente("Maurizio", "Verdi");


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
//        Abbonamento a2 = rivenditoreDAO.emettiAbbonamento(r1, TipoAbbonamento.MENSILE, t1);
        Abbonamento a3 = rivenditoreDAO.emettiAbbonamento(r1, TipoAbbonamento.SETTIMANALE, t2);
//        Abbonamento a4 = rivenditoreDAO.emettiAbbonamento(r2, TipoAbbonamento.ANNUALE, t1);
//        Abbonamento a5 = rivenditoreDAO.emettiAbbonamento(r2, TipoAbbonamento.MENSILE, t1);
//        Abbonamento a6 = rivenditoreDAO.emettiAbbonamento(r2, TipoAbbonamento.SETTIMANALE, t2);

        System.out.println("Biglietti dal primo rivenditore");
        System.out.println(b1);
        System.out.println(b2);

        System.out.println("Biglietti dal secondo rivenditore");
        System.out.println(b3);
        System.out.println(b4);

        System.out.println("Abbonamenti dal primo rivenditore");
        System.out.println(a1);
//        System.out.println(a2);
        System.out.println(a3);

//        System.out.println("Abbonamenti dal secondo rivenditore");
//        System.out.println(a4);
//        System.out.println(a5);
//        System.out.println(a6);

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
        mezzoDAO.save(mezzo2);
        Mezzo mezzo3 = new Mezzo("TRAM-MAD-03", TipoMezzo.TRAM, StatoMezzo.IN_SERVIZIO, 120);
        mezzoDAO.save(mezzo3);
        Mezzo mezzo4 = new Mezzo("TRAM-MAD-04", TipoMezzo.TRAM, StatoMezzo.IN_SERVIZIO, 200);
        mezzoDAO.save(mezzo4);
        Mezzo mezzo5 = new Mezzo("BUS-CAN-05", TipoMezzo.BUS, StatoMezzo.IN_MANUTENZIONE, 40);
        mezzoDAO.save(mezzo5);

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

                scelta = parseInt(scanner.nextLine());

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
                        compraBiglietto();
                        break;


                    case 3:
                        // Compra Abbonamento
                        compraAbbonamento();
                        break;

                    case 4:
                        // verifico validita abbonamento
                        verificoValidita(em, utenteDAO);
                        break;

                    case 5:
                        // TEST RIVENDITOREDAO
                        System.out.println("Funzionalità non ancora implementata.");
                        break;

                    case 6:
                        menuGestioneMezzi(scanner, mezzoDAO, trattaDAO, percorrenzaDAO);
                        break;

                    case 7:
                        menuStoricoMezzi(scanner, mezzoDAO, periodoDAO);
                        break;

                    case 8:
                        System.out.println("Menu delle Tratte ");
                        System.out.println("1 Trova tratta per id");
                        System.out.println("2 Mostra tutte le tratte");
                        System.out.println("3 Cancella tratta per id ");
                        System.out.println("4 Conta quante volte quella tratta e' stata percorsa");

                        int sceltaTratta = parseInt(scanner.nextLine());
                        switch (sceltaTratta) {
                            case 1:
                                try {
                                    System.out.println("Inserisci id della tratta:");
                                    String idtratta = scanner.nextLine();
                                    Tratta trovato = trattaDAO.trovaPerID(idtratta);
                                    System.out.println(trovato);
                                } catch (NonTrovato e) {
                                    System.out.println("Errore: tratta non trovata!" + e.getMessage());
                                }
                                break;
                            case 2:
                                List<Tratta> listatratte = trattaDAO.TrovaTutteLeTratte();
                                System.out.println("--- ELENCO TRATTE ---");
                                listatratte.forEach(System.out::println);
                                break;
                            case 3:
                                try {
                                    System.out.println("Inserisci id della tratta per cancellarla:");
                                    String idtrattaD = scanner.nextLine();
                                    Tratta trovato = trattaDAO.cancellaTratta(idtrattaD);
                                    System.out.println(trovato);
                                } catch (NonTrovato e) {
                                    System.out.println("Errore: tratta non trovata!" + e.getMessage());
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
                                    System.out.println("Errore: tratta non trovata!" + e.getMessage());
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
                                                System.out.println("Totale biglietti emessi nel periodo " + inizio + "-" + fine + ":" + totaleBiglietti);

                                            } else if (tipo.equals("abbonamenti")) {

                                                Long totaleAbbonamenti = abbonamentoDAO.countAbbonamentiPeriodo(inizio, fine);
                                                System.out.println("Totale abbonamenti emessi nel periodo " + inizio + "-" + fine + ":" +
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

    static void compraBiglietto() {
        Scanner s = new Scanner(System.in);
        //potrei sostituirlo con un metodo selRivenditore()
        System.out.println("Seleziona uno dei seguenti rivenditori:");
        for (int i = 0; i < rivenditori.size(); i++) {
            System.out.println((i + 1) + " - " + rivenditori.get(i).getNomeAttivita() + "\n");
        }
        int riv = s.nextInt();
        if (riv < 1 || riv > rivenditori.size()) {
            System.out.println("Valore inserito non riconosciuto, si prega di riprovare");
            compraBiglietto();
        }
        Rivenditore r = rivenditori.get(riv - 1);
        Biglietto b = rivenditoreDAO.emettiBiglietto(r);
        System.out.println("Biglietto acquistato con successo");
    }

    static void compraAbbonamento() {
        Scanner s = new Scanner(System.in);
        System.out.println("Seleziona uno dei seguenti rivenditori:");
        //scelta del rivenditore
        for (int i = 0; i < rivenditori.size(); i++) {
            System.out.println((i + 1) + " - " + rivenditori.get(i).getNomeAttivita() + "\n");
        }
        int riv = s.nextInt();
        if (riv < 1 || riv > rivenditori.size()) {
            System.out.println("Valore inserito non riconosciuto, si prega di riprovare");
            compraAbbonamento();
        }
        Rivenditore r = rivenditori.get(riv - 1);
        TipoAbbonamento durata = sceltaDurata();
        //tessera utente
        Tessera t = new Tessera(u3);
        Abbonamento a = rivenditoreDAO.emettiAbbonamento(r, durata, t);
        System.out.println("Ecco i dati del tuo abbonamento: " + a);
    }

    static TipoAbbonamento sceltaDurata() {
        Scanner s = new Scanner(System.in);
        // scelta del tipo di abbonamento
        System.out.println("Ora devi selezionare la durata desiderata del tuo abbonamento:\n" +
                "1 - SETTIMANALE\n" +
                "2 - MENSILE\n" +
                "3 - ANNUALE");
        int res = s.nextInt();
        TipoAbbonamento durata = switch (res) {
            case 1 -> TipoAbbonamento.SETTIMANALE;
            case 2 -> TipoAbbonamento.MENSILE;
            case 3 -> TipoAbbonamento.ANNUALE;
            default -> null;
        };
        if (durata == null) {
            System.out.println("Scelta non valida, seleziona una delle scelte da 1 a 3");
            compraAbbonamento();
        }
        return durata;
    }

    static void verificoValidita(EntityManager em, UtenteDAO utenteDAO) {
        Scanner s = new Scanner(System.in);
        System.out.println("Per verificare se il tuo abbonamento è ancora valido, devi accedere al tuo profilo utente.\n" +
                "Inserisci di seguito il nome del profilo utente");

        String nomeUtente = s.nextLine().trim();

        Utente u = em.createQuery("select u from Utente u where concat(u.nome, ' ', u.cognome) like :nomeInserito", Utente.class)
                .setParameter("nomeInserito", "%" + nomeUtente + "%").getSingleResult();

        Abbonamento a = utenteDAO.controllaAbbonamento(u);

        if (a.getDataFine().isAfter(LocalDate.now()))
            System.out.println("Il tuo abbonamento è ancora valido, scadrà in data " + a.getDataFine());
        else
            System.out.println("Ci dispiace, ma il tuo abbonamento è scaduto in data " + a.getDataFine());
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
