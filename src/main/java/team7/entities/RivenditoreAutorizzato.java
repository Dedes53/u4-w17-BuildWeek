package team7.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "rivenditori_autorizzati")
public class RivenditoreAutorizzato extends Rivenditore {

    //   costruttori
    protected RivenditoreAutorizzato() {
    }

    public RivenditoreAutorizzato(String nomeAttivita) {
        super(nomeAttivita);
    }


}
