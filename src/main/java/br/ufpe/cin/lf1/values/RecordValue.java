package br.ufpe.cin.lf1.values;

import java.util.Map;

public record RecordValue(String recordTypeName, Map<String, Value> fields) implements Value {
    public RecordValue {
        fields = Map.copyOf(fields);
    }

    public Value getField(String fieldName) {
        Value val = fields.get(fieldName);
        if (val == null) {
            throw new RuntimeException("Erro de Execução: O campo '" + fieldName + "' não existe no registro '" + recordTypeName + "'.");
        }
        return val;
    }
}