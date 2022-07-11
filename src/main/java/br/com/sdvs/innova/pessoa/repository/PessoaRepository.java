package br.com.sdvs.innova.pessoa.repository;

import br.com.sdvs.innova.pessoa.domain.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> , JpaSpecificationExecutor<Pessoa> {

	Pessoa findByCpf(String cpf);
	Pessoa findByCorporativoAndCpf(String corporativo, String cpf);
}