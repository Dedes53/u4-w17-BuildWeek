package team7.entities;

import jakarta.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "percorrenza")
public class Percorrenza {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_mezzo")
    private Mezzo mezzo;

    @ManyToOne
    @JoinColumn(name = "id_tratta")
    private Tratta tratta;

    @Column(name = "data_ora_partenza")
    private LocalDateTime dataOraPartenza;

    @Column(name = "data_ora_arrivo")
    private LocalDateTime dataOraArrivo;

    @Column(name = "tempo_effettivo")
    private int tempoEffettivoPercorrenza;


    public Percorrenza() {}
    public Percorrenza(Mezzo mezzo, Tratta tratta, LocalDateTime partenza, LocalDateTime arrivo) {
        this.mezzo = mezzo;
        this.tratta = tratta;
        this.dataOraPartenza = partenza;
        this.dataOraArrivo = arrivo;
        this.tempoEffettivoPercorrenza =(int) Duration.between(partenza, arrivo).toMinutes();
    }

    // per ritardi o antticipi
    public String getScostamentoRispettoAlPrevisto() {
        int prevista = tratta.getDurata();
        int differenza = tempoEffettivoPercorrenza - prevista;

        if (differenza > 0) return "Ritardo di " + differenza + " min";
        if (differenza < 0) return "Anticipo di " + Math.abs(differenza) + " min";
        return "In orario";
    }
//getter

    public UUID getId() {
        return id;
    }

    public Mezzo getMezzo() {
        return mezzo;
    }

    public Tratta getTratta() {
        return tratta;
    }

    public LocalDateTime getDataOraPartenza() {
        return dataOraPartenza;
    }

    public LocalDateTime getDataOraArrivo() {
        return dataOraArrivo;
    }

    public int getTempoEffettivoPercorrenza() {
        return tempoEffettivoPercorrenza;
    }

    //setter
    public void setMezzo(Mezzo mezzo) {
        this.mezzo = mezzo;
    }

    public void setTratta(Tratta tratta) {
        this.tratta = tratta;
    }

    public void setDataOraPartenza(LocalDateTime dataOraPartenza) {
        this.dataOraPartenza = dataOraPartenza;
    }

    public void setDataOraArrivo(LocalDateTime dataOraArrivo) {
        this.dataOraArrivo = dataOraArrivo;
    }

    public void setTempoEffettivoPercorrenza(int tempoEffettivoPercorrenza) {
        this.tempoEffettivoPercorrenza = tempoEffettivoPercorrenza;
    }

    @Override
    public String toString() {
        return "Percorrenza{" +
                "id=" + id +
                ", mezzo=" + mezzo +
                ", tratta=" + tratta +
                ", dataOraPartenza=" + dataOraPartenza +
                ", dataOraArrivo=" + dataOraArrivo +
                ", tempoEffettivoPercorrenza=" + tempoEffettivoPercorrenza +
                '}';
    }
}
