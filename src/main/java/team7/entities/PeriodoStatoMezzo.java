package team7.entities;

import jakarta.persistence.*;
import team7.enumm.StatoMezzo;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "periodi_stato_mezzo")
public class PeriodoStatoMezzo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    // l'id viene generato automaticamente usando un UUID
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "mezzo_id", nullable = false)
    private Mezzo mezzo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatoMezzo stato;

    @Column(nullable = false)
    private LocalDate dataInizio;

    private LocalDate dataFine;

    public PeriodoStatoMezzo() {
    }

    public PeriodoStatoMezzo(Mezzo mezzo, StatoMezzo stato, LocalDate dataInizio, LocalDate dataFine) {
        // Costruttore per creare un oggetto PeriodoStatoMezzo già con i valori iniziali
        this.mezzo = mezzo;
        this.stato = stato;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
    }

    public UUID getId() {
        // restituisce l'id del periodo di stato.
        return id;
    }

    public Mezzo getMezzo() {
        // restituisce il mezzo associato a questo periodo
        return mezzo;
    }

    public void setMezzo(Mezzo mezzo) {
        // permette di modificare il mezzo associato
        this.mezzo = mezzo;
    }

    public StatoMezzo getStato() {
        // restituisce lo stato del mezzo in questo periodo
        return stato;
    }

    public void setStato(StatoMezzo stato) {
        // permette di modificare lo stato del mezzo
        this.stato = stato;
    }

    public LocalDate getDataInizio() {
        // restituisce la data di inizio del periodo
        return dataInizio;
    }

    public void setDataInizio(LocalDate dataInizio) {
        // permette di modificare la data di inizio
        this.dataInizio = dataInizio;
    }

    public LocalDate getDataFine() {
        // restituisce la data di fine del periodo
        return dataFine;
    }

    public void setDataFine(LocalDate dataFine) {
        // permette di modificare la data di fine
        this.dataFine = dataFine;
    }

    @Override
    public String toString() {
        return "PeriodoStatoMezzo{" +
                "id=" + id +
                ", mezzo=" + mezzo.getCodiceMezzo() +
                ", stato=" + stato +
                ", dataInizio=" + dataInizio +
                ", dataFine=" + dataFine +
                '}';
    }
}