package ro.tacheandrei.diseratie.administrare.domain;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import ro.tacheandrei.diseratie.administrare.dto.ValoareDTO;

import javax.persistence.*;

@Entity
@Table(name = "valoare")
@Data
public class Valoare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_camp")
    private Camp camp;

    private String valoare;

    public static Valoare from(ValoareDTO valoareDTO) {
        Valoare valoareEntity = new Valoare();

        BeanUtils.copyProperties(valoareDTO, valoareEntity);

        if (valoareDTO.getCamp() != null) {
            valoareEntity.setCamp(Camp.from(valoareDTO.getCamp()));
        }

        return valoareEntity;
    }
}
