package ro.tacheandrei.diseratie.gestionarestudenti.dto;

import lombok.Data;

@Data
public class CampDTO {

    private Long id;
    private String cod;
    private String denumire;
    private ProiectDTO proiect;
}
