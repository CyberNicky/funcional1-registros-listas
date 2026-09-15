package br.ufpe.cin.lf1.ast;

public record FieldAccessNode(ASTNode targetExpression, String fieldName) implements ASTNode {}