package team7.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import team7.enumm.TipoAbbonamento;

import java.time.LocalDate;

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

    public Abbonamento(String codiceUnivoco, LocalDate dataEmissione, Rivenditore rivenditore, TipoAbbonamento tipo, LocalDate dataInizio, LocalDate dataFine, Tessera tessera) {
        super(codiceUnivoco, dataEmissione, rivenditore);
        this.tipo = tipo;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
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
                "tipo=" + tipo +
                ", dataInizio=" + dataInizio +
                ", dataFine=" + dataFine +
                ", tessera=" + tessera +
                '}';
    }
}