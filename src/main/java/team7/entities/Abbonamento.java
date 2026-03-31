package team7.entities;
import jakarta.persistence.*;
import team7.enumm.TipoAbbonamento;

import java.time.LocalDate;

@Entity
public class Abbonamento extends TitoloViaggio {

    @Enumerated(EnumType.STRING)
    private TipoAbbonamento tipo; //TODO ENUM PER TIPO, devo creare l'enum per definire se è un settimanale, mensile ecc ecc...

    private LocalDate dataInizio;
    private LocalDate dataFine;

    @ManyToOne
    private Tessera tessera;
}