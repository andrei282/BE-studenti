package ro.tacheandrei.diseratie.gestionarestudenti.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProiectDTO {

    private Long id;
    private String cod;
    private String denumire;
    private List<CampDTO> campuri;
}
