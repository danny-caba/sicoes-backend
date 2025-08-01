package pe.gob.osinergmin.sicoes.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Combo {

    @Id
    @Column(name = "ID")
    private Long id;
    @Column(name = "VALOR")
    private String valor;
}