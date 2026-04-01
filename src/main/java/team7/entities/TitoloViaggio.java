package team7.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class TitoloViaggio {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true)
    private String codiceUnivoco;

    private LocalDate dataEmissione;

    @ManyToOne
    @JoinColumn(name = "rivenditore_id")
    private Rivenditore rivenditore;


    protected TitoloViaggio() {
    }

    public void setRivenditore(Rivenditore r) {
        this.rivenditore = r;
        this.dataEmissione = LocalDate.now();
        this.codiceUnivoco = r.generaCodiceUnivoco();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCodiceUnivoco() {
        return codiceUnivoco;
    }

    public void setCodiceUnivoco(String codiceUnivoco) {
        this.codiceUnivoco = codiceUnivoco;
    }

    public LocalDate getDataEmissione() {
        return dataEmissione;
    }

    public void setDataEmissione(LocalDate dataEmissione) {
        this.dataEmissione = dataEmissione;
    }

    public Rivenditore getRivenditore() {
        return rivenditore;
    }
}