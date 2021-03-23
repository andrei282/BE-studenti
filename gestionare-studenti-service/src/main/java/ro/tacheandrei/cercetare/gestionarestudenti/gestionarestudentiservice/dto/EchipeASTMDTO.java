package ro.tacheandrei.cercetare.gestionarestudenti.gestionarestudentiservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import ro.tacheandrei.cercetare.gestionarestudenti.gestionarestudentiservice.entity.EchipeASTM;

@Data
@NoArgsConstructor
public class EchipeASTMDTO {

    private Long id;

    private String membruUnu;
    private String membruDoi;
    private String membruTrei;
    private String membruPatru;
    private String categorieTema;
    private String temaDisertatie;

    public static EchipeASTMDTO from(EchipeASTM echipeASTM){
        EchipeASTMDTO echipeISATMDTO = new EchipeASTMDTO();
        BeanUtils.copyProperties(echipeASTM, echipeISATMDTO);
        return echipeISATMDTO;
    }
}
