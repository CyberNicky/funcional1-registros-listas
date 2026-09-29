package br.ufpe.cin.lf1.prototipo.ast;

public record FieldAccessNode(ASTNode targetExpression, String fieldName) implements ASTNode {}