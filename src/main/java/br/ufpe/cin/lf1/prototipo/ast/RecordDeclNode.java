package br.ufpe.cin.lf1.prototipo.ast;

import br.ufpe.cin.lf1.prototipo.types.Type;
import java.util.Map;

public record RecordDeclNode(String name, Map<String, Type> declaredFields) implements ASTNode {}