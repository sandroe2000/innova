package br.com.sdvs.innova.cep.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.sdvs.innova.pessoa.domain.Endereco;

@Repository
public class EnderecoJdbcTemplate {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Page<Endereco> findByLogNoContaining(String logradouro, Pageable pageable){
        
        Order order = !pageable.getSort().isEmpty() ? pageable.getSort().toList().get(0) : Order.by("cep");

        List<Endereco> enderecos = jdbcTemplate.query(
            "SELECT LOG.CEP AS cep, LOG.LOG_NO AS logradouro, '' as numero, LOG.LOG_COMPLEMENTO AS complemento, LOC.UF_UFE_SG AS uf, LOC.LOC_NO AS cidade, BAI.BAI_NO AS bairro, '' as tipo FROM LOGRADOURO LOG INNER JOIN BAIRRO BAI ON LOG.BAIRRO_BAI_NU = BAI.BAI_NU INNER JOIN LOCALIDADE LOC ON BAI.LOCALIDADE_LOC_NU = LOC.LOC_NU WHERE LOG.LOG_NO LIKE '%"+logradouro+"%' ORDER BY " + 
            order.getProperty() + " " + order.getDirection().name() + " LIMIT " + pageable.getPageSize() + " OFFSET " + pageable.getOffset(),
            (rs, rowNum) -> mapEnderecoResult(rs));
        return new PageImpl<Endereco>(enderecos, pageable, count(logradouro));
    }

    public int count(String logradouro) {
        return jdbcTemplate.queryForObject("SELECT count(LOG.CEP) FROM LOGRADOURO LOG INNER JOIN BAIRRO BAI ON LOG.BAIRRO_BAI_NU = BAI.BAI_NU INNER JOIN LOCALIDADE LOC ON BAI.LOCALIDADE_LOC_NU = LOC.LOC_NU WHERE LOG.LOG_NO LIKE '%"+logradouro+"%'", Integer.class);
    }

    private Endereco mapEnderecoResult(final ResultSet rs) throws SQLException {
        Endereco endereco = new Endereco();
        endereco.setCep(rs.getString("CEP"));
        endereco.setLogradouro(rs.getString("LOGRADOURO"));
        endereco.setComplemento(rs.getString("COMPLEMENTO"));
        endereco.setUf(rs.getString("UF"));
        endereco.setCidade(rs.getString("CIDADE"));
        endereco.setBairro(rs.getString("BAIRRO"));
        return endereco;
    }
}
