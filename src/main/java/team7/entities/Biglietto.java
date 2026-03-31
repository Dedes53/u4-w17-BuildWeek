package team7.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Biglietto extends TitoloViaggio {

    private boolean vidimato = false;

    @ManyToOne
    private Mezzo mezzoVidimazione;

    private LocalDateTime dataDiVidimazione;
}