package team7.entities;

import java.util.UUID;

public class DistributoreAutomatico extends Rivenditore {

    //   attributi
    private boolean attivo;


    //   costruttore
    protected DistributoreAutomatico() {
    }

    public DistributoreAutomatico(UUID rivenditoreId, String nomeAttivita, boolean attivo) {
        super(rivenditoreId, nomeAttivita);
        this.attivo = attivo;
    }


    //   getter/setter
    public boolean isAttivo() {
        return attivo;
    }

    public void setAttivo(boolean attivo) {
        this.attivo = attivo;
    }


    @Override
    public String toString() {
        return "DistributoreAutomatico{" +
                "attivo=" + attivo +
                '}';
    }
}
