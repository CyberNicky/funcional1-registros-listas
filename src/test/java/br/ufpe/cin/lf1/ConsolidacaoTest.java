package br.ufpe.cin.lf1;

import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import lf1.plp.expressions1.util.TipoPrimitivo;
import lf1.plp.expressions2.expression.*;
import lf1.plp.expressions2.memory.ContextoCompilacao;
import lf1.plp.expressions2.memory.VariavelNaoDeclaradaException;
import lf1.plp.functional1.Programa;
import lf1.plp.functional1.extension.ErroExtensao;
import lf1.plp.functional1.memory.ContextoExecucaoFuncional;
import lf1.plp.functional1.parser.Func1Parser;
import lf1.plp.functional1.parser.ParseException;
import org.junit.Test;
import static org.junit.Assert.*;

public class ConsolidacaoTest {
    private Programa parse(String fonte) throws Exception { return new Func1Parser(new StringReader(fonte)).Input(); }
    private Valor avaliar(String fonte) throws Exception {
        Programa p = parse(fonte);
        assertTrue(p.checaTipo());
        return p.executar();
    }

    private record Saida(int codigo, String out, String err) {}
    private Saida cli(String fonte, String... args) throws Exception {
        List<String> comando = new ArrayList<>(List.of(
            Path.of(System.getProperty("java.home"), "bin", "java").toString(),
            "-cp", Path.of("target/classes").toAbsolutePath().toString(), "br.ufpe.cin.lf1.Main"));
        comando.addAll(List.of(args));
        Process processo = new ProcessBuilder(comando).start();
        try {
            try (var entrada = processo.getOutputStream()) {
                entrada.write(fonte.getBytes(StandardCharsets.UTF_8));
            }
            assertTrue("CLI não terminou", processo.waitFor(10, TimeUnit.SECONDS));
            return new Saida(processo.exitValue(),
                new String(processo.getInputStream().readAllBytes(), StandardCharsets.UTF_8),
                new String(processo.getErrorStream().readAllBytes(), StandardCharsets.UTF_8));
        } finally { processo.destroyForcibly(); }
    }

    @Test public void construtorEmFuncaoPreservaTipoNominalSobSombreamento() throws Exception {
        String fonte = "let record P { x: Int } in let fun criar n = P { x: n } in "
            + "let var a = criar(1) in let record P { y: String } in let var b = criar(1) in a == b";
        assertTrue(((ValorBooleano) avaliar(fonte)).valor());
        Programa programa = parse(fonte);
        assertTrue(programa.checaTipo());
        Programa copia = new Programa(programa.getExpressao().clone());
        assertTrue(copia.checaTipo());
        assertTrue(((ValorBooleano) copia.executar()).valor());
    }

    @Test public void erroDeExecucaoRestauraEscoposDeLetEFuncao() throws Exception {
        Programa p = parse("let fun f x = head(x) in let var x = [] in f(x)");
        assertTrue(p.checaTipo());
        ContextoExecucaoFuncional amb = new ContextoExecucaoFuncional();
        amb.incrementa();
        amb.map(new Id("x"), new ValorInteiro(7));
        assertThrows(ErroExtensao.class, () -> p.getExpressao().avaliar(amb));
        assertEquals(Integer.valueOf(7), ((ValorInteiro) amb.get(new Id("x"))).valor());
        amb.restaura();
        assertThrows(VariavelNaoDeclaradaException.class, () -> amb.get(new Id("x")));
    }

    @Test public void erroDeTipoRestauraEscoposDuranteGetTipo() throws Exception {
        Programa p = parse("let var x = true in head(1)");
        ContextoCompilacao amb = new ContextoCompilacao();
        amb.incrementa();
        amb.map(new Id("x"), TipoPrimitivo.INTEIRO);
        assertThrows(ErroExtensao.class, () -> p.getExpressao().getTipo(amb));
        assertSame(TipoPrimitivo.INTEIRO, amb.get(new Id("x")));
        amb.restaura();
    }

