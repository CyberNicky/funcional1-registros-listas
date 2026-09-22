package lf1.plp.functional1.extension;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import lf1.plp.expressions1.util.Tipo;
import lf1.plp.expressions2.expression.Id;
import lf1.plp.functional1.util.TipoPolimorfico;

/** Identidade nominal por declaração, inclusive sob sombreamento de nomes. */
public final class TipoRegistro extends TipoComposto {
    private final String nome;
    private final Map<String, Tipo> campos;

    public TipoRegistro(String nome, Map<String, Tipo> campos) {
        this.nome = nome;
        this.campos = Collections.unmodifiableMap(new LinkedHashMap<>(campos));
    }

    // ':' não pertence a identificadores da linguagem: tipos não colidem com variáveis.
    public static Id chave(String nome) { return new Id("record:" + nome); }
    public Map<String, Tipo> campos() { return campos; }
    public String getNome() { return nome; }
    public boolean eIgual(Tipo outro) {
        return outro instanceof TipoPolimorfico ? outro.eIgual(this) : this == outro;
    }
}
