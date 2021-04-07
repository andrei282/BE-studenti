package ro.tacheandrei.diseratie.administrare.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageDTO {
    private List<JsonNode> content;
    private Long totalElements;

}
