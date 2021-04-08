package ro.tacheandrei.diseratie.administrare.domain;

import lombok.Data;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity(name = "v_valori_nomenclator")
@Immutable
@Data
public class ValoriNomenclator {

    @Id
    private long id;

    private Long idNomenclator;

    private String cod;

    @Column(name = "id_camp")
    private Long idCamp;

    private String camp;

    private String tipCamp;

    @Column(name = "denumire_camp")
    private String denumireCamp;

    private String valoare;

    @Column(name = "linie")
    private Long idLinie;

    @Column(name = "id_linked_by")
    private Long idLinkedBy;

    @Column(name = "id_linked_by_camp")
    private Long idLinkedByCamp;
}
