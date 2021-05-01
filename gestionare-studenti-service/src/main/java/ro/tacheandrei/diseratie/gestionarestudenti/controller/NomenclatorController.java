package ro.tacheandrei.diseratie.gestionarestudenti.controller;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.tacheandrei.diseratie.gestionarestudenti.dto.ValoriNomenclatorDTO;
import ro.tacheandrei.diseratie.gestionarestudenti.feign.NomenclatorFeign;
import ro.tacheandrei.diseratie.gestionarestudenti.service.NomenclatorService;
import ro.tacheandrei.disertatie.components.dto.NomenclatorRequestDTO;
import ro.tacheandrei.disertatie.components.dto.PageDTO;
import ro.tacheandrei.disertatie.components.table.PageRequestDTO;
import ro.tacheandrei.disertatie.components.table.TableListDTO;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/nomenclatoare")
@AllArgsConstructor
public class NomenclatorController {

    private final NomenclatorService nomenclatorService;
    private final NomenclatorFeign nomenclatorFeign;

    @GetMapping("/{cod}")
    public <T> List<T> getNomenclatorByCod(@PathVariable String cod){
        return nomenclatorService.getNomenclatorByCod(cod);
    }

    @GetMapping("/coloane/{cod}")
    public List<ValoriNomenclatorDTO> getGridNomenclatoareByCod(@PathVariable String cod){
        return nomenclatorFeign.getGridNomenclatoareByCod(cod);
    }

    @PostMapping("/id")
    public JsonNode cautaNomenclatorByCodAndId(@RequestBody @Valid NomenclatorRequestDTO nomenclatorRequest) {
        return nomenclatorFeign.cautaNomenclatorByCodAndId(nomenclatorRequest);
    }

    @PostMapping("/linii")
    public Map<Long, JsonNode> cautaNomenclatorByCodAndId(@RequestBody List<Long> idsLinii) {
        return nomenclatorFeign.cautaLinii(idsLinii);
    }

    @PostMapping("/detalii")
    public JsonNode cautaDetaliiNomenclatorByCod(@RequestBody @Valid NomenclatorRequestDTO nomenclatorRequest) {
        return nomenclatorFeign.cautaDetaliiNomenclatorByCod(nomenclatorRequest);
    }

    @GetMapping("/campuri/{idNomenclator}")
    public List<JsonNode> cautaCampuriByNomenclatorId(@PathVariable Long idNomenclator) {
        return nomenclatorFeign.cautaCampuriByNomenclatorId(idNomenclator);
    }

    @PostMapping("/byId/{idNomenclator}")
    public List<JsonNode> cautaValoriNomenclatoare(@PathVariable Long idNomenclator, @RequestBody Set<Long> ids) {
        return nomenclatorFeign.cautaValoriNomenclatoare(idNomenclator, ids);
    }

    @PostMapping("/save-linii")
    public List<JsonNode> salveazaLinii(@RequestBody List<Map<String, Object>> linii){
        return nomenclatorFeign.salveazaLinii(linii);
    }

}
