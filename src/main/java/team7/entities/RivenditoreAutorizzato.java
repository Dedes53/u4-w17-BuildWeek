package team7.entities;

import java.util.UUID;

public class RivenditoreAutorizzato extends Rivenditore {

    //   costruttori
    protected RivenditoreAutorizzato() {
    }

    public RivenditoreAutorizzato(UUID rivenditoreId, String nomeAttivita) {
        super(rivenditoreId, nomeAttivita);
    }


}
