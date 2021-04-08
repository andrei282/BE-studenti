package ro.tacheandrei.diseratie.administrare.dto;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import ro.tacheandrei.diseratie.administrare.domain.Camp;

@Data
public class CampDTO {

    private Long id;
    private String cod;
    private String denumire;
    private String tipCamp;
    private NomenclatorDTO nomenclator;
    private NomenclatorDTO link;
    private CampDTO linkCamp;

    public static CampDTO from(Camp camp) {
        CampDTO campDTO = new CampDTO();
        BeanUtils.copyProperties(camp, campDTO);
        return campDTO;
    }

    public CampDTO fromEntity(Camp camp) {
        this.setId(camp.getId());
        this.setCod(camp.getCod());
        this.setDenumire(camp.getDenumire());
        this.setTipCamp(camp.getTipCamp());

        if (camp.getNomenclator() != null) {
            NomenclatorDTO nomenclator = new NomenclatorDTO();
            nomenclator.setId(camp.getNomenclator().getId());
            nomenclator.setCod(camp.getNomenclator().getCod());
            nomenclator.setDenumire(camp.getNomenclator().getDenumire());
            this.setNomenclator(nomenclator);
        }

        if (camp.getLink() != null) {
            NomenclatorDTO nomLink = new NomenclatorDTO();
            nomLink.setId(camp.getLink().getId());
            nomLink.setCod(camp.getLink().getCod());
            nomLink.setDenumire(camp.getLink().getDenumire());
            this.setLink(nomLink);
        }

        if (camp.getLinkCamp() != null) {
            CampDTO nomCamp = new CampDTO();
            nomCamp.setId(camp.getLinkCamp().getId());
            nomCamp.setCod(camp.getLinkCamp().getCod());
            nomCamp.setDenumire(camp.getLinkCamp().getDenumire());
            this.setLinkCamp(nomCamp);
        }

        return this;
    }

}
