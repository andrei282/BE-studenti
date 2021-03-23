package ro.tacheandrei.cercetare.gestionarestudenti.gestionarestudentiservice.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "proiect_isatm")
@Data
@NoArgsConstructor
public class ProiectISATM {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nume;
    private String specializare;
    private String descrierePost;
    private String detaliereLicenta;
    private String limbajeCunoscute;
    private String temaDisertatie;
    private String categorieTema;
    private String detaliereDiseratie;
}
