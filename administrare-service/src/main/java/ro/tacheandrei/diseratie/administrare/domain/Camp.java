package ro.tacheandrei.diseratie.administrare.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import ro.tacheandrei.diseratie.administrare.dto.CampDTO;

import javax.persistence.*;

@Entity
@Table(name = "camp")
@Data
public class Camp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cod;

    private String denumire;

    private String tipCamp;

    @JsonBackReference(value = "campSet")
    @ManyToOne
    @JoinColumn(name = "id_nomenclator")
    private Nomenclator nomenclator;

    @JsonBackReference(value = "link")
    @ManyToOne
    @JoinColumn(name = "id_linked_by")
    private Nomenclator link;

    @JsonBackReference(value = "linkCamp")
    @ManyToOne
    @JoinColumn(name = "id_linked_by_camp")
    private Camp linkCamp;

    public static Camp from(CampDTO campDTO) {
        Camp camp = new Camp();
        BeanUtils.copyProperties(campDTO, camp);
        return camp;
    }
}
