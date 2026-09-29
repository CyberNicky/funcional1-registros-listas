package br.ufpe.cin.lf1.prototipo.ast;

public record BuiltinOpNode(String opName, ASTNode argument) implements ASTNode {}