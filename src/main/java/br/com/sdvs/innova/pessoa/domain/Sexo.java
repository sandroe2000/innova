package br.com.sdvs.innova.pessoa.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
public class Sexo implements Serializable {

    private static final long serialVersionUID = -4409290369056638335L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = true)
    private String corporativo;

    private String descricao;
    private boolean inativo;
    
    public Sexo() {
    }

    public Sexo(String corporativo, String descricao, boolean inativo) {
        this.corporativo = corporativo;
        this.descricao = descricao;
        this.inativo = inativo;
    }

    public Sexo(Long id, String corporativo, String descricao, boolean inativo) {
        this.id = id;
        this.corporativo = corporativo;
        this.descricao = descricao;
        this.inativo = inativo;
    }
}