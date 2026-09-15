package br.ufpe.cin.lf1;

import br.ufpe.cin.lf1.ast.*;
import br.ufpe.cin.lf1.eval.Environment;
import br.ufpe.cin.lf1.eval.Interpreter;
import br.ufpe.cin.lf1.typechecker.TypeChecker;
import br.ufpe.cin.lf1.typechecker.TypeErrorException;
import br.ufpe.cin.lf1.types.PrimitiveType;
import br.ufpe.cin.lf1.types.Type;
import br.ufpe.cin.lf1.values.*;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        TypeChecker typeChecker = new TypeChecker();
        Interpreter interpreter = new Interpreter();
        Environment env = new Environment();

        RecordDeclNode declPessoa = new RecordDeclNode("Pessoa", Map.of(
                "nome", PrimitiveType.STRING,
                "idade", PrimitiveType.INT
        ));
        typeChecker.registerRecordType(declPessoa);
        IO.println("-> Tipo Pessoa declarado com sucesso.");

        ASTNode p1 = new RecordInstNode("Pessoa", Map.of(
                "nome", new RecordInstNode.StaticValue(new StringValue("Monique")),
                "idade", new RecordInstNode.StaticValue(new IntValue(23))
        ));

        ASTNode p2 = new RecordInstNode("Pessoa", Map.of(
                "nome", new RecordInstNode.StaticValue(new StringValue("Bruno")),
                "idade", new RecordInstNode.StaticValue(new IntValue(25))
        ));

        ASTNode listaPessoasNode = new ListLiteralNode(List.of(p1, p2));

        Type tipoLista = typeChecker.check(listaPessoasNode);
        IO.println("-> Lista verificada estaticamente com o tipo: " + tipoLista);

        ListValue listaPessoasVal = (ListValue) interpreter.eval(listaPessoasNode, env);
        env.bind("pessoas", listaPessoasVal);

        IO.println("\n--- Executando Operações sobre Listas ---");

        ASTNode headOp = new BuiltinOpNode("head", listaPessoasNode);
        RecordValue primeiro = (RecordValue) interpreter.eval(headOp, env);
        IO.println("head(pessoas) = " + primeiro);

        ASTNode acessoNome = new FieldAccessNode(headOp, "nome");
        Value nomeVal = interpreter.eval(acessoNome, env);
        IO.println("head(pessoas).nome = " + ((StringValue) nomeVal).value());

        ASTNode tailOp = new BuiltinOpNode("tail", listaPessoasNode);
        ListValue resto = (ListValue) interpreter.eval(tailOp, env);
        IO.println("tail(pessoas) contem " + resto.elements().size() + " elemento(s).");

        ASTNode emptyOp = new BuiltinOpNode("isEmpty", listaPessoasNode);
        BoolValue estaVazia = (BoolValue) interpreter.eval(emptyOp, env);
        IO.println("isEmpty(pessoas) = " + estaVazia.value());

        IO.println("\n--- Validação de Erros Previstos ---");

        try {
            ASTNode pInvalido = new RecordInstNode("Pessoa", Map.of(
                    "nome", new RecordInstNode.StaticValue(new StringValue("Monique")),
                    "idade", new RecordInstNode.StaticValue(new BoolValue(true))
            ));
            typeChecker.check(pInvalido);
        } catch (TypeErrorException e) {
            IO.println("[OK] Capturou Erro de Tipo: " + e.getMessage());
        }

        try {
            ASTNode acessoErrado = new FieldAccessNode(p1, "endereco");
            typeChecker.check(acessoErrado);
        } catch (TypeErrorException e) {
            IO.println("[OK] Capturou Erro de Campo Inexistente: " + e.getMessage());
        }

        try {
            ListValue listaVazia = new ListValue(PrimitiveType.INT, List.of());
            listaVazia.head();
        } catch (RuntimeException e) {
            IO.println("[OK] Capturou Erro de Runtime: " + e.getMessage());
        }
    }
}