package ro.tacheandrei.disertatie.components.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class MaterieDTO extends BaseNomDTO{
    private String abreviere;
    private String masterat;
    private String profesor;
    private List<BaseNomDTO> proiecte;
}
