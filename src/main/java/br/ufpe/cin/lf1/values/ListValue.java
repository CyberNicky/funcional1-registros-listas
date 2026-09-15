package br.ufpe.cin.lf1.values;

import br.ufpe.cin.lf1.types.Type;
import java.util.List;

public record ListValue(Type elementType, List<RecordValue> elements) implements Value {
    public ListValue {
        elements = List.copyOf(elements);
    }

    public RecordValue head() {
        if (elements.isEmpty()) {
            throw new RuntimeException("Erro de Execução: Não é possível obter head de uma lista vazia.");
        }
        return elements.get(0);
    }

    public ListValue tail() {
        if (elements.isEmpty()) {
            throw new RuntimeException("Erro de Execução: Não é possível obter tail de uma lista vazia.");
        }
        return new ListValue(elementType, elements.subList(1, elements.size()));
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }
}