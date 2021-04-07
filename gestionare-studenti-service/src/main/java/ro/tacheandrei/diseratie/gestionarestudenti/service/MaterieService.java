package ro.tacheandrei.diseratie.gestionarestudenti.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ro.tacheandrei.diseratie.gestionarestudenti.dto.MaterieDTO;
import ro.tacheandrei.diseratie.gestionarestudenti.entity.Materie;
import ro.tacheandrei.diseratie.gestionarestudenti.repository.MaterieRepository;
import ro.tacheandrei.diseratie.gestionarestudenti.table.HeaderTableService;
import ro.tacheandrei.diseratie.gestionarestudenti.table.PageRequestDTO;
import ro.tacheandrei.diseratie.gestionarestudenti.table.TableListDTO;

@Service
@AllArgsConstructor
public class MaterieService {

    private final MaterieRepository materieRepository;

    public TableListDTO<Page<MaterieDTO>> getAll(PageRequestDTO pageRequestDTO){
        PageRequest defaultPageRequest = PageRequest.of(0, 10, Sort.by("id").descending());

        Page<MaterieDTO> pageMastere = materieRepository
                .findAll(defaultPageRequest)
                .map(MaterieDTO::from);

        return new TableListDTO<>(pageMastere, HeaderTableService.calculeazaColoaneTabel(MaterieDTO.class));
    }

    public MaterieDTO salveaza(Materie materie) {
        return MaterieDTO.from(materieRepository.save(materie));
    }

    public MaterieDTO editeaza(Long id, Materie materie) {
        if(!materie.getId().equals(id)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Id-ul nu coincide cu cel ce se doreste a fi editat.");
        }
        return salveaza(materie);
    }

    public void sterge(Long id) {
        materieRepository.deleteById(id);
    }
}
