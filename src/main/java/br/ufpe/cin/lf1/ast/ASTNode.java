package br.ufpe.cin.lf1.ast;

public sealed interface ASTNode permits RecordDeclNode, RecordInstNode, FieldAccessNode, ListLiteralNode, BuiltinOpNode {}