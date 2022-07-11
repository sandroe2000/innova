package br.com.sdvs.innova.pessoa.domain;

import lombok.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Endereco implements Serializable {

    private static final long serialVersionUID = -2636212814114471552L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cep;
    private String logradouro;
    private boolean principal;
    private String numero;
    private String complemento;
    private String uf;
    private String cidade;
    private String bairro;
    private String tipo;

    @ManyToOne
    @JsonBackReference
    private Pessoa pessoa;

}