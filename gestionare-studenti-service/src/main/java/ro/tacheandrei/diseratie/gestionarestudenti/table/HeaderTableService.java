package ro.tacheandrei.diseratie.gestionarestudenti.table;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HeaderTableService {

    public static <T> List<FieldDescriptor> calculeazaColoaneTabel(Class<T> clasa){
        List<FieldDescriptor> header = new ArrayList<>();

        for(Field field: clasa.getDeclaredFields()){
            if(field.isAnnotationPresent(TableHeader.class)){
                TableHeader tableHeader = field.getAnnotation(TableHeader.class);
                header.add(new FieldDescriptor(
                        tableHeader.name(),
                        field.getType().getName(),
                        field.getName(),
                        tableHeader.order()
                ));
            }
        }

        header.sort(Comparator.comparing(FieldDescriptor::getOrder));
        return header;
    }
}
