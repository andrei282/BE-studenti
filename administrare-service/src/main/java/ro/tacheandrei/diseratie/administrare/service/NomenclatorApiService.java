package ro.tacheandrei.diseratie.administrare.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ro.tacheandrei.diseratie.administrare.domain.Camp;
import ro.tacheandrei.diseratie.administrare.domain.Linie;
import ro.tacheandrei.diseratie.administrare.domain.Nomenclator;
import ro.tacheandrei.diseratie.administrare.domain.ValoriNomenclator;
import ro.tacheandrei.diseratie.administrare.dto.CampDTO;
import ro.tacheandrei.diseratie.administrare.dto.LinieDTO;
import ro.tacheandrei.diseratie.administrare.dto.NomenclatorDTO;
import ro.tacheandrei.diseratie.administrare.repository.CampRepository;
import ro.tacheandrei.diseratie.administrare.repository.LinieRepository;
import ro.tacheandrei.diseratie.administrare.repository.NomenclatorRepository;
import ro.tacheandrei.diseratie.administrare.repository.ValoriNomenclatorRepository;
import ro.tacheandrei.disertatie.components.dto.NomenclatorRequestDTO;
import ro.tacheandrei.disertatie.components.enums.NomenclatorEnum;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NomenclatorApiService {

    private final NomenclatorRepository nomenclatorRepository;
    private final ValoriNomenclatorRepository valoriNomenclatorRepository;
    private final CalculLiniiNomenclatoareService calculLiniiNomenclatoareService;
    private final LinieRepository linieRepository;
    private final CampRepository campRepository;
    private final NomenclatorService nomenclatorService;

    public List<JsonNode> cautaNomenclatorByCod(NomenclatorRequestDTO nomenclatorRequestDTO, boolean withLinks) {
        final String codNomenclator = nomenclatorRequestDTO.getCod();
        final Nomenclator nomenclator = nomenclatorRepository.findByCod(codNomenclator)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Nomenclatorul nu este gasit"));

        List<ValoriNomenclator> valoriNomenclator = valoriNomenclatorRepository.findAllByCod(nomenclator.getCod());

        List<JsonNode> result = calculLiniiNomenclatoareService.calculeazaLiniiToDTO(valoriNomenclator, nomenclator.getCampSet(), withLinks, true);

        if (NomenclatorEnum.MASTERAT.getValue().equals(codNomenclator)) {
            adaugaMaterie(result);
        }
        if (NomenclatorEnum.MATERIE.getValue().equals(codNomenclator)) {
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
                                        .filter(proiect -> proiect.get("materie") != null &&
                                                materie.get("cod") != null &&
                                                proiect.get("materie").asText() == materie.get("cod").asText()
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
                                        .filter(m -> m.get("masterat") != null &&
                                                masterat.get("cod") != null &&
                                                m.get("masterat").asText().equals(masterat.get("cod").asText())
                                        )
                                        .collect(Collectors.toList())
                        ))
        );
    }

    public JsonNode cautaNomenclatorByCodAndId(NomenclatorRequestDTO nomenclatorRequest) {

        if (nomenclatorRequest.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id este required");
        }

        List<JsonNode> jsonNodes = cautaNomenclatorByCod(nomenclatorRequest, true);

        return jsonNodes
                .stream()
                .filter(n -> n.get("id") != null && n.get("id").asLong() == nomenclatorRequest.getId())
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "nomenclatorul cu id: " + nomenclatorRequest.getId() + " nu a fost gasit"));
    }

    public Map<Long, LinieDTO> cautaLiniiByIds(List<Long> idsLinii) {
        return linieRepository
                .findAllByIdIn(idsLinii)
                .stream()
                .map(l -> new LinieDTO().fromEntity(l))
                .collect(Collectors.toMap(LinieDTO::getId, Function.identity()));
    }

    public NomenclatorDTO cautaDetaliiNomenclatorByCod(NomenclatorRequestDTO nomenclatorRequest) {
        Nomenclator nomenclator = nomenclatorRepository.findByCod(nomenclatorRequest.getCod())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.GONE, "Nomenclatorul cu codul solicitat nu exista in baza de date"));

        return NomenclatorDTO.from(nomenclator);
    }

    public List<CampDTO> cautaCampuriByNomenclatorId(Long idNomenclator) {

        List<Camp> campuriEntitate = campRepository.findAllByNomenclatorId(idNomenclator);

        return campuriEntitate.stream()
                .map(c -> new CampDTO().fromEntity(c))
                .sorted(Comparator.comparing(CampDTO::getId))
                .collect(Collectors.toList());
    }

    public List<LinieDTO> salveazaLinii(List<Linie> linii) {
        linii.forEach(
                l -> {
                    String denumire = l.getValoareSet()
                            .stream()
                            .filter(vs -> vs.getCamp().getCod().equals("denumire"))
                            .findFirst()
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "Nu au fost completate toate campurile obligatorii"))
                            .getValoare();
                    l.getValoareSet().forEach(vs -> {
                        if (vs.getCamp().getCod().equals("cod")) {
                            vs.setValoare(denumire.toLowerCase().replace(" ", "_"));
                        }
                    });
                });
        List<LinieDTO> result = new ArrayList<>();
        linii.forEach(l -> result.add(nomenclatorService.adaugaLinieSiValori(l)));
        return result;
    }
}
