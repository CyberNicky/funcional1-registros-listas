package lf1.plp.functional1.extension;

import lf1.plp.expressions1.util.Tipo;
import lf1.plp.functional1.util.Inferencia;

/** Elemento nominal ou variável restrita a registros, compartilhada com head/tail. */
public final class TipoLista extends TipoComposto {
    private final Tipo elemento;
    public TipoLista(Tipo elemento) {
        this.elemento = elemento == null ? Inferencia.novoRegistro() : elemento;
        if (!Inferencia.exigirRegistro(this.elemento))
            throw new ErroExtensao("Listas aceitam somente registros.");
    }
    public Tipo elemento() { return Inferencia.resolver(elemento); }
    public String getNome() { return "[" + elemento().getNome() + "]"; }
    public boolean eIgual(Tipo outro) { return Inferencia.unificar(this, outro); }
    public Tipo intersecao(Tipo outro) { return eIgual(outro) ? this : null; }
}
