package team7.entities;
import jakarta.persistence.*;

import java.time.*;
import java.util.UUID;

@Entity
@Table(name = "tratta")
public class Tratta {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(name="zona_partenza")
    private String zonaPartenza;
    @Column(name="zona_arrivo")
    private String zonaFinale;
    @Column(name="durata")
    private Duration durata;

    public Tratta(){}

    public Tratta( String zonaPartenza, String zonaFinale, int minuti) {
        this.zonaPartenza = zonaPartenza;
        this.zonaFinale = zonaFinale;
        this.durata = Duration.ofMinutes(minuti);

    }
public String getTempoPercorrenzaFormattato(){
    long ore = durata.toHours();
    int minuti = durata.toMinutesPart();
    return ore + "h " + minuti + "m";
}
//getter
    public UUID getId() {
        return id;
    }

    public String getZonaPartenza() {
        return zonaPartenza;
    }

    public Duration getDurata() {
        return durata;
    }

    public String getZonaFinale() {
        return zonaFinale;
    }
    //setter

    public void setZonaPartenza(String zonaPartenza) {
        this.zonaPartenza = zonaPartenza;
    }

    public void setZonaFinale(String zonaFinale) {
        this.zonaFinale = zonaFinale;
    }

    public void setDurata(Duration durata) {
        this.durata = durata;
    }
}
