package br.com.sdvs.innova.pessoa.repository;

import br.com.sdvs.innova.pessoa.domain.Tratamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TratamentoRepository extends JpaRepository<Tratamento, Long> {
    Page<Tratamento> findAllByDescricaoContaining(String descricao, Pageable pageable);
}
