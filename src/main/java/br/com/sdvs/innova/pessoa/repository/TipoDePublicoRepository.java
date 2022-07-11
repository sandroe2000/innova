package br.com.sdvs.innova.pessoa.repository;

import br.com.sdvs.innova.pessoa.domain.TipoDePublico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoDePublicoRepository extends JpaRepository<TipoDePublico, Long> {
    Page<TipoDePublico> findAllByDescricaoContaining(String descricao, Pageable pageable);
    TipoDePublico findByDescricao(String descricao);
}
