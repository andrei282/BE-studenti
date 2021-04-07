package ro.tacheandrei.diseratie.administrare.controller;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ro.tacheandrei.diseratie.administrare.domain.Linie;
import ro.tacheandrei.diseratie.administrare.domain.Nomenclator;
import ro.tacheandrei.diseratie.administrare.dto.CampDTO;
import ro.tacheandrei.diseratie.administrare.dto.LinieDTO;
import ro.tacheandrei.diseratie.administrare.dto.NomenclatorDTO;
import ro.tacheandrei.diseratie.administrare.dto.PageDTO;
import ro.tacheandrei.diseratie.administrare.service.NomenclatoareGridService;
import ro.tacheandrei.diseratie.administrare.service.NomenclatorService;
import ro.tacheandrei.disertatie.components.table.PageRequestDTO;
import ro.tacheandrei.disertatie.components.table.TableListDTO;

import java.util.List;

@RestController
@RequestMapping("/nomenclatoare")
@AllArgsConstructor
public class NomenclatorController {

    private final NomenclatorService nomenclatorService;
//    private final NomenclatorApiService nomenclatorApiService;
    private final NomenclatoareGridService nomenclatoareGridService;

    @PostMapping("/linie")
    public LinieDTO adaugaLinieSiValori(@RequestBody LinieDTO linieDTO) {
        Linie linie = Linie.from(linieDTO);
        return nomenclatorService.adaugaLinieSiValori(linie);
    }

    @PostMapping
    public TableListDTO<Page<Nomenclator>> cautaNomenclatoare(@RequestBody PageRequestDTO pageRequestDTO) {
        return nomenclatoareGridService.cautaNomenclatoare(pageRequestDTO);
    }

    @PostMapping("/{idNomenclator}")
    public TableListDTO<PageDTO> cautaValoriNomenclatoare(@PathVariable Long idNomenclator,
                                                          @RequestBody PageRequestDTO pageRequestDTO) {
        return nomenclatoareGridService.cautaValoriNomenclatoare(idNomenclator, pageRequestDTO);
    }

    @GetMapping("/campuri/{idNomenclator}")
    public List<CampDTO> cautaCampuriByNomenclatorId(@PathVariable Long idNomenclator) {
        return nomenclatorService.cautaCampuriByNomenclatorId(idNomenclator);
    }

    @GetMapping("/{idNomenclator}")
    public List<JsonNode> cautaValoriNomenclatoare(@PathVariable Long idNomenclator) {
        return nomenclatoareGridService.cautaValoriNomenclatoare(idNomenclator);
    }

    @GetMapping("/linie/{idLinie}")
    public LinieDTO cautaLinieByIdLinie(@PathVariable Long idLinie) {
        return nomenclatorService.cautaLinieByIdLinie(idLinie);
    }

    @DeleteMapping("/linie/{idLinie}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLinie(@PathVariable Long idLinie) {
        nomenclatorService.deleteLinie(idLinie);
    }

    @GetMapping("/coduri/{idNomenclator}")
    public List<String> cautaCoduriNomenclatoare(@PathVariable Long idNomenclator) {
        return nomenclatorService.cautaCoduriNomenclatoare(idNomenclator);
    }

    @GetMapping("/detalii/{idNomenclator}")
    public NomenclatorDTO getNomenclator(@PathVariable Long idNomenclator) {
        return nomenclatorService.getNomenclator(idNomenclator);
    }
}
