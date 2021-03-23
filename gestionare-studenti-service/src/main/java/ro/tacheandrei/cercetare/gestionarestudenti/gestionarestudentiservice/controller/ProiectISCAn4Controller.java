package ro.tacheandrei.cercetare.gestionarestudenti.gestionarestudentiservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ro.tacheandrei.cercetare.gestionarestudenti.gestionarestudentiservice.dto.ProiectISCAn4DTO;
import ro.tacheandrei.cercetare.gestionarestudenti.gestionarestudentiservice.entity.ProiectISCAn4;
import ro.tacheandrei.cercetare.gestionarestudenti.gestionarestudentiservice.service.ProiectISCAn4Service;

import java.util.List;

@RestController
@RequestMapping("/proiectISCAn4")
public class ProiectISCAn4Controller {

    private final ProiectISCAn4Service proiectISCAn4Service;

    public ProiectISCAn4Controller(ProiectISCAn4Service proiectISCAn4Service) {
        this.proiectISCAn4Service = proiectISCAn4Service;
    }

    @PostMapping
    public ProiectISCAn4DTO editProiect(@RequestBody ProiectISCAn4DTO proiectISCAn4){
        return proiectISCAn4Service.saveProiect(ProiectISCAn4.from(proiectISCAn4));
    }

    @PutMapping("/{id}")
    public ProiectISCAn4DTO saveProiect(@PathVariable Long id, @RequestBody ProiectISCAn4DTO proiectISCAn4DTO){
        ProiectISCAn4 proiectISCAn4 = ProiectISCAn4.from(proiectISCAn4DTO);
        if(!id.equals(proiectISCAn4.getId())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Nu puteti edita acest proiect");
        }
        return proiectISCAn4Service.saveProiect(proiectISCAn4);
    }

    @GetMapping("/list")
    public List<ProiectISCAn4DTO> getAllProiecteISC(){
        return proiectISCAn4Service.getAllProiecteISC();
    }

    @DeleteMapping("/{id}")
    public void deleteProiectById(@PathVariable Long id){
        proiectISCAn4Service.delete(id);
    }
}
