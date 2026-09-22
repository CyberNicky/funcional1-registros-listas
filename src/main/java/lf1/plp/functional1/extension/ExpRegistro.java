package lf1.plp.functional1.extension;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import lf1.plp.expressions1.util.Tipo;
import lf1.plp.expressions2.expression.Expressao;
import lf1.plp.expressions2.expression.Valor;
import lf1.plp.expressions2.memory.AmbienteCompilacao;
import lf1.plp.expressions2.memory.AmbienteExecucao;
import lf1.plp.expressions2.memory.VariavelNaoDeclaradaException;

public final class ExpRegistro extends ExpressaoExtensao {
    private final String nome;
    private final Map<String, Expressao> campos;
    public ExpRegistro(String nome, Map<String, Expressao> campos) {
        this.nome = nome;
        this.campos = Collections.unmodifiableMap(new LinkedHashMap<>(campos));
    }
    public TipoRegistro getTipo(AmbienteCompilacao amb) {
        TipoRegistro tipo;
        try { tipo = (TipoRegistro) amb.get(TipoRegistro.chave(nome)); }
        catch (VariavelNaoDeclaradaException e) { throw new ErroExtensao("Tipo de registro não declarado: " + nome); }
        if (!tipo.campos().keySet().equals(campos.keySet()))
            throw new ErroExtensao("Campos incorretos de " + nome + ": esperados " + tipo.campos().keySet()
                + ", recebidos " + campos.keySet());
        for (var campo : campos.entrySet()) {
            verificar(campo.getValue(), amb);
            Tipo recebido = campo.getValue().getTipo(amb);
            Tipo esperado = tipo.campos().get(campo.getKey());
            if (!esperado.eIgual(recebido)) throw new ErroExtensao("Tipo incompatível no campo "
                + campo.getKey() + ": esperado " + esperado + ", recebido " + recebido);
        }
        return tipo;
    }
    public ValorRegistro avaliar(AmbienteExecucao amb) {
        var definicao = (ExpDeclaracaoRegistro.Definicao) amb.get(TipoRegistro.chave(nome));
        Map<String, Valor> valores = new LinkedHashMap<>();
        campos.forEach((campo, exp) -> valores.put(campo, exp.avaliar(amb)));
        return new ValorRegistro(definicao.valor(), valores);
    }
    public ExpRegistro clone() {
        Map<String, Expressao> copia = new LinkedHashMap<>();
        campos.forEach((campo, exp) -> copia.put(campo, exp.clone()));
        return new ExpRegistro(nome, copia);
    }
}
