package it.uniroma3.siw.spring.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
public class Piatto {

    /* ----------------------------------------------------------------*/
    /* --------------------- variabili --------------------------------*/
    /* ----------------------------------------------------------------*/

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String nome;


    private String descrizione;

    @OneToMany
    private List<Ingrediente> listaIngredienti;

}
