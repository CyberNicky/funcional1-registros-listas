package br.ufpe.cin.lf1.prototipo.ast;

public sealed interface ASTNode permits RecordDeclNode, RecordInstNode, FieldAccessNode, ListLiteralNode, BuiltinOpNode {}