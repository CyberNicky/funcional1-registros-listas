package br.ufpe.cin.lf1.values;

public sealed interface Value permits IntValue, StringValue, BoolValue, RecordValue, ListValue {}