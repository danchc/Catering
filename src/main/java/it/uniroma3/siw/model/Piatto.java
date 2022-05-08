package it.uniroma3.siw.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Piatto {

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

    @OneToMany
    private List<Ingrediente> listaIngredienti;

    /* ----------------------------------------------------------------*/
    /* ------------ GETTERS & SETTERS ---------------------------------*/
    /* ----------------------------------------------------------------*/

    public Long getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return this.descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public List<Ingrediente> getListaIngredienti() {
        return this.listaIngredienti;
    }

    public void setListaIngredienti(List<Ingrediente> listaIngredienti) {
        this.listaIngredienti = listaIngredienti;
    }
}
