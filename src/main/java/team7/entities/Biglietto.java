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

    public Biglietto(String codiceUnivoco, LocalDate dataEmissione, Rivenditore rivenditore, boolean vidimato, Mezzo mezzoVidimazione, LocalDateTime dataDiVidimazione) {
        super(codiceUnivoco, dataEmissione, rivenditore);
        this.vidimato = vidimato;
        this.mezzoVidimazione = mezzoVidimazione;
        this.dataDiVidimazione = dataDiVidimazione;
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
}