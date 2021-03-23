package ro.tacheandrei.cercetare.gestionarestudenti.gestionarestudentiservice.controller;

import org.springframework.web.bind.annotation.*;
import ro.tacheandrei.cercetare.gestionarestudenti.gestionarestudentiservice.dto.ProiectISATMDTO;
import ro.tacheandrei.cercetare.gestionarestudenti.gestionarestudentiservice.entity.ProiectISATM;
import ro.tacheandrei.cercetare.gestionarestudenti.gestionarestudentiservice.service.ProiectISATMService;

import java.util.List;

@RestController
@RequestMapping("/proiectISATM")
public class ProiectISATMController {

    private final ProiectISATMService proiectISATMService;

    public ProiectISATMController(ProiectISATMService proiectISATMService) {
        this.proiectISATMService = proiectISATMService;
    }

    @PostMapping
    public ProiectISATMDTO saveProiect(@RequestBody ProiectISATM proiectISATM){
        return proiectISATMService.saveEchipaISATM(proiectISATM);
    }

    @GetMapping("/list")
    public List<ProiectISATMDTO> getAllProiecteISC(){
        return proiectISATMService.getAllEchipeISTM();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        proiectISATMService.deleteById(id);
    }
}
