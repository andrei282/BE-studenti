package ro.tacheandrei.diseratie.gestionarestudenti.dto;

import lombok.Data;

@Data
public class ValoriNomenclatorDTO {

    private long id;

    private Long idNomenclator;

    private String cod;

    private Long idCamp;

    private String camp;

    private String tipCamp;

    private String denumireCamp;

    private String valoare;

    private Long idLinie;

    private Long idLinkedBy;

    private Long idLinkedByCamp;

}

