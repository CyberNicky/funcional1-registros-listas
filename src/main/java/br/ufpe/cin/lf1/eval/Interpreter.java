package br.ufpe.cin.lf1.eval;

import br.ufpe.cin.lf1.ast.*;
import br.ufpe.cin.lf1.types.RecordType;
import br.ufpe.cin.lf1.types.Type;
import br.ufpe.cin.lf1.values.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Interpreter {

    public Value eval(ASTNode node, Environment env) {
        return switch (node) {
            case RecordInstNode inst -> {
                Map<String, Value> evaluatedFields = new HashMap<>();
                for (var entry : inst.fieldExpressions().entrySet()) {
                    Value val = switch (entry.getValue()) {
                        case RecordInstNode.StaticValue sv -> sv.value();
                        case RecordInstNode.DynamicASTNode dn -> eval(dn.node(), env);
                    };
                    evaluatedFields.put(entry.getKey(), val);
                }
                yield new RecordValue(inst.recordTypeName(), evaluatedFields);
            }

            case FieldAccessNode access -> {
                Value target = eval(access.targetExpression(), env);
                if (target instanceof RecordValue record) {
                    yield record.getField(access.fieldName());
                }
                throw new RuntimeException("Erro ao acessar campo em valor que não é um registro.");
            }

            case ListLiteralNode list -> {
                List<RecordValue> elements = new ArrayList<>();
                Type elemType = null;

                for (ASTNode expr : list.elements()) {
                    RecordValue rec = (RecordValue) eval(expr, env);
                    elements.add(rec);
                    if (elemType == null) {
                        elemType = new RecordType(rec.recordTypeName(), Map.of());
                    }
                }
                yield new ListValue(elemType, elements);
            }

            case BuiltinOpNode op -> {
                ListValue list = (ListValue) eval(op.argument(), env);
                yield switch (op.opName()) {
                    case "head" -> list.head();
                    case "tail" -> list.tail();
                    case "isEmpty" -> new BoolValue(list.isEmpty());
                    default -> throw new RuntimeException("Operação inválida: " + op.opName());
                };
            }

            default -> throw new UnsupportedOperationException("Nó não suportado no interpretador.");
        };
    }
}