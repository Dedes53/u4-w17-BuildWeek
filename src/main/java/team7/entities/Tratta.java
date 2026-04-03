package team7.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tratta")
public class Tratta {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "zona_partenza")
    private String zonaPartenza;

    @Column(name = "zona_arrivo")
    private String zonaFinale;

    @Column(name = "durata")
    private int durata;

    public Tratta() {}

    public Tratta(String zonaPartenza, String zonaFinale, int minuti) {
        this.zonaPartenza = zonaPartenza;
        this.zonaFinale = zonaFinale;
        this.durata = minuti;
    }

    public String getTempoPercorrenzaFormattato() {
        int ore = durata / 60;
        int minuti = durata % 60;
        return ore + "h " + minuti + "m";
    }

    // getter
    public UUID getId() {
        return id;
    }

    public String getZonaPartenza() {
        return zonaPartenza;
    }

    public String getZonaFinale() {
        return zonaFinale;
    }

    public int getDurata() {
        return durata;
    }

    // setter
    public void setZonaPartenza(String zonaPartenza) {
        this.zonaPartenza = zonaPartenza;
    }

    public void setZonaFinale(String zonaFinale) {
        this.zonaFinale = zonaFinale;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    @Override
    public String toString() {
        return "Tratta{" +
                "id=" + id +
                ", zonaPartenza='" + zonaPartenza + '\'' +
                ", zonaFinale='" + zonaFinale + '\'' +
                ", durata=" + durata + " min" +
                '}';
    }
}
