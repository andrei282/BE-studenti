package ro.tacheandrei.disertatie.components.filter;

import java.util.Arrays;

public enum FilterType {
    TEXT("text"),
    NUMBER("number"),
    DATE("date"),
    UUID("uuid"),
    COMMON("common"),
    CUSTOM("custom");

    public String type;


    FilterType(String type) {
        this.type = type;
    }

    public static FilterType getValue(String op) {
        return Arrays.stream(FilterType.values())
                .filter((v) -> v.type.equals(op)).findFirst().get();
    }
}
