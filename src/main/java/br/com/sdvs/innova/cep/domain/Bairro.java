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
public class Bairro implements Serializable  {

    @Id
    @Column(name = "bai_nu")
    private Long baiNu;

    @Column(name = "bai_no")
    private String baiNo;

    @ManyToOne
    private Uf uf;

    @ManyToOne
    private Localidade localidade;
    
}
