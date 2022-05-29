package it.uniroma3.siw.spring.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
public class Buffet {

    /* ----------------------------------------------------------------*/
    /* --------------------- variabili --------------------------------*/
    /* ----------------------------------------------------------------*/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String descrizione;

    @ManyToOne(cascade = {CascadeType.ALL})
    private Chef chef;

    @OneToMany
    private List<Piatto> piatti;

}
