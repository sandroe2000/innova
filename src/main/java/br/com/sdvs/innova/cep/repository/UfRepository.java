package br.com.sdvs.innova.cep.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sdvs.innova.cep.domain.Uf;

@Repository
public interface UfRepository extends JpaRepository<Uf, String> {
}
