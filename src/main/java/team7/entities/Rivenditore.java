package team7.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "rivenditori")
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQuery(
        name = "distributori_automatici_attivi",
        query = "select d from DistributoreAutomatico d where d.attivo=true"
)
@NamedQuery(
        name = "distributori_automatici_disattivati",
        query = "select d from DistributoreAutomatico d where d.attivo=false"
)
public abstract class Rivenditore {

    //  attibuti
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID rivenditoreId;

    @Column(name = "nome_attivita")
    private String nomeAttivita;

    @Column(name = "contatore_titoli", nullable = false)
    private long contatoreTitoli;

    //  costruttori
    protected Rivenditore() {
    }

    public Rivenditore(String nome_attivita) {
        this.nomeAttivita = nome_attivita;
    }


    //  getters/setters
    public UUID getRivenditoreId() {
        return rivenditoreId;
    }

    public String getNomeAttivita() {
        return nomeAttivita;
    }

    public void setNomeAttivita(String nomeAttivita) {
        this.nomeAttivita = nomeAttivita;
    }

    @Override
    public String toString() {
        return "Rivenditore{" +
                "rivenditore_id=" + rivenditoreId +
                ", nome_attivita='" + nomeAttivita + '\'' +
                '}';
    }

    //   genera il codice univoco per il titolo di viaggio che emette
    public String generaCodiceUnivoco() {
        this.contatoreTitoli++;
        String uuidCorto = this.rivenditoreId.toString().substring(0, 8);
        return String.format("RIV-%s-%05d", uuidCorto, this.contatoreTitoli);
    }

}
