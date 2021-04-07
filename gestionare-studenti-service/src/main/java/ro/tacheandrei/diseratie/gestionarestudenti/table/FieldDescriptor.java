package ro.tacheandrei.diseratie.gestionarestudenti.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FieldDescriptor {

    private String fieldName;
    private String fieldType;
    private String field;
    private int order;

    public FieldDescriptor(String fieldName, String fieldType, String field, int order) {
        String[] arrOfFieldType = fieldType.split("\\.");
        this.fieldType = arrOfFieldType[arrOfFieldType.length - 1];
        this.fieldName = fieldName;
        this.field = field;
        this.order = order;
    }
}
