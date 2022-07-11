package br.com.sdvs.innova.pessoa.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TipoDePublico implements Serializable {

    private static final long serialVersionUID = -6788727755926916375L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String corporativo;
    private String descricao;
    private boolean inativo;

}
