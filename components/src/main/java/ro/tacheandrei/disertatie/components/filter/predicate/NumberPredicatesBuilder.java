package ro.tacheandrei.disertatie.components.filter.predicate;


import ro.tacheandrei.disertatie.components.filter.SearchCriteria;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class NumberPredicatesBuilder {

    private SearchCriteria criteria;

    public NumberPredicatesBuilder(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    public Predicate createPredicate(Root root, CriteriaBuilder criteriaBuilder) {
        switch (this.criteria.getSearchOperation()) {
            case EQUAL:
                return criteriaBuilder.equal(this.criteria.getPathExpression(root),
                        this.criteria.getFilter()
                );
            case CONTAIN:
                return criteriaBuilder.like(
                        this.criteria.getPathExpression(root).as(String.class),
                        "%" + criteria.getFilter().toString().toLowerCase() + "%"
                );
            default:
                return criteriaBuilder.equal(
                        this.criteria.getPathExpression(root),
                        this.criteria.getFilter()
                );

        }


    }

}
