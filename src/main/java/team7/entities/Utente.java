package team7.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity

public class Utente {

    @Id
    @GeneratedValue
    private UUID id;

    private String nome;
    private String cognome;

    @OneToOne
    @JoinColumn(name = "tessera_utente")
    private Tessera tessera;

    //costruttori
    protected Utente() {
    }

    public Utente(String nome, String cognome,) {
        this.nome = nome;
        this.cognome = cognome;
       // this.tessera = tessera;
    }


    //   getters/setters
    public UUID getId() {
        return id;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Tessera getTessera() {
        return this.tessera;
    }

    public void setTessera(Tessera tessera) {
        this.tessera = tessera;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", tessera=" + tessera +
                '}';
    }
}
