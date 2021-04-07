package ro.tacheandrei.diseratie.gestionarestudenti.entity;

import lombok.Data;

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
}
