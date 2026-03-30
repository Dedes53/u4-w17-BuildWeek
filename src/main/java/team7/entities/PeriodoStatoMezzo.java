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
        this.mezzo = mezzo;
        this.stato = stato;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
    }

    public UUID getId() {
        return id;
    }

    public Mezzo getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzo mezzo) {
        this.mezzo = mezzo;
    }

    public StatoMezzo getStato() {
        return stato;
    }

    public void setStato(StatoMezzo stato) {
        this.stato = stato;
    }

    public LocalDate getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(LocalDate dataInizio) {
        this.dataInizio = dataInizio;
    }

    public LocalDate getDataFine() {
        return dataFine;
    }

    public void setDataFine(LocalDate dataFine) {
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