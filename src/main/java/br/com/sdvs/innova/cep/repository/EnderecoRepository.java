package br.com.sdvs.innova.cep.repository;

import br.com.sdvs.innova.pessoa.domain.Endereco;
import br.com.sdvs.innova.pessoa.domain.Pessoa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository  extends JpaRepository<Endereco, Long> {

    Page<Endereco> findAByPessoa(Pessoa pessoa, Pageable pageable);
}
