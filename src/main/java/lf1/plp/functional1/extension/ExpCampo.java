package lf1.plp.functional1.extension;

import lf1.plp.expressions1.util.Tipo;
import lf1.plp.expressions2.expression.Expressao;
import lf1.plp.expressions2.expression.Valor;
import lf1.plp.expressions2.memory.AmbienteCompilacao;
import lf1.plp.expressions2.memory.AmbienteExecucao;
import lf1.plp.functional1.util.TipoPolimorfico;

public final class ExpCampo extends ExpressaoExtensao {
    private final Expressao alvo;
    private final String campo;
    public ExpCampo(Expressao alvo, String campo) { this.alvo = alvo; this.campo = campo; }
    public Tipo getTipo(AmbienteCompilacao amb) {
        verificar(alvo, amb);
        Tipo tipo = alvo.getTipo(amb);
        if (tipo instanceof TipoPolimorfico)
            throw new ErroExtensao("A inferência de campos em parâmetros de funções ainda não está implementada.");
        if (!(tipo instanceof TipoRegistro registro)) throw new ErroExtensao("Acesso a campo exige um registro.");
        Tipo resultado = registro.campos().get(campo);
        if (resultado == null) throw new ErroExtensao("Campo inexistente '" + campo + "' em " + registro.getNome());
        return resultado;
    }
    public Valor avaliar(AmbienteExecucao amb) { return ((ValorRegistro) alvo.avaliar(amb)).campo(campo); }
    public ExpCampo clone() { return new ExpCampo(alvo.clone(), campo); }
}
