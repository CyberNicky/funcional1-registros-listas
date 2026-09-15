package br.ufpe.cin.lf1.ast;

public record BuiltinOpNode(String opName, ASTNode argument) implements ASTNode {}