package ro.tacheandrei.diseratie.administrare.controller;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.tacheandrei.diseratie.administrare.service.NomenclatorApiService;
import ro.tacheandrei.disertatie.components.dto.NomenclatorRequestDTO;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/nomenclatoare")
@AllArgsConstructor
public class NomenclatorApiController {

    private final NomenclatorApiService nomenclatorApiService;

    @PostMapping
    public List<JsonNode> cautaNomenclatorByCod(@RequestBody @Valid NomenclatorRequestDTO nomenclatorRequest) {
        return nomenclatorApiService.cautaNomenclatorByCod(nomenclatorRequest, true);
    }
}
