package br.com.sdvs.innova.pessoa.repository.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import br.com.sdvs.innova.pessoa.domain.Pessoa;
import br.com.sdvs.innova.pessoa.dto.SearchCriteria;

public class IdentificacaoSpecification implements Specification<Pessoa> {

    private SearchCriteria criteria;

    public IdentificacaoSpecification(final SearchCriteria criteria) {
        super();
        this.criteria = criteria;
    }

    public SearchCriteria getCriteria() {
        return criteria;
    }

    @Override
    public Predicate toPredicate(Root<Pessoa> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        
        if (criteria.getOperation().equalsIgnoreCase("GT")) {
            return criteriaBuilder.greaterThanOrEqualTo( root.<String> get(criteria.getKey()), criteria.getValue().toString() );
        } 
        
        if (criteria.getOperation().equalsIgnoreCase("LT")) {
            return criteriaBuilder.lessThanOrEqualTo(root.<String> get( criteria.getKey()), criteria.getValue().toString() );
        } 
        
        if (criteria.getOperation().equalsIgnoreCase("EQUAL")) {
            return criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue());            
        }

        if (criteria.getOperation().equalsIgnoreCase("LIKE")) {
            return criteriaBuilder.like(root.<String>get( criteria.getKey()), "%" + criteria.getValue() + "%" );
        }

        return null;
    }
    
}
