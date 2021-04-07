package ro.tacheandrei.diseratie.administrare.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;
import ro.tacheandrei.diseratie.administrare.domain.*;
import ro.tacheandrei.diseratie.administrare.dto.CampDTO;
import ro.tacheandrei.diseratie.administrare.dto.LinieDTO;
import ro.tacheandrei.diseratie.administrare.dto.NomenclatorDTO;
import ro.tacheandrei.diseratie.administrare.repository.*;

import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@AllArgsConstructor
public class NomenclatorService {
    static final String CODUL_CAMPULUI_COD = "cod";
    static final String TIP_CAMP_DATE = "date";

    private final NomenclatorRepository nomenclatorRepository;
    private final CampRepository campRepository;
    private final LinieRepository linieRepository;
    private final ValoareRepository valoareRepository;
    private final ValoriNomenclatorRepository valoriNomenclatorRepository;

    public LinieDTO adaugaLinieSiValori(Linie linie) {

        if (linie.getNomenclator() == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Datele trimise nu sunt corespunzatoare");
        }

        Nomenclator nomenclator = nomenclatorRepository.findById(linie.getNomenclator().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.GONE, "Nomenclatorul cu id-ul solicitat nu exista in baza de date"));

        nomenclator.getCampSet().forEach(cDef -> {
            long count = getCount(linie, cDef);
            if (count == 0) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Nomenclatorul nu este salvat corespunzator");
            }
        });

        checkDateValues(linie);

        Linie res = linieRepository.save(linie);

        return new LinieDTO().fromEntity(res);
    }

    private void checkDateValues(Linie linie) {
        if (!CollectionUtils.isEmpty(linie.getValoareSet())) {
            linie.getValoareSet().stream()
                    .filter(valoare -> TIP_CAMP_DATE.equalsIgnoreCase(valoare.getCamp().getTipCamp()))
                    .forEach(valoare -> {
                        try {
                            ZonedDateTime.parse(valoare.getValoare());
                        } catch (DateTimeParseException e) {
                            throw new ResponseStatusException(BAD_REQUEST, "Valoarea de tip data nu este in formatul corespunzator.");
                        }
                    });
        }
    }

    public List<CampDTO> cautaCampuriByNomenclatorId(Long idNomenclator) {

        List<Camp> campuriEntitate = campRepository.findAllByNomenclatorId(idNomenclator);

        return campuriEntitate.stream()
                .map(c -> new CampDTO().fromEntity(c))
                .sorted(Comparator.comparing(CampDTO::getId))
                .collect(Collectors.toList());
    }

    public LinieDTO cautaLinieByIdLinie(Long idLinie) {
        Linie linie = linieRepository.findById(idLinie)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nomenclatorul nu pot fi adus"));

        return  new LinieDTO().fromEntity(linie);

    }

    public void deleteLinie(Long idLinie) {

        Linie linie = linieRepository.findById(idLinie)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nomenclatorul nu pot fi adus"));


        ValoriNomenclator existaDependinta = valoriNomenclatorRepository.findByValoareAndIdLinkedBy(String.valueOf(linie.getId()), linie.getNomenclator().getId());

        if (existaDependinta != null) {
            Camp camp = campRepository
                    .findById(existaDependinta.getIdCamp())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.GONE, "Nomenclatorul nu pot fi gasit"));

            throw new ResponseStatusException(HttpStatus.CONFLICT, "Nomenclatorul " + camp.getNomenclator().getDenumire() + " este folosit in alt nomenclator");
        } else {
            linieRepository.deleteById(idLinie);
        }
    }

    public List<String> cautaCoduriNomenclatoare(Long idNomenclator) {

        List<Valoare> valoriCodList = valoareRepository.findAllByCampCodAndCampNomenclatorId(
                CODUL_CAMPULUI_COD, idNomenclator);

        return valoriCodList.stream()
                .map(Valoare::getValoare)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    private long getCount(Linie linie, Camp cDef) {
        return linie.getValoareSet()
                .stream()
                .filter(valTrimis -> valTrimis.getCamp().getId().equals(cDef.getId()))
                .count();
    }

    public NomenclatorDTO getNomenclator(Long idNomenclator) {
        Nomenclator nomenclator = nomenclatorRepository.findById(idNomenclator)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.GONE, "Nomenclatorul cu id-ul solicitat nu exista in baza de date"));

        return NomenclatorDTO.from(nomenclator);
    }

}
