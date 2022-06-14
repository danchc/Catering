package it.uniroma3.siw.spring.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Nazione {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 2)
    private String iso;

    private String nome;

    private int numcode;

    private int phonecode;

    @OneToMany(mappedBy = "nazione")
    private List<Chef> chef;
}