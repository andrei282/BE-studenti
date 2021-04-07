package ro.tacheandrei.diseratie.gestionarestudenti.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ro.tacheandrei.diseratie.gestionarestudenti.dto.MaterieDTO;
import ro.tacheandrei.diseratie.gestionarestudenti.entity.Materie;
import ro.tacheandrei.diseratie.gestionarestudenti.service.MaterieService;
import ro.tacheandrei.diseratie.gestionarestudenti.table.PageRequestDTO;
import ro.tacheandrei.diseratie.gestionarestudenti.table.TableListDTO;

@RestController
@RequestMapping("/materie")
@AllArgsConstructor
public class MaterieController {

    private final MaterieService materieService;

    @PostMapping("/grid")
    public TableListDTO<Page<MaterieDTO>> getGrid(@RequestBody PageRequestDTO pageRequestDTO){
        return materieService.getAll(pageRequestDTO);
    }

    @PostMapping
    public MaterieDTO salveazaMasterat(@RequestBody Materie materie){
        return materieService.salveaza(materie);
    }

    @PutMapping("/{id}")
    public MaterieDTO editeazaMasterat(@PathVariable Long id, @RequestBody Materie materie){
        return materieService.editeaza(id, materie);
    }

    @DeleteMapping("/{id}")
    public void editeazaMasterat(@PathVariable Long id){
        materieService.sterge(id);
    }
}
