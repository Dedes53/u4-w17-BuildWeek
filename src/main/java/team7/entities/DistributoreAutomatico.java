package team7.entities;

public class DistributoreAutomatico extends Rivenditore {

    //   attributi
    private boolean attivo;


    //   costruttore
    protected DistributoreAutomatico() {
    }

    public DistributoreAutomatico(String nomeAttivita, boolean attivo) {
        super(nomeAttivita);
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
