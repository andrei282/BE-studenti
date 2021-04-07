package ro.tacheandrei.disertatie.components.filter.predicate;


import ro.tacheandrei.disertatie.components.filter.SearchCriteria;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class StringPredicatesBuilder {
    private SearchCriteria criteria;

    public StringPredicatesBuilder(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    public Predicate createPredicate(Root root, CriteriaBuilder criteriaBuilder) {
        switch (this.criteria.getSearchOperation()) {
            case EQUAL:
                return criteriaBuilder.equal(
                        criteriaBuilder.lower(this.criteria.getPathExpression(root).as(String.class)),
                        criteria.getFilter().toString().toLowerCase()
                );
            case CONTAIN:
                return criteriaBuilder.like(
                        criteriaBuilder.lower(this.criteria.getPathExpression(root).as(String.class)),
                        "%" + criteria.getFilter().toString().toLowerCase() + "%"
                );
            default:
                return criteriaBuilder.equal(
                        criteriaBuilder.lower(this.criteria.getPathExpression(root).as(String.class)),
                        this.criteria.getFilter()
                );

        }
    }

}
