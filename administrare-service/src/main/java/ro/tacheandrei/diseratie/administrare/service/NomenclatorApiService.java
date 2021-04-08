package ro.tacheandrei.diseratie.administrare.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ro.tacheandrei.diseratie.administrare.domain.Nomenclator;
import ro.tacheandrei.diseratie.administrare.domain.ValoriNomenclator;
import ro.tacheandrei.diseratie.administrare.repository.NomenclatorRepository;
import ro.tacheandrei.diseratie.administrare.repository.ValoriNomenclatorRepository;
import ro.tacheandrei.disertatie.components.dto.NomenclatorRequestDTO;
import ro.tacheandrei.disertatie.components.enums.NomenclatorEnum;

import java.util.List;
import java.util.stream.Collectors;

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

        List<JsonNode> result = calculLiniiNomenclatoareService.calculeazaLiniiToDTO(valoriNomenclator, nomenclator.getCampSet(), withLinks, true);

        if(NomenclatorEnum.MASTERAT.getValue().equals(codNomenclator)){
            adaugaMaterie(result);
        }
        if(NomenclatorEnum.MATERIE.getValue().equals(codNomenclator)){
            adaugaProiecte(result);
        }

        return result;
    }

    private void adaugaProiecte(List<JsonNode> result) {
        List<JsonNode> proiecte = cautaNomenclatorByCod(new NomenclatorRequestDTO(NomenclatorEnum.PROIECT.getValue()), true);

        result.forEach((materie ->
                ((ObjectNode) materie)
                        .putArray("proiecte")
                        .addAll(
                                proiecte.stream()
                                        .filter(proiect ->
                                                proiect.get("materie") != null &&
                                                        materie.get("cod") != null &&
                                                        proiect.get("materie").asLong() == materie.get("cod").asLong()
                                        )
                                        .collect(Collectors.toList())
                        ))
        );
    }

    private void adaugaMaterie(List<JsonNode> result) {
        List<JsonNode> materii = cautaNomenclatorByCod(new NomenclatorRequestDTO(NomenclatorEnum.MATERIE.getValue()), true);

        result.forEach((masterat ->
                ((ObjectNode) masterat)
                        .putArray("materii")
                        .addAll(
                                materii.stream()
                                        .filter(m ->
                                                m.get("masterat") != null &&
                                                        masterat.get("cod") != null &&
                                                        m.get("masterat").asLong() == masterat.get("cod").asLong()
                                        )
                                        .collect(Collectors.toList())
                        ))
        );
    }
}
