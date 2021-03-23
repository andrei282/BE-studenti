package ro.tacheandrei.cercetare.gestionarestudenti.gestionarestudentiservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import ro.tacheandrei.cercetare.gestionarestudenti.gestionarestudentiservice.entity.ProiectISCAn4;

@Data
@NoArgsConstructor
public class ProiectISCAn4DTO {

    private Long id;

    private String nume;
    private String grupa;
    private String temaTpi;
    private String coechipier;
    private String temaLicenta;
    private String tehnologiiTpi;
    private String tehnologiiExtra;
    private String tematica;
    private String temaIsc;

    public static ProiectISCAn4DTO from(ProiectISCAn4 proiectISCAn4){
        ProiectISCAn4DTO proiectISCAn4DTO = new ProiectISCAn4DTO();
        BeanUtils.copyProperties(proiectISCAn4, proiectISCAn4DTO);
        return proiectISCAn4DTO;
    }
}
