package team7.entities;

import jakarta.persistence.*;
import team7.enumm.TipoAbbonamento;

import java.time.LocalDate;

@NamedQuery(
        name = "abbonamento_attivo_utente",
        query = "select a from Abbonamento a where a.tessera.utente.id = :utenteId and a.dataFine >= :oggi"
)
@Entity
public class Abbonamento extends TitoloViaggio {

    @Enumerated(EnumType.STRING)
    private TipoAbbonamento tipo;

    private LocalDate dataInizio;
    private LocalDate dataFine;

    @ManyToOne
    private Tessera tessera;


    //    costruttori
    protected Abbonamento() {
    }

    public Abbonamento(Rivenditore rivenditore, TipoAbbonamento tipo, Tessera tessera) {
        super(rivenditore);
        this.tipo = tipo;
        this.dataInizio = LocalDate.now();
        switch (tipo) {
            case SETTIMANALE:
                this.dataFine = dataInizio.plusWeeks(1);
                break;
            case MENSILE:
                this.dataFine = dataInizio.plusMonths(1);
                break;
            case ANNUALE:
                this.dataFine = dataInizio.plusYears(1);
                break;
        }
        this.tessera = tessera;
    }


    //    getters/setters
    public TipoAbbonamento getTipo() {
        return tipo;
    }

    public void setTipo(TipoAbbonamento tipo) {
        this.tipo = tipo;
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

    public Tessera getTessera() {
        return tessera;
    }

    public void setTessera(Tessera tessera) {
        this.tessera = tessera;
    }

    @Override
    public String toString() {
        return "Abbonamento{" +
                super.toString() +
                "tipo=" + tipo +
                ", dataInizio=" + dataInizio +
                ", dataFine=" + dataFine +
                ", tessera=" + tessera +
                '}';
    }
}