package ro.tacheandrei.cercetare.gestionarestudenti.gestionarestudentiservice.service;

import org.springframework.stereotype.Service;
import ro.tacheandrei.cercetare.gestionarestudenti.gestionarestudentiservice.dto.ProiectISATMDTO;
import ro.tacheandrei.cercetare.gestionarestudenti.gestionarestudentiservice.entity.ProiectISATM;
import ro.tacheandrei.cercetare.gestionarestudenti.gestionarestudentiservice.repository.ProiectISATMRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProiectISATMService {

    private final ProiectISATMRepository proiectISATMRepository;

    public ProiectISATMService(ProiectISATMRepository proiectISATMRepository) {
        this.proiectISATMRepository = proiectISATMRepository;
    }

    public ProiectISATMDTO saveEchipaISATM(ProiectISATM proiectISATM) {
        return ProiectISATMDTO.from(proiectISATMRepository.save(proiectISATM));
    }

    public List<ProiectISATMDTO> getAllEchipeISTM() {
        return proiectISATMRepository
                .findAll()
                .stream()
                .map(ProiectISATMDTO::from)
                .collect(Collectors.toList());
    }


    public void deleteById(Long id) {
        proiectISATMRepository.deleteById(id);
    }
}
