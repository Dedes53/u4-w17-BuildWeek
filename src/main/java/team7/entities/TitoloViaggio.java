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


    //    costruttori
    protected TitoloViaggio() {
    }

    public TitoloViaggio(Rivenditore r) {
        //   richiama setta i parametri della vendita: data, rivenditore e richiama un metodo che genera il codiceUnivoco basato sull'uuid del rivenditore selezionato
        this.setRivenditore(r);
    }


    //    getters/setters
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

    public void setRivenditore(Rivenditore r) {
        this.rivenditore = r;
        this.dataEmissione = LocalDate.now();
        this.codiceUnivoco = r.generaCodiceUnivoco();
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", codiceUnivoco='" + codiceUnivoco + '\'' +
                ", dataEmissione=" + dataEmissione +
                ", rivenditore=" + rivenditore +
                '}';
    }


}