package ro.tacheandrei.diseratie.administrare.dto;

import lombok.Data;
import ro.tacheandrei.diseratie.administrare.domain.Linie;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class LinieDTO {

    private Long id;
    private NomenclatorDTO nomenclator;
    private List<ValoareDTO> valoareSet;
    private LocalDate valabilDeLa;
    private LocalDate valabilPanaLa;

    public LinieDTO fromEntity(Linie linie) {
        this.setId(linie.getId());

        NomenclatorDTO nomenclator = new NomenclatorDTO();
        nomenclator.setId(linie.getNomenclator().getId());
        nomenclator.setCod(linie.getNomenclator().getCod());
        nomenclator.setDenumire(linie.getNomenclator().getDenumire());
        this.setNomenclator(nomenclator);


        List<ValoareDTO> valori = linie.getValoareSet()
                .stream()
                .map((v) -> {
                    ValoareDTO valoare = new ValoareDTO();
                    valoare.setId(v.getId());
                    valoare.setValoare(v.getValoare());

                    CampDTO camp = new CampDTO();
                    camp.setId(v.getCamp().getId());
                    camp.setCod(v.getCamp().getCod());
                    camp.setDenumire(v.getCamp().getDenumire());
                    camp.setTipCamp(v.getCamp().getTipCamp());

                    if (v.getCamp().getLink() != null) {
                        NomenclatorDTO linkNom = new NomenclatorDTO();
                        linkNom.setId(v.getCamp().getLink().getId());
                        linkNom.setCod(v.getCamp().getLink().getCod());
                        linkNom.setDenumire(v.getCamp().getLink().getDenumire());
                        camp.setLink(linkNom);
                    }

                    if (v.getCamp().getLinkCamp() != null) {
                        CampDTO linkCamp = new CampDTO();
                        linkCamp.setId(v.getCamp().getLinkCamp().getId());
                        linkCamp.setCod(v.getCamp().getLinkCamp().getCod());
                        linkCamp.setDenumire(v.getCamp().getLinkCamp().getDenumire());
                        camp.setLinkCamp(linkCamp);
                    }

                    valoare.setCamp(camp);

                    return valoare;
                })
                .sorted(Comparator.comparing(o -> o.getCamp().getId()))
                .collect(Collectors.toList());
        this.setValoareSet(valori);

        return this;
    }

}
