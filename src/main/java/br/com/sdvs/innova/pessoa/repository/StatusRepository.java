package br.com.sdvs.innova.pessoa.repository;

import br.com.sdvs.innova.pessoa.domain.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
    Page<Status> findAllByDescricaoContaining(String descricao, Pageable pageable);
    Status findByDescricao(String descricao);
}
