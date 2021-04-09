package ro.tacheandrei.diseratie.gestionarestudenti.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ro.tacheandrei.diseratie.gestionarestudenti.feign.NomenclatorFeign;
import ro.tacheandrei.disertatie.components.dto.BaseNomDTO;
import ro.tacheandrei.disertatie.components.dto.MasteratDTO;
import ro.tacheandrei.disertatie.components.dto.NomenclatorRequestDTO;
import ro.tacheandrei.disertatie.components.enums.NomenclatorEnum;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.databind.DeserializationFeature.READ_ENUMS_USING_TO_STRING;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_ENUMS_USING_TO_STRING;

@Service
public class NomenclatorService {

    private final NomenclatorFeign nomenclatorFeign;
    private final ObjectMapper mapper;

    public NomenclatorService(NomenclatorFeign nomenclatorFeign,
                                    ObjectMapper mapper) {
        this.nomenclatorFeign = nomenclatorFeign;
        this.mapper = mapper
                .configure(WRITE_ENUMS_USING_TO_STRING, true)
                .configure(READ_ENUMS_USING_TO_STRING, true);
    }

    public <T> List<T> getNomenclatorByCod(String cod) {
        NomenclatorRequestDTO nomenclatorRequestDTO = new NomenclatorRequestDTO(cod);
        try {
            List<Map<String, Object>> mapList =  nomenclatorFeign.getNomenclatorByCod(nomenclatorRequestDTO);
            if(NomenclatorEnum.MASTERAT.getValue().equals(cod)) {
                return (List<T>) mapList.stream().map(m -> mapper.convertValue(m, MasteratDTO.class)).collect(Collectors.toList());
            } else{
                return (List<T>) mapList.stream().map(m -> mapper.convertValue(m, BaseNomDTO.class)).collect(Collectors.toList());
            }
        } catch (FeignException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Eroare comunicare server nomenclatoare", ex.getCause());
        }
    }
}
