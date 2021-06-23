package ro.tacheandrei.disertatie.components.table;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class PageRequestDTO {
    private Integer page;
    private Integer pageSize;
    private List<Map<String, String>> sortModel = new ArrayList<>();
    private Map<String, Map<String, String>> filterModel = new HashMap<>();

}
