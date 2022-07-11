package br.com.sdvs.innova.cep.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sdvs.innova.cep.domain.Logradouro;

@Repository
public interface LogradouroRepository extends JpaRepository<Logradouro, Long> {
    Optional<Logradouro> findByCep(String cep);
    Page<Logradouro> findByLogNoContaining(String logradouro, Pageable pageable);
}
