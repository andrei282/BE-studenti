package ro.tacheandrei.diseratie.gestionarestudenti.feign;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ro.tacheandrei.diseratie.gestionarestudenti.dto.ValoriNomenclatorDTO;
import ro.tacheandrei.disertatie.components.dto.NomenclatorRequestDTO;

import java.util.List;
import java.util.Map;

@FeignClient(name = "administrare-service")
public interface NomenclatorFeign {

    @PostMapping("/api/nomenclatoare")
    List<Map<String, Object>> getNomenclatorByCod(@RequestBody NomenclatorRequestDTO nomenclatorRequestDTO);

    @GetMapping("/api/nomenclatoare/coloane/{cod}")
    List<ValoriNomenclatorDTO> getGridNomenclatoareByCod(@PathVariable  String cod);

    @PostMapping("/api/nomenclatoare/id")
    JsonNode cautaNomenclatorByCodAndId(NomenclatorRequestDTO nomenclatorRequest);
}
