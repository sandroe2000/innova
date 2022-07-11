package br.com.sdvs.innova.cep.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Logradouro implements Serializable  {

    @Id
    @Column(name = "log_nu")
    private Long logNu;
    
    @Column(name = "log_no")
    private String logNo;

    @Column(name = "log_complemento")
    private String logComplemento;

    private String cep;

    @Column(name = "txo_tx")
    private String tloTx; 

    @Column(name = "log_no_abrev")
    private String logNoAbrev;    

    @ManyToOne
    private Uf uf;

    @ManyToOne
    private Localidade localidade;

    @ManyToOne
    private Bairro bairro;
}
