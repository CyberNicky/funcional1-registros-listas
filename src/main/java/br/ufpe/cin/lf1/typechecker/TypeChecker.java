package br.ufpe.cin.lf1.typechecker;

import br.ufpe.cin.lf1.ast.*;
import br.ufpe.cin.lf1.types.*;
import br.ufpe.cin.lf1.values.*;

import java.util.HashMap;
import java.util.Map;

public class TypeChecker {
    private final Map<String, RecordType> typeEnvironment = new HashMap<>();

    public void registerRecordType(RecordDeclNode decl) {
        typeEnvironment.put(decl.name(), new RecordType(decl.name(), decl.declaredFields()));
    }

    public Type check(ASTNode node) {
        return switch (node) {
            case RecordInstNode inst -> {
                RecordType recordType = typeEnvironment.get(inst.recordTypeName());
                if (recordType == null) {
                    throw new TypeErrorException("Tipo de registro não declarado: " + inst.recordTypeName());
                }

                if (inst.fieldExpressions().size() != recordType.fields().size()) {
                    throw new TypeErrorException("Número incorreto de campos para o registro " + inst.recordTypeName());
                }

                for (var entry : inst.fieldExpressions().entrySet()) {
                    String fieldName = entry.getKey();
                    Type expectedType = recordType.fields().get(fieldName);

                    if (expectedType == null) {
                        throw new TypeErrorException("Campo inexistente '" + fieldName + "' no registro " + inst.recordTypeName());
                    }

                    Type actualType = switch (entry.getValue()) {
                        case RecordInstNode.StaticValue sv -> getTypeOfValue(sv.value());
                        case RecordInstNode.DynamicASTNode dn -> check(dn.node());
                    };

                    if (!expectedType.equals(actualType)) {
                        throw new TypeErrorException("Tipo incompatível para o campo '" + fieldName + "'. Esperado: " 
                            + expectedType + ", Recebido: " + actualType);
                    }
                }
                yield recordType;
            }

            case FieldAccessNode access -> {
                Type targetType = check(access.targetExpression());
                if (!(targetType instanceof RecordType recType)) {
                    throw new TypeErrorException("Acesso a campo em um tipo não-registro: " + targetType);
                }
                Type fieldType = recType.fields().get(access.fieldName());
                if (fieldType == null) {
                    throw new TypeErrorException("Campo '" + access.fieldName() + "' não existe no registro " + recType.name());
                }
                yield fieldType;
            }

            case ListLiteralNode list -> {
                if (list.elements().isEmpty()) {
                    yield new ListType(PrimitiveType.INT);
                }

                Type firstType = check(list.elements().get(0));
                if (!(firstType instanceof RecordType)) {
                    throw new TypeErrorException("Esta versão da linguagem suporta apenas listas de registros.");
                }

                for (int i = 1; i < list.elements().size(); i++) {
                    Type currentType = check(list.elements().get(i));
                    if (!firstType.equals(currentType)) {
                        throw new TypeErrorException("Registros de tipos diferentes na mesma lista: " 
                            + firstType + " e " + currentType);
                    }
                }
                yield new ListType(firstType);
            }

            case BuiltinOpNode op -> {
                Type argType = check(op.argument());
                if (!(argType instanceof ListType listType)) {
                    throw new TypeErrorException("Operação '" + op.opName() + "' só é permitida em listas.");
                }

                yield switch (op.opName()) {
                    case "head" -> listType.elementType();
                    case "tail" -> listType;
                    case "isEmpty" -> PrimitiveType.BOOLEAN;
                    default -> throw new TypeErrorException("Operação desconhecida: " + op.opName());
                };
            }

            default -> throw new UnsupportedOperationException("Nó não suportado na verificação de tipos.");
        };
    }

    private Type getTypeOfValue(Value value) {
        return switch (value) {
            case IntValue v -> PrimitiveType.INT;
            case StringValue v -> PrimitiveType.STRING;
            case BoolValue v -> PrimitiveType.BOOLEAN;
            case RecordValue r -> typeEnvironment.get(r.recordTypeName());
            case ListValue l -> new ListType(l.elementType());
        };
    }
}