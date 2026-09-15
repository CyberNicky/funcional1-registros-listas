package br.ufpe.cin.lf1.types;

import java.util.Map;

public record RecordType(String name, Map<String, Type> fields) implements Type {}