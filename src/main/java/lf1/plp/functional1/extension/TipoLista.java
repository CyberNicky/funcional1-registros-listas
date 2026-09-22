package lf1.plp.functional1.extension;

import lf1.plp.expressions1.util.Tipo;
import lf1.plp.functional1.util.TipoPolimorfico;

/** null representa apenas o elemento ainda indeterminado de uma lista vazia. */
public final class TipoLista extends TipoComposto {
    private final TipoRegistro elemento;
    public TipoLista(TipoRegistro elemento) { this.elemento = elemento; }
    public TipoRegistro elemento() { return elemento; }
    public String getNome() { return "[" + (elemento == null ? "?registro" : elemento.getNome()) + "]"; }
    public boolean eIgual(Tipo outro) {
        if (outro instanceof TipoPolimorfico) return outro.eIgual(this);
        return outro instanceof TipoLista lista
            && (elemento == null || lista.elemento == null || elemento.eIgual(lista.elemento));
    }
    public Tipo intersecao(Tipo outro) {
        if (!eIgual(outro)) return null;
        return elemento == null && outro instanceof TipoLista ? outro : this;
    }
}
