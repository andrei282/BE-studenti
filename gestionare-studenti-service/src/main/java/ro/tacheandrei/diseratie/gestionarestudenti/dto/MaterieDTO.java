package ro.tacheandrei.diseratie.gestionarestudenti.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import ro.tacheandrei.diseratie.gestionarestudenti.entity.Materie;
import ro.tacheandrei.diseratie.gestionarestudenti.table.TableHeader;

@Data
@NoArgsConstructor
public class MaterieDTO {

    @TableHeader(name = "Id")
    private Long id;

    @TableHeader(name = "Denumire")
    private String denumire;

    @TableHeader(name = "Aberviere")
    private String abreviere;

    @TableHeader(name = "Cod")
    private String cod;

    @TableHeader(name = "Profesor")
    private String profesor;

    @TableHeader(name = "Masterat")
    private String denumireMasterat;
    private MasteratDTO masterat;

    public static MaterieDTO from(Materie materie) {
        MaterieDTO materieDTO = new MaterieDTO();
        BeanUtils.copyProperties(materie, materieDTO, "masterat");

        materieDTO.setMasterat(MasteratDTO.from(materie.getMasterat()));
        materieDTO.setDenumireMasterat(materie.getMasterat().getAbreviere());

        return materieDTO;
    }
}
