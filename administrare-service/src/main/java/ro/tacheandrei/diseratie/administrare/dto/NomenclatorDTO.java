package ro.tacheandrei.diseratie.administrare.dto;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import ro.tacheandrei.diseratie.administrare.domain.Camp;
import ro.tacheandrei.diseratie.administrare.domain.Nomenclator;

import java.util.Set;
import java.util.stream.Collectors;

@Data
public class NomenclatorDTO {
    private long id;
    private String cod;
    private String denumire;

    private boolean afiseazaValabilitate;

    private boolean allowAdd;

    private boolean allowDelete;

    private boolean overwriteCod;

    private Set<CampDTO> campSet;

    public static NomenclatorDTO from(Nomenclator nomenclator){
        NomenclatorDTO nomenclatorDTO = new NomenclatorDTO();

        BeanUtils.copyProperties(nomenclator, nomenclatorDTO);
        nomenclatorDTO.setCampSet(
                nomenclator
                    .getCampSet()
                    .stream()
                    .map(CampDTO::from)
                    .collect(Collectors.toSet())
        );

        return nomenclatorDTO;
    }

}
