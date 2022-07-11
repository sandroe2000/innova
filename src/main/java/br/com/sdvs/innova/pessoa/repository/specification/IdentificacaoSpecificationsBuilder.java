package br.com.sdvs.innova.pessoa.repository.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;

import br.com.sdvs.innova.pessoa.domain.Pessoa;
import br.com.sdvs.innova.pessoa.dto.Identificacao;
import br.com.sdvs.innova.pessoa.dto.SearchCriteria;

public class IdentificacaoSpecificationsBuilder {
    
    private final List<SearchCriteria> params;

    public IdentificacaoSpecificationsBuilder() {
        params = new ArrayList<SearchCriteria>();
    }

    public IdentificacaoSpecificationsBuilder with(Identificacao identificacao) {
        if(identificacao.getId() != null && identificacao.getId()>0) params.add(new SearchCriteria("id", "EQUAL", identificacao.getId()));
        if(identificacao.getCorporativo() != null && !identificacao.getCorporativo().isEmpty()) params.add(new SearchCriteria("corporativo", "EQUAL", identificacao.getCorporativo()));
        if(identificacao.getCpf() != null && !identificacao.getCpf().isEmpty()) params.add(new SearchCriteria("cpf", "EQUAL", identificacao.getCpf()));
        if(identificacao.getRgie() != null && !identificacao.getRgie().isEmpty()) params.add(new SearchCriteria("rgie", "EQUAL", identificacao.getRgie()));
        if(identificacao.getNascimento() != null ) params.add(new SearchCriteria("nascimento", "EQUAL", identificacao.getNascimento() ));
        if(identificacao.getEmail() != null && !identificacao.getEmail().isEmpty()) params.add(new SearchCriteria("nome", "EQUAL", identificacao.getEmail()));
        if(identificacao.getNome() != null && !identificacao.getNome().isEmpty() ) params.add(new SearchCriteria("nome", "LIKE", identificacao.getNome()));
        return this;
    }

    public Specification<Pessoa> build() {
      
        if (params.size() == 0) {
            return null;
        }

        List<Specification<Pessoa>> specs = params.stream()
            .map(IdentificacaoSpecification::new)
            .collect(Collectors.toList());

        Specification<Pessoa> result = specs.get(0);

        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result).and(specs.get(i));
        }

        return result;
    }
}
