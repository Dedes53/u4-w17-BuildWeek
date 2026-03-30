package team7.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "rivenditori")
public abstract class Rivenditore {

    //  attibuti
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID rivenditoreId;
    @Column(name = "nome_attivita")
    private String nomeAttivita;


    //  costruttori
    public Rivenditore(UUID rivenditore_id, String nome_attivita) {
        this.rivenditoreId = rivenditore_id;
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
}
