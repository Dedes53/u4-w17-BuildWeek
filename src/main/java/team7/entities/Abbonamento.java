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
}