package ro.tacheandrei.cercetare.gestionarestudenti.gestionarestudentiservice.controller;

import org.springframework.web.bind.annotation.*;
import ro.tacheandrei.cercetare.gestionarestudenti.gestionarestudentiservice.dto.EchipeASTMDTO;
import ro.tacheandrei.cercetare.gestionarestudenti.gestionarestudentiservice.entity.EchipeASTM;
import ro.tacheandrei.cercetare.gestionarestudenti.gestionarestudentiservice.service.EchipeASTMService;

import java.util.List;

@RestController
@RequestMapping("/echipeASTM")
public class EchipeASTMController {

    private final EchipeASTMService echipeASTMService;

    public EchipeASTMController(EchipeASTMService echipeASTMService) {
        this.echipeASTMService = echipeASTMService;
    }

    @PostMapping
    public EchipeASTMDTO saveProiect(@RequestBody EchipeASTM echipeASTM){
        return echipeASTMService.saveEchipaASTM(echipeASTM);
    }

    @GetMapping("/list")
    public List<EchipeASTMDTO> getAllProiecteISC(){
        return echipeASTMService.getAllEchipeASTM();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        echipeASTMService.deteleById(id);
    }
}
