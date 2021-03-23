package ro.tacheandrei.cercetare.gestionarestudenti.gestionarestudentiservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import ro.tacheandrei.cercetare.gestionarestudenti.gestionarestudentiservice.entity.ProiectISATM;

@Data
@NoArgsConstructor
public class ProiectISATMDTO {

    private Long id;
    private String nume;
    private String specializare;
    private String descrierePost;
    private String detaliereLicenta;
    private String limbajeCunoscute;
    private String temaDisertatie;
    private String categorieTema;
    private String detaliereDiseratie;

    public static ProiectISATMDTO from(ProiectISATM proiectISATM){
        ProiectISATMDTO echipeISATMDTO = new ProiectISATMDTO();
        BeanUtils.copyProperties(proiectISATM, echipeISATMDTO);
        return echipeISATMDTO;
    }
}
