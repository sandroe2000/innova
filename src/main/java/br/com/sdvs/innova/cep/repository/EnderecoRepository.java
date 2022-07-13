package br.com.sdvs.innova.cep.repository;

import br.com.sdvs.innova.pessoa.domain.Endereco;
import br.com.sdvs.innova.pessoa.domain.Pessoa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EnderecoRepository  extends JpaRepository<Endereco, Long> {

    Page<Endereco> findAByPessoa(Pessoa pessoa, Pageable pageable);

    @Query(value = "SELECT 0 as id, LOG.CEP AS cep, LOG.LOG_NO AS logradouro, '' as numero, LOG.LOG_COMPLEMENTO AS complemento, LOC.UF_UFE_SG AS uf, LOC.LOC_NO AS cidade, BAI.BAI_NO AS bairro, '' as tipo FROM LOGRADOURO LOG INNER JOIN BAIRRO BAI ON LOG.BAIRRO_BAI_NU = BAI.BAI_NU INNER JOIN LOCALIDADE LOC ON BAI.LOCALIDADE_LOC_NU = LOC.LOC_NU WHERE LOG.LOG_NO LIKE ?1",
           countQuery = "SELECT count(LOG.CEP) FROM LOGRADOURO LOG INNER JOIN BAIRRO BAI ON LOG.BAIRRO_BAI_NU = BAI.BAI_NU INNER JOIN LOCALIDADE LOC ON BAI.LOCALIDADE_LOC_NU = LOC.LOC_NU WHERE LOG.LOG_NO LIKE ?1",
           nativeQuery = true)
    Page<Endereco> findByLogNoContaining(String logradouro, Pageable pageable);
}
