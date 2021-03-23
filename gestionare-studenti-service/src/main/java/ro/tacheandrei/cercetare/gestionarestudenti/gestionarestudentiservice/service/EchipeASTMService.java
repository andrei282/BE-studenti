package ro.tacheandrei.cercetare.gestionarestudenti.gestionarestudentiservice.service;

import org.springframework.stereotype.Service;
import ro.tacheandrei.cercetare.gestionarestudenti.gestionarestudentiservice.dto.EchipeASTMDTO;
import ro.tacheandrei.cercetare.gestionarestudenti.gestionarestudentiservice.entity.EchipeASTM;
import ro.tacheandrei.cercetare.gestionarestudenti.gestionarestudentiservice.repository.EchipeASTMRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EchipeASTMService {

    private final EchipeASTMRepository echipeASTMRepository;

    public EchipeASTMService(EchipeASTMRepository echipeASTMRepository) {
        this.echipeASTMRepository = echipeASTMRepository;
    }

    public EchipeASTMDTO saveEchipaASTM(EchipeASTM echipeASTM) {
        return EchipeASTMDTO.from(echipeASTMRepository.save(echipeASTM));
    }

    public List<EchipeASTMDTO> getAllEchipeASTM() {
        return echipeASTMRepository
                .findAll()
                .stream()
                .map(EchipeASTMDTO::from)
                .collect(Collectors.toList());
    }


    public void deteleById(Long id) {
        echipeASTMRepository.deleteById(id);
    }
}
