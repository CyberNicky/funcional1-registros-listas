package br.ufpe.cin.lf1.prototipo.values;

public sealed interface Value permits IntValue, StringValue, BoolValue, RecordValue, ListValue {}