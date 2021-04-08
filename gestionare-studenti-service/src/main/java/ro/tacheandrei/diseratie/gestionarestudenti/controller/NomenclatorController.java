package ro.tacheandrei.diseratie.gestionarestudenti.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.tacheandrei.diseratie.gestionarestudenti.feign.NomenclatorFeign;
import ro.tacheandrei.disertatie.components.dto.NomenclatorRequestDTO;

import java.util.List;

@RestController
@RequestMapping("/nomenclatoare")
@AllArgsConstructor
public class NomenclatorController {

    private final NomenclatorFeign nomenclatorFeign;

    @PostMapping
    public List<Object> getNomenclatorByCod(@RequestBody NomenclatorRequestDTO nomenclatorRequestDTO){
        return nomenclatorFeign.getNomenclatorByCod(nomenclatorRequestDTO);
    }
}
