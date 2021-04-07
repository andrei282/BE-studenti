package ro.tacheandrei.disertatie.components.filter;

import org.springframework.data.jpa.domain.Specification;
import ro.tacheandrei.disertatie.components.filter.predicate.NumberPredicatesBuilder;
import ro.tacheandrei.disertatie.components.filter.predicate.StringPredicatesBuilder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GenericSpecification<T> implements Specification<T> {

    private final List<SearchCriteria> searchCriteriaList = new ArrayList<>();

    public GenericSpecification(Map<String, Map<String, String>> filterModel) {
        String columnName;
        Object filter;
        SearchOperation searchOperation;
        FilterType filterType;

        for (Map.Entry<String, Map<String, String>> entry : filterModel.entrySet()) {
            columnName = entry.getKey();
            filter = entry.getValue().get("filter");
            searchOperation = SearchOperation.getValue(entry.getValue().get("type"));
            filterType = FilterType.getValue(entry.getValue().get("filterType"));

            if (filterType.equals(FilterType.DATE)) {
                filter = entry.getValue().get("dateFrom");
                this.searchCriteriaList.add(new SearchCriteria(columnName, filter, searchOperation, filterType));
            } else {
                this.searchCriteriaList.add(new SearchCriteria(columnName, filter, searchOperation, filterType));
            }
        }
    }

    public GenericSpecification() {
    }

    public void addExtraCriteria(final List<SearchCriteria> extra) {

        this.searchCriteriaList.addAll(extra);
    }

    public void addExtraCriteria(SearchCriteria extra) {
        this.searchCriteriaList.add(extra);
    }


    public List<SearchCriteria> getSearchCriteriaList() {
        return searchCriteriaList;
    }

    @Override
    public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        for (SearchCriteria criteria : searchCriteriaList) {

            switch (criteria.getFilterType()) {
                case NUMBER:
                    predicates.add(new NumberPredicatesBuilder(criteria).createPredicate(root, criteriaBuilder));
                    break;
                case TEXT:
                    predicates.add(new StringPredicatesBuilder(criteria).createPredicate(root, criteriaBuilder));
                    break;
            }

        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenericSpecification<?> that = (GenericSpecification<?>) o;
        return Objects.equals(searchCriteriaList, that.searchCriteriaList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(searchCriteriaList);
    }
}
