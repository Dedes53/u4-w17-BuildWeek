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


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
}
