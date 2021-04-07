package ro.tacheandrei.disertatie.components.table;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Objects;

@Data
public class PageableCreation {

    private PageableCreation() {
        throw new IllegalStateException("Utility class");
    }

    public static Pageable createDefaultPageable(String field, Sort.Direction direction, PageRequestDTO pageRequest) {
        String sortField = "id";
        Sort.Direction searchDirection = Sort.Direction.DESC;
        int page = 0;
        int pageSize = 10;

        if (pageRequest.getPage() != null) {
            page = pageRequest.getPage();
        }

        if (pageRequest.getPageSize() != null && pageRequest.getPageSize() != 0) {
            pageSize = pageRequest.getPageSize();
        }

        if (!pageRequest.getSortModel().isEmpty()) {
            sortField = pageRequest.getSortModel().get(0).get("colId");
            searchDirection = Sort.Direction.fromString(pageRequest.getSortModel().get(0).get("sort").toLowerCase());
        } else if (Objects.nonNull(field) && Objects.nonNull(direction)) {
            sortField = field;
            searchDirection = direction;
        }

        return PageRequest.of(page, pageSize, searchDirection, sortField);
    }
}
