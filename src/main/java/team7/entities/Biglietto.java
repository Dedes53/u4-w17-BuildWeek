package team7.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Biglietto extends TitoloViaggio {

    private boolean vidimato = false;

    @ManyToOne
    private Mezzo mezzoVidimazione;

    private LocalDateTime dataDiVidimazione;


    //    costruttori
    protected Biglietto() {
    }

    public Biglietto(Rivenditore rivenditore) {
        super(rivenditore);
    }


    //    getters/setters
    public boolean isVidimato() {
        return vidimato;
    }

    public void setVidimato(boolean vidimato) {
        this.vidimato = vidimato;
    }

    public LocalDateTime getDataDiVidimazione() {
        return dataDiVidimazione;
    }

    public void setDataDiVidimazione(LocalDateTime dataDiVidimazione) {
        this.dataDiVidimazione = dataDiVidimazione;
    }

    public Mezzo getMezzoVidimazione() {
        return mezzoVidimazione;
    }

    public void setMezzoVidimazione(Mezzo mezzoVidimazione) {
        this.mezzoVidimazione = mezzoVidimazione;
    }

    public void vidimazione(Mezzo mezzo, LocalDateTime dataVidimazione) {
        this.dataDiVidimazione = dataVidimazione;
        this.mezzoVidimazione = mezzo;
        this.vidimato = true;
    }

    
    @Override
    public String toString() {
        return "Biglietto{" +
                super.toString() +
                "vidimato=" + vidimato +
                ", mezzoVidimazione=" + mezzoVidimazione +
                ", dataDiVidimazione=" + dataDiVidimazione +
                '}';
    }
}
