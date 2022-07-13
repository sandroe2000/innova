package br.com.sdvs.innova.pessoa.repository;

import br.com.sdvs.innova.pessoa.domain.Sexo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SexoRepository extends JpaRepository<Sexo, Long> {
    Page<Sexo> findAllByDescricaoContaining(String descricao, Pageable pageable);
    Sexo findByDescricao(String descricao);
}
