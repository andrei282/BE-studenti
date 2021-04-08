package ro.tacheandrei.diseratie.administrare.dto;

import lombok.Data;

@Data
public class ValoareDTO {

    private Long id;
    private String valoare;
    private CampDTO camp;

}
