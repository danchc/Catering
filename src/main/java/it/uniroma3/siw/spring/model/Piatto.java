package it.uniroma3.siw.spring.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor
public class Piatto {

    /* ----------------------------------------------------------------*/
    /* --------------------- variabili --------------------------------*/
    /* ----------------------------------------------------------------*/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descrizione;

    @ManyToOne
    private Buffet buffet;

    @ManyToMany(mappedBy = "piatti", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Ingrediente> ingredienti;

}
