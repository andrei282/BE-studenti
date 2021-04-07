package ro.tacheandrei.diseratie.administrare.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ro.tacheandrei.diseratie.administrare.domain.Camp;
import ro.tacheandrei.diseratie.administrare.domain.Nomenclator;
import ro.tacheandrei.diseratie.administrare.domain.ValoriNomenclator;
import ro.tacheandrei.diseratie.administrare.repository.NomenclatorRepository;
import ro.tacheandrei.diseratie.administrare.repository.ValoriNomenclatorRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CalculLiniiNomenclatoareService {

    private final ValoriNomenclatorRepository valoriNomenclatorRepository;
    private final NomenclatorRepository nomenclatorRepository;

    public List<JsonNode> calculeazaLiniiToDTO(List<ValoriNomenclator> list, List<Camp> campuri, boolean withLinks, boolean applyChildFullObjects) {
        final ObjectMapper mapper = new ObjectMapper();

        final List<ValoriNomenclator> finalLinks = cautaValoriLinks(campuri, list);
        final Map<Long, List<JsonNode>> groupNomObiect = getChildNoms(applyChildFullObjects, finalLinks);

        final Map<Long, List<ValoriNomenclator>> groupByLinie = list.stream().collect(Collectors.groupingBy(ValoriNomenclator::getIdLinie));
        final List<Map<String, Object>> resFinal = new ArrayList<>();

        groupByLinie.keySet().forEach(e -> {
            Map<String, Object> row = new HashMap<>();
            groupByLinie.get(e).forEach(el -> {
                createRow(row, el, withLinks, finalLinks, applyChildFullObjects, groupNomObiect);
            });
            resFinal.add(row);
        });

        List<Map<String, Object>> resSortat = resFinal;

        return resSortat.stream().map(element -> mapper.convertValue(element, JsonNode.class)).collect(Collectors.toList());
    }

    private List<ValoriNomenclator> cautaValoriLinks(List<Camp> campuri, List<ValoriNomenclator> list) {
        List<Camp> linkedFields = campuri
                .stream()
                .filter((c) -> c.getLink() != null)
                .collect(Collectors.toList());

        List<ValoriNomenclator> links = new ArrayList<>();
        if (!linkedFields.isEmpty()) {

            List<Long> noms = linkedFields.stream()
                    .map((linkedField) -> linkedField.getLink().getId())
                    .collect(Collectors.toList());

            Set<Long> linii =  list.stream()
                    .filter((el) -> (el.getIdLinkedBy() != null && el.getIdLinkedBy() != 0))
                    .map((el) -> Long.parseLong(el.getValoare()))
                    .collect(Collectors.toSet());

            links = valoriNomenclatorRepository.findAllByIdNomenclatorInAndIdLinieIn(noms, linii);
        }
        return links;
    }

    private Map<Long, List<JsonNode>> getChildNoms(boolean applyChildFullObjects, List<ValoriNomenclator> finalLinks) {
        final Map<Long, List<JsonNode>> groupNomObiect = new HashMap<>();
        final Set<Long> childNoms = finalLinks
                .stream()
                .map(ValoriNomenclator::getIdNomenclator)
                .collect(Collectors.toSet());

        if(applyChildFullObjects && !childNoms.isEmpty()){
            List<Nomenclator> nomLinks  = nomenclatorRepository.findByIdIn(childNoms);
            childNoms.forEach(key -> {

                Nomenclator nomenclator = nomLinks
                        .stream()
                        .filter(n -> n.getId() == key)
                        .findFirst()
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));

                List<ValoriNomenclator> linkForSameNom = finalLinks
                        .stream()
                        .filter((l) -> l.getIdNomenclator() == nomenclator.getId())
                        .collect(Collectors.toList());

                List<JsonNode> res = calculeazaLiniiToDTO(linkForSameNom, nomenclator.getCampSet(), true, false);

                groupNomObiect.put(nomenclator.getId(), res);
            });

        }
        return groupNomObiect;
    }

    private void createRow(Map<String, Object> row , ValoriNomenclator el, boolean withLinks, List<ValoriNomenclator> finalLinks,
                           boolean applyChildFullObjects, final Map<Long, List<JsonNode>> groupNomObiect){
        //remap if is a link
        if (withLinks && el.getIdLinkedBy() != null && el.getIdLinkedBy() != 0) {
            row.put(el.getCamp(), this.calculeazaValoareLink(finalLinks, el));

            if(applyChildFullObjects){
                JsonNode value = groupNomObiect.get(el.getIdLinkedBy())
                        .stream()
                        .filter(v -> v.get("id").toString().equals(el.getValoare()))
                        .findFirst()
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));

                row.put( el.getCamp() + "Child", value);
            }
        } else {
            row.put(el.getCamp(), el.getValoare());
        }

        row.put("id", el.getIdLinie());
    }

    private String calculeazaValoareLink(List<ValoriNomenclator> finalLinks, ValoriNomenclator element) {
        List<ValoriNomenclator> linkForLine = finalLinks
                .stream()
                .filter((l) ->
                        l.getIdNomenclator() == element.getIdLinkedBy() &&
                                l.getIdLinie() == Long.parseLong(element.getValoare()))
                .collect(Collectors.toList());

        return linkForLine.stream()
                .filter((l) -> l.getIdCamp().equals(element.getIdLinkedByCamp()))
                .findFirst()
                .map(ValoriNomenclator::getValoare)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Mapari gresite"));

    }
}
