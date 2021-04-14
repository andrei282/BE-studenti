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

}
