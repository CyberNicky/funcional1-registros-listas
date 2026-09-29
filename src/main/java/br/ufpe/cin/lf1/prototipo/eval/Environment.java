package br.ufpe.cin.lf1.prototipo.eval;

import br.ufpe.cin.lf1.prototipo.values.Value;
import java.util.HashMap;
import java.util.Map;

public class Environment {
    private final Map<String, Value> bindings = new HashMap<>();

    public void bind(String name, Value value) {
        bindings.put(name, value);
    }

    public Value lookup(String name) {
        Value val = bindings.get(name);
        if (val == null) {
            throw new RuntimeException("Variável não declarada no ambiente: " + name);
        }
        return val;
    }
}