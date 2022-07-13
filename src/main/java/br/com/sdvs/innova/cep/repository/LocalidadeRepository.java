package br.com.sdvs.innova.cep.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sdvs.innova.cep.domain.Localidade;

public interface LocalidadeRepository extends JpaRepository<Localidade, Long> {
    
    Page<Localidade> findAllByUfeSg(String ufeSg, Pageable pageable);
    Localidade findByLocNo(String locNo);;
    Localidade findByUfeSgAndLocNo(String ufeSg, String locNo);
}

