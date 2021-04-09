package ro.tacheandrei.diseratie.gestionarestudenti.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.tacheandrei.diseratie.gestionarestudenti.feign.NomenclatorFeign;
import ro.tacheandrei.diseratie.gestionarestudenti.service.NomenclatorService;
import ro.tacheandrei.disertatie.components.dto.PageDTO;
import ro.tacheandrei.disertatie.components.table.PageRequestDTO;
import ro.tacheandrei.disertatie.components.table.TableListDTO;

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

    @PostMapping("/{proiect}/{cod}")
    public TableListDTO<PageDTO> getGridNomenclatoareByCod(@PathVariable String cod, @PathVariable String proiect, @RequestBody PageRequestDTO pageRequestDTO){
        return nomenclatorFeign.getGridNomenclatoareByCod(proiect, cod, pageRequestDTO);
    }

}
