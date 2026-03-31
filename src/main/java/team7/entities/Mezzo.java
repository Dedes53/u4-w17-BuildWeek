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
    // il valore dell'id viene generato automaticamente usando un UUID
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
        // Costruttore  per creare un oggetto mezzo già con i valori iniziali
        this.codiceMezzo = codiceMezzo;
        this.tipoMezzo = tipoMezzo;
        this.statoAttuale = statoAttuale;
        this.capienza = capienza;
    }

    public UUID getId() {
        // restituisce l'id del mezzo
        return id;
    }

    public String getCodiceMezzo() {
        // restituisce il codice identificativo del mezzo
        return codiceMezzo;
    }

    public void setCodiceMezzo(String codiceMezzo) {
        // Permette di modificare il codice del mezzo.
        this.codiceMezzo = codiceMezzo;
    }

    public TipoMezzo getTipoMezzo() {
        // restituisce il tipo del mezzo
        return tipoMezzo;
    }

    public void setTipoMezzo(TipoMezzo tipoMezzo) {
        // permette di modificare il tipo del mezzo
        this.tipoMezzo = tipoMezzo;
    }

    public StatoMezzo getStatoAttuale() {
        // restituisce lo stato attuale del mezzo
        return statoAttuale;
    }

    public void setStatoAttuale(StatoMezzo statoAttuale) {
        // permette di modificare lo stato attuale del mezzo
        this.statoAttuale = statoAttuale;
    }

    public int getCapienza() {
        // restituisce il numero massimo di persone che il mezzo può contenere
        return capienza;
    }

    public void setCapienza(int capienza) {
        // permette di modificare la capienza del mezzo
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