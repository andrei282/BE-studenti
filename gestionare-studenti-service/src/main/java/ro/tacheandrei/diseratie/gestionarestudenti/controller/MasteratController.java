package ro.tacheandrei.diseratie.gestionarestudenti.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ro.tacheandrei.diseratie.gestionarestudenti.dto.MasteratDTO;
import ro.tacheandrei.diseratie.gestionarestudenti.entity.Masterat;
import ro.tacheandrei.diseratie.gestionarestudenti.service.MasteratService;
import ro.tacheandrei.diseratie.gestionarestudenti.table.PageRequestDTO;
import ro.tacheandrei.diseratie.gestionarestudenti.table.TableListDTO;

import java.util.List;

@RestController
@RequestMapping("/master")
@AllArgsConstructor
public class MasteratController {

    private final MasteratService masteratService;

    @PostMapping("/grid")
    public TableListDTO<Page<MasteratDTO>> getGrid(@RequestBody PageRequestDTO pageRequestDTO){
        return masteratService.getAll(pageRequestDTO);
    }

    @PostMapping
    public MasteratDTO salveazaMasterat(@RequestBody Masterat masterat){
        return masteratService.salveaza(masterat);
    }

    @PutMapping("/{id}")
    public MasteratDTO editeazaMasterat(@PathVariable Long id, @RequestBody Masterat masterat){
        return masteratService.editeaza(id, masterat);
    }

    @DeleteMapping("/{id}")
    public void editeazaMasterat(@PathVariable Long id){
        masteratService.sterge(id);
    }

    @GetMapping("/list")
    public List<MasteratDTO> listaMasterate(){
        return masteratService.getListaMasterate();
    }
}
