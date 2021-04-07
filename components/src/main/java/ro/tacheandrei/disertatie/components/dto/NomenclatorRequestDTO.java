package ro.tacheandrei.disertatie.components.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class NomenclatorRequestDTO {
    private Long id;

    @NotNull
    private String cod;
    private LocalDate date;

    public NomenclatorRequestDTO(String cod, LocalDate date) {
        this.cod = cod;
        this.date = date;
    }

    public NomenclatorRequestDTO(String cod, Long id) {
        this.cod = cod;
        this.id = id;
    }

    public NomenclatorRequestDTO(String cod) {
        this.cod = cod;
    }

}
