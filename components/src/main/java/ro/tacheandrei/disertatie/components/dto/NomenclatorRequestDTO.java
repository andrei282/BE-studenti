package ro.tacheandrei.disertatie.components.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class NomenclatorRequestDTO {
    private Long id;

    @NotNull
    private String cod;

    public NomenclatorRequestDTO(String cod, Long id) {
        this.cod = cod;
        this.id = id;
    }

    public NomenclatorRequestDTO(String cod) {
        this.cod = cod;
    }

}
