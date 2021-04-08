package ro.tacheandrei.diseratie.administrare.domain;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import ro.tacheandrei.diseratie.administrare.dto.LinieDTO;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "linie")
@Data
public class Linie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_nomenclator")
    private Nomenclator nomenclator;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_linie")
    private Set<Valoare> valoareSet;

    public static Linie from(LinieDTO linieDTO) {
        Linie linie = new Linie();
        BeanUtils.copyProperties(linieDTO, linie, "valoareSet", "nomenclator");

        if (linieDTO.getValoareSet() != null) {
            linie.setValoareSet(
                    linieDTO
                            .getValoareSet()
                            .stream()
                            .map(Valoare::from)
                            .collect(Collectors.toSet())
            );
        }

        if (linieDTO.getNomenclator() != null) {
            linie.setNomenclator(Nomenclator.from(linieDTO.getNomenclator()));
        }

        return linie;
    }
}
