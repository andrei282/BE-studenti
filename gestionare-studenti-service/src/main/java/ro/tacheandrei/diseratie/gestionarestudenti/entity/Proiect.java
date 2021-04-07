package ro.tacheandrei.diseratie.gestionarestudenti.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "proiect")
@Data
@NoArgsConstructor
public class Proiect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cod;

    private String denumire;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "proiect")
    private List<Camp> campuri;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_materie")
    private Materie materie;
}
