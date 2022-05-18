package it.uniroma3.siw.spring.model;

import javax.persistence.*;
import java.util.List;

@Entity
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

    @ManyToOne
    private Chef chef;

    @OneToMany
    private List<Piatto> piatti;

    /* ----------------------------------------------------------------*/
    /* ------------ GETTERS & SETTERS ---------------------------------*/
    /* ----------------------------------------------------------------*/

    public String getDescrizione() {
        return this.descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Long getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
