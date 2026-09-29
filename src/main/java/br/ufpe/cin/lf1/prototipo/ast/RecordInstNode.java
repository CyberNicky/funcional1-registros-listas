package br.ufpe.cin.lf1.prototipo.ast;

import java.util.Map;

public record RecordInstNode(String recordTypeName, Map<String, ValueOrASTNode> fieldExpressions) implements ASTNode {
    public sealed interface ValueOrASTNode permits StaticValue, DynamicASTNode {}
    public record StaticValue(br.ufpe.cin.lf1.prototipo.values.Value value) implements ValueOrASTNode {}
    public record DynamicASTNode(ASTNode node) implements ValueOrASTNode {}
}