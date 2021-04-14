package ro.tacheandrei.diseratie.administrare.dto;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import ro.tacheandrei.diseratie.administrare.domain.ValoriNomenclator;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
public class ValoriNomenclatorDTO {

    private long id;

    private Long idNomenclator;

    private String cod;

    private Long idCamp;

    private String camp;

    private String tipCamp;

    private String denumireCamp;

    private String valoare;

    private Long idLinie;

    private Long idLinkedBy;

    private Long idLinkedByCamp;

    public static ValoriNomenclatorDTO from(ValoriNomenclator valoriNomenclator){
        ValoriNomenclatorDTO resulted = new ValoriNomenclatorDTO();
        BeanUtils.copyProperties(valoriNomenclator, resulted);
        return resulted;
    }
}

