package ro.tacheandrei.diseratie.administrare.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ro.tacheandrei.diseratie.administrare.domain.Camp;
import ro.tacheandrei.diseratie.administrare.domain.Nomenclator;
import ro.tacheandrei.diseratie.administrare.domain.ValoriNomenclator;
import ro.tacheandrei.diseratie.administrare.dto.ValoriNomenclatorDTO;
import ro.tacheandrei.disertatie.components.dto.PageDTO;
import ro.tacheandrei.diseratie.administrare.repository.CampRepository;
import ro.tacheandrei.diseratie.administrare.repository.NomenclatorRepository;
import ro.tacheandrei.diseratie.administrare.repository.ValoriNomenclatorRepository;
import ro.tacheandrei.disertatie.components.filter.FilterType;
import ro.tacheandrei.disertatie.components.filter.GenericSpecification;
import ro.tacheandrei.disertatie.components.filter.SearchCriteria;
import ro.tacheandrei.disertatie.components.filter.SearchOperation;
import ro.tacheandrei.disertatie.components.table.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NomenclatoareGridService {

    private final NomenclatorRepository nomenclatorRepository;
    private final ValoriNomenclatorRepository valoriNomenclatorRepository;
    private final CalculLiniiNomenclatoareService calculLiniiNomenclatoareService;
    private final CampRepository campRepository;

    public TableListDTO<Page<Nomenclator>> cautaNomenclatoare(PageRequestDTO pageRequest) {

        PageRequest defaultPageRequest = PageRequest.of(0, 10, Sort.by("id").descending());
        GenericSpecification<Nomenclator> genericSpecification = new GenericSpecification<>(pageRequest.getFilterModel());

        Page<Nomenclator> pagedResult = nomenclatorRepository.findAll(genericSpecification, defaultPageRequest);

        return new TableListDTO<>(pagedResult, HeaderTableService.calculeazaColoaneTabel(Nomenclator.class));
    }

    public TableListDTO<PageDTO> cautaValoriNomenclatoare(Long idNomenclator, PageRequestDTO pageRequestDTO) {
        Nomenclator nomenclator = nomenclatorRepository.findById(idNomenclator)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Nomenclatorul nu este gasit"));

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage(), 10, Sort.Direction.ASC, "idLinie");

        GenericSpecification<ValoriNomenclator> specification = new GenericSpecification<>(pageRequestDTO.getFilterModel());

        specification.addExtraCriteria(
                Collections.singletonList(
                        new SearchCriteria("idNomenclator", idNomenclator, SearchOperation.EQUAL, FilterType.NUMBER)
                )
        );

        List<ValoriNomenclator> pagedResult = valoriNomenclatorRepository.findAll(specification);

        List<FieldDescriptor> fieldDescriptors = new ArrayList<>();
        nomenclator.getCampSet()
                .forEach((camp) -> {
                    String fieldType = "String";
                    FieldDescriptor fieldDescriptor = new FieldDescriptor(camp.getDenumire(), fieldType, camp.getCod());
                    fieldDescriptor.setFieldType(fieldType);
                    fieldDescriptors.add(fieldDescriptor);
                });

        List<JsonNode> fakeDTO = calculLiniiNomenclatoareService.calculeazaLiniiToDTO(pagedResult, nomenclator.getCampSet(), true, false);

        fakeDTO.sort(Comparator.comparing(lhs -> lhs.get("id").toString(), Comparator.reverseOrder()));

        int firstElement = pageRequestDTO.getPage() * pageRequestDTO.getPageSize();
        int lastElement = (pageRequestDTO.getPage() + 1) * pageRequestDTO.getPageSize();
        if(lastElement > fakeDTO.size()){
            lastElement = fakeDTO.size() % pageRequestDTO.getPageSize() + firstElement;
        }
        fakeDTO = fakeDTO.subList(firstElement, lastElement);
        PageDTO fakePage = new PageDTO(fakeDTO, (long) (pagedResult.size() / nomenclator.getCampSet().size()));

        return new TableListDTO<>(fakePage, fieldDescriptors);
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

    public List<JsonNode> cautaValoriNomenclatoare(Long idNomenclator) {
        List<Camp> campuri = campRepository.findAllByNomenclatorId(idNomenclator);
        List<ValoriNomenclator> valoriNom = valoriNomenclatorRepository.findAllByIdNomenclator(idNomenclator);

        return calculLiniiNomenclatoareService
                .calculeazaLiniiToDTO(valoriNom, campuri, true, false)
                .stream()
                .map(node -> {
                    ((ObjectNode)node).put("id", node.get("id").asText());
                    return node;
                })
                .collect(Collectors.toList());
    }

    public List<JsonNode> cautaValoriNomenclatoareByIdAndLinii(Long idNomenclator, Set<Long> idsLinii) {
        final ObjectMapper mapper = new ObjectMapper();
        List<Camp> campuri = campRepository.findAllByNomenclatorId(idNomenclator);
        List<ValoriNomenclator> valoriNom = valoriNomenclatorRepository.findAllByIdNomenclator(idNomenclator);

        List<JsonNode> nodeList = calculLiniiNomenclatoareService
                .calculeazaLiniiToDTO(valoriNom, campuri, true, false)
                .stream()
                .filter(node -> idsLinii.contains(node.get("id").asLong()))
                .map(node -> {
                    ((ObjectNode)node).put("id", node.get("id").asText());
                    return node;
                })
                .collect(Collectors.toList());

        Set<Long> liniiColoane = valoriNomenclatorRepository
                .findAllByValoareIn(idsLinii.stream().map(String::valueOf).collect(Collectors.toSet()))
                .stream()
                .map(ValoriNomenclator::getIdLinie)
                .collect(Collectors.toSet());

        Map<Long, List<ValoriNomenclator>> groupedByLinie = valoriNomenclatorRepository
                .findAllByIdLinieIn(liniiColoane)
                .stream()
                .collect(Collectors.groupingBy(ValoriNomenclator::getIdLinie));

        nodeList
                .forEach(node -> {
                    Long id = node.get("id").asLong();
                    groupedByLinie.forEach((k, v) -> {
                        if(v.stream().map(ValoriNomenclator::getValoare).collect(Collectors.toList()).contains(String.valueOf(id))){
                            if(node.has("valoriColoane")){
                                v.forEach(lista ->
                                    ((ArrayNode) node.get("valoriColoane")).add(mapper.convertValue(lista, JsonNode.class))
                                );
                            } else{
                                ((ObjectNode)node).put("valoriColoane", mapper.convertValue(v, JsonNode.class));
                            }
                        }
                    });
                });

        return nodeList;
    }


    public TableListDTO<PageDTO> cautaValoriNomenclatoareByCod(String cod, PageRequestDTO pageRequestDTO) {
        Nomenclator nomenclator = nomenclatorRepository.findByCod(cod)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Nomenclatorul nu este gasit"));

        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "idLinie");

        GenericSpecification<ValoriNomenclator> specification = new GenericSpecification<>(pageRequestDTO.getFilterModel());

        specification.addExtraCriteria(
                Collections.singletonList(
                        new SearchCriteria("valoare", cod, SearchOperation.EQUAL, FilterType.NUMBER)
                )
        );

        Page<ValoriNomenclator> pagedResult = valoriNomenclatorRepository.findAll(specification, pageable);

        List<FieldDescriptor> fieldDescriptors = new ArrayList<>();
        nomenclator.getCampSet()
                .forEach((camp) -> {
                    String fieldType = "String";
                    FieldDescriptor fieldDescriptor = new FieldDescriptor(camp.getDenumire(), fieldType, camp.getCod());
                    fieldDescriptor.setFieldType(fieldType);
                    fieldDescriptors.add(fieldDescriptor);
                });

        List<JsonNode> fakeDTO = calculLiniiNomenclatoareService.calculeazaLiniiToDTO(pagedResult.getContent(), nomenclator.getCampSet(), true, false);

        fakeDTO.sort(Comparator.comparing(lhs -> lhs.get("id").toString(), Comparator.reverseOrder()));

        PageDTO fakePage = new PageDTO(fakeDTO, pagedResult.getTotalElements() / nomenclator.getCampSet().size());

        return new TableListDTO<>(fakePage, fieldDescriptors);
    }

    public List<ValoriNomenclatorDTO> cautaValoriNomenclatoareByProiect(String cod) {

        List<ValoriNomenclatorDTO> allColumns = valoriNomenclatorRepository
                .findAllColumns(cod)
                .stream()
                .map(ValoriNomenclatorDTO::from)
                .collect(Collectors.toList());

        ValoriNomenclator valoreProiect = valoriNomenclatorRepository.findByValoareAndCamp(cod, "cod");

        List<Long> liniiProiectCautat = valoriNomenclatorRepository
                .findAllByValoare(String.valueOf(valoreProiect.getIdLinie()))
                .stream()
                .map(ValoriNomenclator::getIdLinie)
                .collect(Collectors.toList());

        return allColumns
                .stream()
                .filter(vn -> liniiProiectCautat.contains(vn.getIdLinie()))
                .collect(Collectors.toList());
    }
}
