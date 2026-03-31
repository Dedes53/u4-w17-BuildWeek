package team7.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class TitoloViaggio {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true)
    private String codice;

    private LocalDate dataEmissione;

    @ManyToOne
    private Rivenditore rivenditore;
}