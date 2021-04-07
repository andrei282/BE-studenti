package ro.tacheandrei.diseratie.gestionarestudenti.table;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class PageRequestDTO {
    private int startRow;
    private int endRow;
    private Integer page;
    private Integer pageSize;
    private List<Map<String, String>> sortModel = new ArrayList<>();
    private Map<String, Map<String, String>> filterModel = new HashMap<>();
    private Map<String, String> extraFields = new HashMap<>();

}
