package br.ufpe.cin.lf1.prototipo.ast;

import java.util.List;

public record ListLiteralNode(List<ASTNode> elements) implements ASTNode {}