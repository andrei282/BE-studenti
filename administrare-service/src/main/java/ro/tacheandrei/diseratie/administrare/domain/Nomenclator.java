package ro.tacheandrei.diseratie.administrare.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import ro.tacheandrei.diseratie.administrare.dto.NomenclatorDTO;
import ro.tacheandrei.disertatie.components.table.TableHeader;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "nomenclator")
@Data
public class Nomenclator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableHeader(name = "id")
    private long id;

    @TableHeader(name = "Cod")
    private String cod;

    @TableHeader(name = "Denumire")
    private String denumire;

    @JsonManagedReference(value = "campSet")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "nomenclator")
    private List<Camp> campSet;

    public static Nomenclator from(NomenclatorDTO nomenclatorDTO) {
        Nomenclator nomenclator = new Nomenclator();
        BeanUtils.copyProperties(nomenclatorDTO, nomenclator);

        return nomenclator;
    }
}
