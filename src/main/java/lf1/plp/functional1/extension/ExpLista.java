package lf1.plp.functional1.extension;

import java.util.ArrayList;
import java.util.List;
import lf1.plp.expressions1.util.Tipo;
import lf1.plp.functional1.util.Inferencia;
import lf1.plp.expressions2.expression.Expressao;
import lf1.plp.expressions2.memory.AmbienteCompilacao;
import lf1.plp.expressions2.memory.AmbienteExecucao;

public final class ExpLista extends ExpressaoExtensao {
    private final List<Expressao> elementos;
    public ExpLista(List<Expressao> elementos) { this.elementos = List.copyOf(elementos); }
    public TipoLista getTipo(AmbienteCompilacao amb) {
        Tipo elemento = Inferencia.novoRegistro();
        for (Expressao exp : elementos) {
            verificar(exp, amb);
            Tipo atual = exp.getTipo(amb);
            if (!Inferencia.exigirRegistro(atual))
                throw new ErroExtensao("Listas aceitam somente registros.");
            if (!elemento.eIgual(atual))
                throw new ErroExtensao("Todos os registros da lista devem ter o mesmo tipo.");
        }
        return new TipoLista(elemento);
    }

    public ValorLista avaliar(AmbienteExecucao amb) {
        List<ValorRegistro> valores = new ArrayList<>();
        for (Expressao exp : elementos) valores.add((ValorRegistro) exp.avaliar(amb));
        TipoRegistro elemento = valores.isEmpty() ? null : valores.get(0).getTipo(null);
        return new ValorLista(new TipoLista(elemento), valores);
    }
    public ExpLista clone() { return new ExpLista(elementos.stream().map(Expressao::clone).toList()); }
}
