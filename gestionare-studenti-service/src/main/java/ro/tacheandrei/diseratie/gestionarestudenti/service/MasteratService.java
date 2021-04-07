package ro.tacheandrei.diseratie.gestionarestudenti.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ro.tacheandrei.diseratie.gestionarestudenti.dto.MasteratDTO;
import ro.tacheandrei.diseratie.gestionarestudenti.entity.Masterat;
import ro.tacheandrei.diseratie.gestionarestudenti.repository.MasteratRepository;
import ro.tacheandrei.diseratie.gestionarestudenti.table.HeaderTableService;
import ro.tacheandrei.diseratie.gestionarestudenti.table.PageRequestDTO;
import ro.tacheandrei.diseratie.gestionarestudenti.table.TableListDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MasteratService {

    private final MasteratRepository masteratRepository;

    public TableListDTO<Page<MasteratDTO>> getAll(PageRequestDTO pageRequestDTO){
        PageRequest defaultPageRequest = PageRequest.of(0, 10, Sort.by("id").descending());

        Page<MasteratDTO> pageMastere = masteratRepository
                .findAll(defaultPageRequest)
                .map(MasteratDTO::from);

        return new TableListDTO<>(pageMastere, HeaderTableService.calculeazaColoaneTabel(MasteratDTO.class));
    }

    public MasteratDTO salveaza(Masterat masterat) {
        return MasteratDTO.from(masteratRepository.save(masterat));
    }

    public MasteratDTO editeaza(Long id, Masterat masterat) {
        if(!masterat.getId().equals(id)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Id-ul nu coincide cu cel ce se doreste a fi editat.");
        }
        return salveaza(masterat);
    }

    public void sterge(Long id) {
        masteratRepository.deleteById(id);
    }

    public List<MasteratDTO> getListaMasterate() {
        return masteratRepository
                .findAll()
                .stream()
                .map(MasteratDTO::from)
                .collect(Collectors.toList());
    }
}
