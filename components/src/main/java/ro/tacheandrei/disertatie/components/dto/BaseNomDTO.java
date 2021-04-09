package ro.tacheandrei.disertatie.components.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BaseNomDTO {
    private Long id;
    private String cod;
    private String denumire;
}
