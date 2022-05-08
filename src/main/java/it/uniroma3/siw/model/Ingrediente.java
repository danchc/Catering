package it.uniroma3.siw.model;

import javax.persistence.*;

@Entity
public class Ingrediente {

    /* ----------------------------------------------------------------*/
    /* --------------------- variabili --------------------------------*/
    /* ----------------------------------------------------------------*/

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String origine;

    @Column(nullable = false)
    private String descrizione;

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

    public String getOrigine() {
        return this.origine;
    }

    public void setOrigine(String origine) {
        this.origine = origine;
    }

    public String getDescrizione() {
        return this.descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}
