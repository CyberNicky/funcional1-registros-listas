package lf1.plp.functional1.extension;

import lf1.plp.expressions2.expression.Expressao;
import lf1.plp.expressions2.memory.AmbienteCompilacao;
import lf1.plp.expressions2.memory.AmbienteExecucao;

public abstract class ExpressaoExtensao implements Expressao {
    public boolean checaTipo(AmbienteCompilacao amb) { getTipo(amb); return true; }
    public Expressao reduzir(AmbienteExecucao amb) { return clone(); }
    public abstract Expressao clone();
    protected static void verificar(Expressao expressao, AmbienteCompilacao amb) {
        if (!expressao.checaTipo(amb)) throw new ErroExtensao("Expressão com tipo incompatível na extensão.");
    }
}
