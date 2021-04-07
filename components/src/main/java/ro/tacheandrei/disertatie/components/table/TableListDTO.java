package ro.tacheandrei.disertatie.components.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableListDTO<T> {
    private T page;
    private List<FieldDescriptor> headerTable;

}
