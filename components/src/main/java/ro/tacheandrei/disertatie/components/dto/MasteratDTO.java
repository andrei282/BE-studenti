package ro.tacheandrei.disertatie.components.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class MasteratDTO extends BaseNomDTO{
    private String abreviere;
    private String coordonator;
    private List<MaterieDTO> materii;
}
