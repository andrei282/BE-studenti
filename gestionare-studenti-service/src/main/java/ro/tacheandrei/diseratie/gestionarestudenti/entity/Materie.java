package ro.tacheandrei.diseratie.gestionarestudenti.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "materie")
@Data
@NoArgsConstructor
public class Materie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_masterat")
    private Masterat masterat;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "materie")
    private List<Proiect> proiecte;

    private String denumire;

    private String abreviere;

    private String cod;

    private String profesor;
}
