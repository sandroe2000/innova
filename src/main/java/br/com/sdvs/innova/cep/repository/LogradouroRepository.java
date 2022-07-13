package br.com.sdvs.innova.cep.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.sdvs.innova.cep.domain.Logradouro;

@Repository
public interface LogradouroRepository extends JpaRepository<Logradouro, Long> {
    
    @Query(
        value = "SELECT LOG.LOG_NU, LOG.CEP, LOG.TXO_TX, LOG.LOG_NO_ABREV, LOG.LOG_NO, LOG.LOG_COMPLEMENTO, LOG.UFE_SG, LOG.LOC_NU, LOC.LOC_NO, LOG.BAI_NU, BAI.BAI_NO FROM LOGRADOURO LOG LEFT JOIN LOCALIDADE LOC ON LOG.LOC_NU = LOC.LOC_NU LEFT JOIN BAIRRO BAI ON LOG.BAI_NU = BAI.BAI_NU WHERE LOG.CEP = ?1", 
        nativeQuery = true
    )
    Optional<Logradouro> findByCep(String cep);

    @Query(
        value = "SELECT LOG.LOG_NU, LOG.CEP, LOG.TXO_TX, LOG.LOG_NO_ABREV, LOG.LOG_NO, LOG.LOG_COMPLEMENTO, LOG.UFE_SG, LOG.LOC_NU, LOC.LOC_NO, LOG.BAI_NU, BAI.BAI_NO FROM LOGRADOURO LOG LEFT JOIN LOCALIDADE LOC ON LOG.LOC_NU = LOC.LOC_NU LEFT JOIN BAIRRO BAI ON LOG.BAI_NU = BAI.BAI_NU WHERE LOG.LOG_NO LIKE ?1", 
        countQuery = "SELECT COUNT(LOG.LOG_NU) FROM LOGRADOURO LOG WHERE LOG.LOG_NO LIKE ?1", 
        nativeQuery = true
    )
    Page<Logradouro> findByLogNoContaining(String logradouro, Pageable pageable);
}
