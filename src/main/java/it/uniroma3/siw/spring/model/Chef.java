package it.uniroma3.siw.spring.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Chef {

    /* ----------------------------------------------------------------*/
    /* --------------------- variabili --------------------------------*/
    /* ----------------------------------------------------------------*/

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cognome;

    @Column(nullable = false)
    private String nazionalita;

    @OneToMany(mappedBy = "chef")
    private List<Buffet> buffetProposti;

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

    public String getCognome() {
        return this.cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNazionalita() {
        return this.nazionalita;
    }

    public void setNazionalita(String nazionalita) {
        this.nazionalita = nazionalita;
    }

    public List<Buffet> getBuffetProposti() {
        return this.buffetProposti;
    }

    public void setBuffetProposti(List<Buffet> buffetProposti) {
        this.buffetProposti = buffetProposti;
    }
}
