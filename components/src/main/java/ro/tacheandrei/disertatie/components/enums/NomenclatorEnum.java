package ro.tacheandrei.disertatie.components.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NomenclatorEnum {
    MASTERAT("masterat"),
    MATERIE("materie"),
    PROIECT("proiect");

    private final String value;

    @Override
    public String toString() {
        return value;
    }
}
