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
public class Localidade implements Serializable  {

    @Id
    @Column(name = "loc_nu")
    private Long locNu;

    @Column(name = "loc_no")
    private String locNo;

    private String cep;

    @Column(name = "ufe_sg")
    private String ufeSg;
}
