package br.com.sdvs.innova.cep.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

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
@Table(indexes = @Index(columnList = "log_no"))
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

    @Column(name = "ufe_sg")
    private String ufeSg;

    @Column(name = "loc_nu")
    private Long locNu;

    @Column(name = "loc_no")
    private String locNo;

    @Column(name = "bai_nu")
    private Long baiNu;

    @Column(name = "bai_no")
    private String baiNo;
}
