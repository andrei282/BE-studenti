package ro.tacheandrei.cercetare.gestionarestudenti.gestionarestudentiservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tacheandrei.cercetare.gestionarestudenti.gestionarestudentiservice.dto.ProiectISCAn4DTO;
import ro.tacheandrei.cercetare.gestionarestudenti.gestionarestudentiservice.entity.ProiectISCAn4;
import ro.tacheandrei.cercetare.gestionarestudenti.gestionarestudentiservice.repository.ProiectISCAn4Repository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProiectISCAn4Service {

    private final ProiectISCAn4Repository proiectISCAn4Repository;

    public ProiectISCAn4Service(ProiectISCAn4Repository proiectISCAn4Repository) {
        this.proiectISCAn4Repository = proiectISCAn4Repository;
    }

    public ProiectISCAn4DTO saveProiect(ProiectISCAn4 proiectISCAn4) {
        return ProiectISCAn4DTO.from(proiectISCAn4Repository.save(proiectISCAn4));
    }

    public List<ProiectISCAn4DTO> getAllProiecteISC() {
        return proiectISCAn4Repository
                .findAll()
                .stream()
                .map(ProiectISCAn4DTO::from)
                .collect(Collectors.toList());
    }


    public void delete(Long id) {
        proiectISCAn4Repository.deleteById(id);
    }
}
