package ro.tacheandrei.diseratie.administrare.controller;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.tacheandrei.diseratie.administrare.domain.Linie;
import ro.tacheandrei.diseratie.administrare.dto.CampDTO;
import ro.tacheandrei.diseratie.administrare.dto.LinieDTO;
import ro.tacheandrei.diseratie.administrare.dto.NomenclatorDTO;
import ro.tacheandrei.diseratie.administrare.dto.ValoriNomenclatorDTO;
import ro.tacheandrei.diseratie.administrare.service.NomenclatoareGridService;
import ro.tacheandrei.diseratie.administrare.service.NomenclatorApiService;
import ro.tacheandrei.disertatie.components.dto.NomenclatorRequestDTO;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    @PostMapping("/detalii")
    public NomenclatorDTO cautaDetaliiNomenclatorByCod(@RequestBody @Valid NomenclatorRequestDTO nomenclatorRequest) {
        return nomenclatorApiService.cautaDetaliiNomenclatorByCod(nomenclatorRequest);
    }

    @GetMapping("/coloane/{cod}")
    public List<ValoriNomenclatorDTO> cautaValoriNomenclatoare(@PathVariable String cod) {
        return nomenclatoareGridService.cautaValoriNomenclatoareByProiect(cod);
    }

    @PostMapping("/id")
    public JsonNode cautaNomenclatorByCodAndId(@RequestBody @Valid NomenclatorRequestDTO nomenclatorRequest) {
        return nomenclatorApiService.cautaNomenclatorByCodAndId(nomenclatorRequest);
    }

    @PostMapping("/linii")
    public Map<Long, LinieDTO> cautaLinieByIdLinie(@RequestBody List<Long> idsLinii) {
        return nomenclatorApiService.cautaLiniiByIds(idsLinii);
    }

    @GetMapping("/campuri/{idNomenclator}")
    public List<CampDTO> cautaCampuriByNomenclatorId(@PathVariable Long idNomenclator) {
        return nomenclatorApiService.cautaCampuriByNomenclatorId(idNomenclator);
    }

    @GetMapping("/{idNomenclator}")
    public List<JsonNode> cautaValoriNomenclatoare(@PathVariable Long idNomenclator) {
        return nomenclatoareGridService.cautaValoriNomenclatoare(idNomenclator);
    }

    @PostMapping("/{idNomenclator}")
    public List<JsonNode> cautaValoriNomenclatoare(@PathVariable Long idNomenclator, @RequestBody Set<Long> idsLinii) {
        return nomenclatoareGridService.cautaValoriNomenclatoareByIdAndLinii(idNomenclator, idsLinii);
    }

    @PostMapping("/save-linii")
    public List<LinieDTO> salveazaLinii(@RequestBody List<Linie> linii){
        return nomenclatorApiService.salveazaLinii(linii);
    }
}
