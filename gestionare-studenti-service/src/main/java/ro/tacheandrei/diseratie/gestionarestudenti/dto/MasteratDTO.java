package ro.tacheandrei.diseratie.gestionarestudenti.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import ro.tacheandrei.diseratie.gestionarestudenti.entity.Masterat;
import ro.tacheandrei.diseratie.gestionarestudenti.table.TableHeader;

import java.util.List;

@Data
@NoArgsConstructor
public class MasteratDTO {

    @TableHeader(name = "Id")
    private Long id;

    @TableHeader(name = "Cod")
    private String cod;

    @TableHeader(name = "Denumire")
    private String denumire;

    @TableHeader(name = "Abreviere")
    private String abreviere;

    @TableHeader(name = "Responsabil")
    private String responsabil;

    private List<MaterieDTO> materii;

    public static MasteratDTO from(Masterat masterat) {
        MasteratDTO masteratDTO = new MasteratDTO();
        BeanUtils.copyProperties(masterat, masteratDTO);
        return masteratDTO;
    }

}
