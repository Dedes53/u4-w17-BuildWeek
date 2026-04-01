package team7.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Biglietto extends TitoloViaggio {

    private boolean vidimato = false;

    @ManyToOne
    private Mezzo mezzoVidimazione;

    private LocalDateTime dataDiVidimazione;

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
}