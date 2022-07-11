package br.com.sdvs.innova.cep.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

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
public class Uf implements Serializable {

    @Id
    @Column(name = "ufe_sg")
    private String ufeSg; 

    @Column(name = "ufe_cep_ini")
    private String ufeCepIni;

    @Column(name = "ufe_cep_fim")
    private String ufeCepFim;
}
