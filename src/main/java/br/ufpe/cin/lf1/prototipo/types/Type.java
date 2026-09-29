package br.ufpe.cin.lf1.prototipo.types;

public sealed interface Type permits PrimitiveType, RecordType, ListType {
    PrimitiveType INT = new PrimitiveType("Int");
    PrimitiveType STRING = new PrimitiveType("String");
    PrimitiveType BOOLEAN = new PrimitiveType("Boolean");
}