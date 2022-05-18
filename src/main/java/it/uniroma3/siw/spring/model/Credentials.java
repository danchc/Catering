package it.uniroma3.siw.spring.model;

import com.sun.istack.NotNull;

import javax.persistence.*;

/* Questa classe modella le credenziali di un generico utente all'interno del sito */
@Entity
public class Credentials {

    /* ----------------------------------------------------------------*/
    /* --------------------- variabili --------------------------------*/
    /* ----------------------------------------------------------------*/

    /* roles */
    public final static String RUOLO_ADMIN = "ADMIN";
    public final static String RUOLO_DEFAULT= "DEFAULT";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    @Column(name = "confirmed-password")
    private String confermaPassword;

    @NotNull
    private String ruolo;

    @NotNull
    private boolean isEnabled;

    @OneToOne(cascade = {CascadeType.ALL})
    private User user;
}
