package lf1.plp.functional1.extension;

import lf1.plp.expressions1.util.Tipo;
import lf1.plp.functional1.util.Inferencia;
import lf1.plp.expressions1.util.TipoPrimitivo;
import lf1.plp.expressions2.expression.Expressao;
import lf1.plp.expressions2.expression.Valor;
import lf1.plp.expressions2.expression.ValorBooleano;
import lf1.plp.expressions2.memory.AmbienteCompilacao;
import lf1.plp.expressions2.memory.AmbienteExecucao;

public final class ExpOperacaoLista extends ExpressaoExtensao {
    public enum Operacao { HEAD, TAIL, IS_EMPTY }
    private final Operacao operacao;
    private final Expressao argumento;
    public ExpOperacaoLista(Operacao operacao, Expressao argumento) {
        this.operacao = operacao;
        this.argumento = argumento;
    }
    public Tipo getTipo(AmbienteCompilacao amb) {
        verificar(argumento, amb);
        TipoLista lista = Inferencia.lista(argumento.getTipo(amb));
        return switch (operacao) {
            case IS_EMPTY -> TipoPrimitivo.BOOLEANO;
            case TAIL -> lista;
            case HEAD -> lista.elemento();
        };
    }
    public Valor avaliar(AmbienteExecucao amb) {
        ValorLista lista = (ValorLista) argumento.avaliar(amb);
        return switch (operacao) {
            case HEAD -> lista.head();
            case TAIL -> lista.tail();
            case IS_EMPTY -> new ValorBooleano(lista.valor().isEmpty());
        };
    }
    public ExpOperacaoLista clone() { return new ExpOperacaoLista(operacao, argumento.clone()); }
}
