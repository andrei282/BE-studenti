package ro.tacheandrei.disertatie.components.filter;

import java.util.Arrays;

public enum SearchOperation {
    EQUAL("equals"),
    CONTAIN("contains");

    public String operation;

    SearchOperation(String op) {
        this.operation = op;
    }

    public static SearchOperation getValue(String op) {
        return Arrays.stream(SearchOperation.values())
                .filter((v) -> v.operation.equals(op)).findFirst().get();
    }
}
