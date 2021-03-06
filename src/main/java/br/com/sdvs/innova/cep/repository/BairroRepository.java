package br.com.sdvs.innova.cep.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sdvs.innova.cep.domain.Bairro;

@Repository
public interface BairroRepository extends JpaRepository<Bairro, Long> {
    Page<Bairro> findAllByLocNu(Long locNu, Pageable pageable);
}
