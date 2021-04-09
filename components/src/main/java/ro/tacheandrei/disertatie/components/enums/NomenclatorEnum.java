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

    public static NomenclatorEnum getForValue(String value) {
        for (NomenclatorEnum nomenclatorEnum : NomenclatorEnum.values()) {
            if (nomenclatorEnum.getValue().equals(value)) {
                return nomenclatorEnum;
            }
        }
        throw new RuntimeException("Contractul " + value + " nu a fost gasit.");
    }

    @Override
    public String toString() {
        return value;
    }
}
