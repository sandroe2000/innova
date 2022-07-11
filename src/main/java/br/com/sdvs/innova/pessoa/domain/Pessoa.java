package br.com.sdvs.innova.pessoa.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Pessoa implements Serializable {

    private static final long serialVersionUID = 2905651040314410436L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String corporativo;

    @ManyToOne
    @JoinColumn(name = "publico")
    private TipoDePublico publico;

    @ManyToOne
    @JoinColumn(name = "status")
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name="pfj")
    private PessoaFisicaJuridica pfj;

    @ElementCollection
    @CollectionTable(name ="naocontactar")
    @Enumerated(EnumType.STRING)
    private Set<NaoContactarPor> naocontactar;

    @ManyToOne
    @JoinColumn(name = "tratamento")
    private Tratamento tratamento;

    @NotNull
    @Size(min = 2, message = "Nome deve conter no mínimo 2 caracteres.")
    @Column(nullable = false)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "sexo")
    private Sexo sexo;

    @Email(message = "Email deve ser válido.")
    private String email;

    //@Pattern(regexp="\\(\\d{2,3}\\)\\d{4,5}-\\d{4}", message = "Telefone com formato inválido!")
    private String telefone;
    private String celular;

    @Column(name="whatsapp")
    private boolean whatsApp;

    @Column(name="rgie")
    private String rgie;

    private String cpf;

    private String cnh;
    private String passaporte;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "pt-BR", timezone = "Brazil/East")
    private LocalDate nascimento;

    @OneToMany(mappedBy = "pessoa")
    @JsonManagedReference
    @ToString.Exclude
    private Set<Endereco> enderecos;

}