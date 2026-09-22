package br.ufpe.cin.lf1;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import lf1.plp.expressions2.expression.Valor;
import lf1.plp.expressions2.expression.ValorBooleano;
import lf1.plp.expressions2.expression.ValorInteiro;
import lf1.plp.expressions2.expression.ValorString;
import lf1.plp.functional1.Programa;
import lf1.plp.functional1.parser.Func1Parser;
import lf1.plp.functional1.parser.ParseException;
import lf1.plp.functional1.parser.TokenMgrError;

/** Entrada de linha de comando para a Funcional 1 da disciplina. */
public class Main {
    public static void main(String[] args) {
        if (args.length == 1 && args[0].equals("--demo-registros")) {
            DemoRegistros.main(new String[0]);
            return;
        }
        if (args.length == 1 && args[0].equals("--help")) {
            usage();
            return;
        }
        if (args.length > 1 || (args.length == 1 && args[0].startsWith("--"))) {
            usage();
            System.exit(2);
            return;
        }
        try (Reader input = args.length == 0
                ? new InputStreamReader(System.in, StandardCharsets.UTF_8)
                : Files.newBufferedReader(Path.of(args[0]), StandardCharsets.UTF_8)) {
            Programa programa = new Func1Parser(input).Input();
            if (!programa.checaTipo()) {
                System.err.println("Erro de tipo: programa rejeitado pela Funcional 1.");
                System.exit(1);
                return;
            }
            Valor valor = programa.executar();
            String resultado = switch (valor) {
                case ValorInteiro inteiro -> Integer.toString(inteiro.valor());
                case ValorBooleano booleano -> Boolean.toString(booleano.valor());
                case ValorString string -> string.valor();
                default -> valor.toString();
            };
            System.out.println("Resultado: " + resultado);
        } catch (ParseException | TokenMgrError e) {
            System.err.println("Erro de sintaxe: " + e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Erro ao ler programa: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            String detalhe = e.getMessage() == null ? e.getClass().getSimpleName() : e.getMessage();
            System.err.println("Erro na verificação ou execução: " + detalhe);
            System.exit(1);
        }
    }

    private static void usage() {
        System.out.println("Uso: java -jar target/lf1-records-lists-1.0-SNAPSHOT.jar [arquivo.lf1]");
        System.out.println("Sem arquivo, lê um programa da entrada padrão até EOF.");
        System.out.println("Opções: --help | --demo-registros");
    }
}
