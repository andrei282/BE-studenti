package ro.tacheandrei.cercetare.gestionarestudenti.gestionarestudentiservice.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "echipe_astm")
@Data
@NoArgsConstructor
public class EchipeASTM {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String membruUnu;
    private String membruDoi;
    private String membruTrei;
    private String membruPatru;
    private String categorieTema;
    private String temaDisertatie;
}
