package ro.tacheandrei.diseratie.administrare.controller;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.tacheandrei.disertatie.components.dto.PageDTO;
import ro.tacheandrei.diseratie.administrare.service.NomenclatoareGridService;
import ro.tacheandrei.diseratie.administrare.service.NomenclatorApiService;
import ro.tacheandrei.disertatie.components.dto.NomenclatorRequestDTO;
import ro.tacheandrei.disertatie.components.table.PageRequestDTO;
import ro.tacheandrei.disertatie.components.table.TableListDTO;

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

    @PostMapping("/{proiect}/{cod}")
    public TableListDTO<PageDTO> cautaValoriNomenclatoare(@PathVariable String cod,
                                                          @PathVariable String proiect,
                                                          @RequestBody PageRequestDTO pageRequestDTO) {
        return nomenclatoareGridService.cautaValoriNomenclatoareByProiect(proiect, cod, pageRequestDTO);
    }
}
