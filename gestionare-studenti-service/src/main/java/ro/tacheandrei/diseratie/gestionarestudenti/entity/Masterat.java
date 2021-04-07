package ro.tacheandrei.diseratie.gestionarestudenti.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "masterat")
@Data
@NoArgsConstructor
public class Masterat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "masterat")
    private List<Materie> materii;

    private String cod;

    private String denumire;

    private String abreviere;

    private String responsabil;
}
