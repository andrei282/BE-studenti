package ro.tacheandrei.diseratie.gestionarestudenti.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

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

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_proiect")
    private Proiect proiect;
}
