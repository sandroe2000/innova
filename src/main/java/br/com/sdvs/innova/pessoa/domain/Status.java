package br.com.sdvs.innova.pessoa.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Status implements Serializable {

    private static final long serialVersionUID = -4972251410677592918L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String corporativo;
    private String descricao;
    private boolean inativo;

}