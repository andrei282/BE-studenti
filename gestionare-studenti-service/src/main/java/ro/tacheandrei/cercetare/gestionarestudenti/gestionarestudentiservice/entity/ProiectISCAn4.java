package ro.tacheandrei.cercetare.gestionarestudenti.gestionarestudentiservice.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import ro.tacheandrei.cercetare.gestionarestudenti.gestionarestudentiservice.dto.ProiectISCAn4DTO;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "proiect_isc_an_4")
@Data
@NoArgsConstructor
public class ProiectISCAn4 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public static ProiectISCAn4 from(ProiectISCAn4DTO proiectISCAn4DTO){
        ProiectISCAn4 proiectISCAn4 = new ProiectISCAn4();
        BeanUtils.copyProperties(proiectISCAn4DTO, proiectISCAn4);
        return proiectISCAn4;
    }
}
