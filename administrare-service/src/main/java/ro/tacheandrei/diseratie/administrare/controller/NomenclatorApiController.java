package ro.tacheandrei.diseratie.administrare.controller;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.tacheandrei.diseratie.administrare.dto.ValoriNomenclatorDTO;
import ro.tacheandrei.diseratie.administrare.service.NomenclatoareGridService;
import ro.tacheandrei.diseratie.administrare.service.NomenclatorApiService;
import ro.tacheandrei.disertatie.components.dto.NomenclatorRequestDTO;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/nomenclatoare")
@AllArgsConstructor
public class NomenclatorApiController {

    private final NomenclatorApiService nomenclatorApiService;
    private final NomenclatoareGridService nomenclatoareGridService;

    @PostMapping
    public List<JsonNode> cautaNomenclatorByCod(@RequestBody @Valid NomenclatorRequestDTO nomenclatorRequest) {
        return nomenclatorApiService.cautaNomenclatorByCod(nomenclatorRequest, true);
    }

    @GetMapping("/coloane/{cod}")
    public List<ValoriNomenclatorDTO> cautaValoriNomenclatoare(@PathVariable String cod) {
        return nomenclatoareGridService.cautaValoriNomenclatoareByProiect(cod);
    }

    @PostMapping("/id")
    public JsonNode cautaNomenclatorByCodAndId(@RequestBody @Valid NomenclatorRequestDTO nomenclatorRequest) {
        return nomenclatorApiService.cautaNomenclatorByCodAndId(nomenclatorRequest);
    }
}
