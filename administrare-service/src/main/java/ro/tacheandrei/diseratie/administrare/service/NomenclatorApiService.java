package ro.tacheandrei.diseratie.administrare.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ro.tacheandrei.diseratie.administrare.domain.Nomenclator;
import ro.tacheandrei.diseratie.administrare.domain.ValoriNomenclator;
import ro.tacheandrei.diseratie.administrare.repository.NomenclatorRepository;
import ro.tacheandrei.diseratie.administrare.repository.ValoriNomenclatorRepository;
import ro.tacheandrei.disertatie.components.dto.NomenclatorRequestDTO;

import java.util.List;

@Service
@AllArgsConstructor
public class NomenclatorApiService {

    private final NomenclatorRepository nomenclatorRepository;
    private final ValoriNomenclatorRepository valoriNomenclatorRepository;
    private final CalculLiniiNomenclatoareService calculLiniiNomenclatoareService;

    public List<JsonNode> cautaNomenclatorByCod(NomenclatorRequestDTO nomenclatorRequestDTO, boolean withLinks) {
        final String codNomenclator = nomenclatorRequestDTO.getCod();
        final Nomenclator nomenclator = nomenclatorRepository.findByCod(codNomenclator);

        List<ValoriNomenclator> valoriNomenclator = valoriNomenclatorRepository.findAllByCod(nomenclator.getCod());

        return calculLiniiNomenclatoareService.calculeazaLiniiToDTO(valoriNomenclator, nomenclator.getCampSet(), withLinks, true);
    }
}
