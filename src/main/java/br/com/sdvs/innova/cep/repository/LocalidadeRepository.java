package br.com.sdvs.innova.cep.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sdvs.innova.cep.domain.Localidade;
import br.com.sdvs.innova.cep.domain.Uf;

public interface LocalidadeRepository extends JpaRepository<Localidade, Long> {
    Page<Localidade> findAllByUf(Uf uf, Pageable pageable);
    Localidade findByLocNo(String locNo);;
    Localidade findByUfAndLocNo(Uf uf, String locNo);
}

