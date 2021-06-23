package ro.tacheandrei.disertatie.components.filter;

import lombok.Data;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.Map;

@Data
public class SearchCriteria {
    private Object columnName;
    private Object filter;
    private SearchOperation searchOperation;
    private FilterType filterType;

    private Map<String, String> extraProps;

    public SearchCriteria(String columnName, Object filter, SearchOperation searchOperation, FilterType filterType) {
        this.columnName = columnName;
        this.filter = filter;
        this.searchOperation = searchOperation;
        this.filterType = filterType;
    }

    public Path getPathExpression(Root root){
        return getPathExpression(root, null);
    }

    public Path getPathExpression(Root root, String explicitnColumn){
        Path expression = null;
        String columnName = explicitnColumn == null ? (String) this.columnName : explicitnColumn;
        if(columnName.contains(".")){
            String[] names = columnName.split("\\.");
            expression = root.get(names[0]);
            for (int i = 1; i < names.length; i++) {
                expression = expression.get(names[i]);
            }
        }else{
            expression = root.get(columnName);
        }
        return expression;
    }

}
