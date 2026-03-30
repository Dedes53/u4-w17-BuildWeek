package team7.entities;

import jakarta.persistence.*;
import team7.enumm.StatoMezzo;
import team7.enumm.TipoMezzo;

import java.util.UUID;

@Entity
@Table(name = "mezzi")
public class Mezzo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String codiceMezzo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMezzo tipoMezzo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatoMezzo statoAttuale;

    @Column(nullable = false)
    private int capienza;

    public Mezzo() {
    }

    public Mezzo(String codiceMezzo, TipoMezzo tipoMezzo, StatoMezzo statoAttuale, int capienza) {
        this.codiceMezzo = codiceMezzo;
        this.tipoMezzo = tipoMezzo;
        this.statoAttuale = statoAttuale;
        this.capienza = capienza;
    }

    public UUID getId() {
        return id;
    }

    public String getCodiceMezzo() {
        return codiceMezzo;
    }

    public void setCodiceMezzo(String codiceMezzo) {
        this.codiceMezzo = codiceMezzo;
    }

    public TipoMezzo getTipoMezzo() {
        return tipoMezzo;
    }

    public void setTipoMezzo(TipoMezzo tipoMezzo) {
        this.tipoMezzo = tipoMezzo;
    }

    public StatoMezzo getStatoAttuale() {
        return statoAttuale;
    }

    public void setStatoAttuale(StatoMezzo statoAttuale) {
        this.statoAttuale = statoAttuale;
    }

    public int getCapienza() {
        return capienza;
    }

    public void setCapienza(int capienza) {
        this.capienza = capienza;
    }

    @Override
    public String toString() {
        return "Mezzo{" +
                "id=" + id +
                ", codiceMezzo='" + codiceMezzo + '\'' +
                ", tipoMezzo=" + tipoMezzo +
                ", statoAttuale=" + statoAttuale +
                ", capienza=" + capienza +
                '}';
    }
}