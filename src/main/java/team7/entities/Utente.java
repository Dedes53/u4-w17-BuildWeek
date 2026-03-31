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
}