    @Test public void camposTemDiagnosticoNaPosicaoDoCampo() throws Exception {
        Programa p = parse("let record P { x: Int } in\nP { x: true }");
        ErroExtensao e = assertThrows(ErroExtensao.class, p::checaTipo);
        assertEquals(2, e.getLinha());
        assertEquals(5, e.getColuna());
        assertTrue(e.getMessage().contains("campo 'x' de P"));
        assertTrue(e.getMessage().contains("esperado INTEIRO, recebido BOOLEANO"));
    }

    @Test public void erroDeChamadaIndicaFuncaoArgumentoELocal() throws Exception {
        Programa p = parse("let fun f p = p.nome in\nf(1)");
        ErroExtensao e = assertThrows(ErroExtensao.class, p::checaTipo);
        assertEquals(2, e.getLinha());
        assertEquals(1, e.getColuna());
        assertTrue(e.getMessage().contains("função 'f': Tipo incompatível no argumento 1"));
    }

    @Test public void errosSintaticosManuaisIncluemPosicao() {
        ParseException e = assertThrows(ParseException.class,
            () -> parse("let record P { x: Int, x: String } in 1"));
        assertTrue(e.getMessage().contains("Linha 1, coluna"));
        e = assertThrows(ParseException.class, () -> parse("let record P { x: Outro } in 1"));
        assertTrue(e.getMessage().contains("Linha 1, coluna"));
    }

    @Test public void cliDistingueTipoSintaxeEExecucao() throws Exception {
        Saida tipo = cli("let fun f p = p.nome in\nf(1)");
        assertEquals(1, tipo.codigo());
        assertEquals("", tipo.out());
        assertTrue(tipo.err().startsWith("Erro de tipo: linha 2, coluna 1:"));
        Saida execucao = cli("\nhead([])");
        assertEquals(1, execucao.codigo());
        assertTrue(execucao.err().startsWith("Erro de execução: linha 2, coluna 1:"));
        Saida sintaxe = cli("let var x = 1 in");
        assertEquals(1, sintaxe.codigo());
        assertTrue(sintaxe.err().startsWith("Erro de sintaxe:"));
    }

    @Test public void cliTrataEntradaUtf8ArquivoAusenteEOpcoes() throws Exception {
        Saida utf8 = cli("\"olá\"");
        assertEquals(0, utf8.codigo());
        assertEquals("Resultado: olá", utf8.out().strip());
        Saida ausente = cli("", "examples/arquivo-inexistente.lf1");
        assertEquals(1, ausente.codigo());
        assertTrue(ausente.err().startsWith("Erro ao ler programa:"));
        assertEquals(2, cli("", "--opcao-inexistente").codigo());
        assertEquals(0, cli("", "--help").codigo());
    }

    @Test public void prototipoEstaIdentificadoComoHistorico() throws Exception {
        Saida saida = cli("", "--demo-registros");
        assertEquals(0, saida.codigo());
        assertTrue(saida.out().startsWith("Protótipo histórico em Java; não executa programas .lf1."));
    }

    @Test public void todosOsExemplosTemResultadoECodigoDocumentados() throws Exception {
        for (String linha : Files.readAllLines(Path.of("examples/resultados.tsv"))) {
            if (linha.isBlank() || linha.startsWith("#")) continue;
            String[] campos = linha.split("\t", 3);
            Saida saida = cli("", "examples/" + campos[0]);
            assertEquals(campos[0], Integer.parseInt(campos[1]), saida.codigo());
            String texto = saida.codigo() == 0 ? saida.out() : saida.err();
            assertTrue(campos[0] + ": " + texto, texto.contains(campos[2]));
        }
    }

    @Test public void programasCompletosDoReadmeSaoExecutaveis() throws Exception {
        var matcher = Pattern.compile("```text\\R(.*?)```", Pattern.DOTALL)
            .matcher(Files.readString(Path.of("README.md")));
        int exemplos = 0;
        while (matcher.find()) { avaliar(matcher.group(1)); exemplos++; }
        assertTrue("README deve apresentar exemplos completos", exemplos >= 3);
    }
}
