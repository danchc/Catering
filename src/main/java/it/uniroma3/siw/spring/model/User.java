package it.uniroma3.siw.spring.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Date;
import java.util.List;

/**
 * Questa entità modella le informazioni di una persona reale, quindi specifica tutte le informazioni
 * necessarie per indicare una persona.
 */
@Entity
@Table(name = "utente")
@Getter @Setter @NoArgsConstructor
public class User {

    /* ----------------------------------------------------------------*/
    /* --------------------- variabili --------------------------------*/
    /* ----------------------------------------------------------------*/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private String cognome;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String telefono;

    @NotNull
    private String indirizzo;

    @NotNull
    private String citta;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataNascita;

    @OneToMany
    private List<Buffet> preferiti;

}
