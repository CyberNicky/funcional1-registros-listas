package br.ufpe.cin.lf1.prototipo.types;

import java.util.Map;

public record RecordType(String name, Map<String, Type> fields) implements Type {}