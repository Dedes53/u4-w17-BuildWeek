package team7.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Tessera {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "data_scadenza")
    private LocalDate dataDiScadenza;

    @OneToOne
    private Utente utente;

    //   costruttori
    protected Tessera() {
    }

    public Tessera(Utente utente) {
        this.utente = utente;
        this.dataDiScadenza = LocalDate.now().plusYears(1);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getDataDiScadenza() {
        return dataDiScadenza;
    }

    public void setDataDiScadenza(LocalDate dataDiScadenza) {
        this.dataDiScadenza = dataDiScadenza;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }
}