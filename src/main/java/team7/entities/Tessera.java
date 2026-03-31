package team7.entities;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Tessera {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true)
    private String numero;

    private LocalDate dataDiScadenza;

    @ManyToOne
    private Utente utente;
}