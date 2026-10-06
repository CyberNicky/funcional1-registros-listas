package lf1.plp.functional1.util;

import java.util.*;
import lf1.plp.expressions1.util.Tipo;
import lf1.plp.functional1.extension.*;

/** Unificação com restrições de campos e instanciação por chamada, sem estado global. */
public final class Inferencia {
    private Inferencia() {}

    public static Tipo resolver(Tipo tipo) {
        if (tipo instanceof TipoPolimorfico var && var.vinculo != null) {
            var.vinculo = resolver(var.vinculo);
            return var.vinculo;
        }
        return tipo;
    }

    public static boolean exigirRegistro(Tipo tipo) {
        tipo = resolver(tipo);
        if (tipo instanceof TipoPolimorfico var) {
            var.restricao = TipoPolimorfico.Restricao.REGISTRO;
            return true;
        }
        return tipo instanceof TipoRegistro;
    }

    public static Tipo campo(Tipo tipo, String nome) {
        tipo = resolver(tipo);
        if (!exigirRegistro(tipo)) throw new ErroExtensao("Acesso a campo exige um registro.");
        if (tipo instanceof TipoRegistro registro) {
            Tipo campo = registro.campos().get(nome);
            if (campo == null) throw new ErroExtensao("Campo inexistente '" + nome + "' em " + registro.getNome());
            return campo;
        }
        TipoPolimorfico var = (TipoPolimorfico) tipo;
        return var.campos.computeIfAbsent(nome, ignorado -> new TipoPolimorfico());
    }

    public static TipoLista lista(Tipo tipo) {
        tipo = resolver(tipo);
        if (tipo instanceof TipoLista lista) return lista;
        TipoLista lista = new TipoLista(new TipoPolimorfico());
        if (tipo instanceof TipoPolimorfico && unificar(tipo, lista)) return lista;
        throw new ErroExtensao("Operação de lista exige uma lista.");
    }

    public static boolean unificar(Tipo a, Tipo b) {
        a = resolver(a);
        b = resolver(b);
        if (a == null || b == null) return false;
        if (a == b) return true;
        if (a instanceof TipoPolimorfico var) return vincular(var, b);
        if (b instanceof TipoPolimorfico var) return vincular(var, a);
        if (a instanceof TipoLista la && b instanceof TipoLista lb)
            return unificar(la.elemento(), lb.elemento());
        if (a instanceof TipoFuncao fa && b instanceof TipoFuncao fb) {
            if (fa.getDominio().size() != fb.getDominio().size()) return false;
            for (int i = 0; i < fa.getDominio().size(); i++)
                if (!unificar(fa.getDominio().get(i), fb.getDominio().get(i))) return false;
            return unificar(fa.getImagem(), fb.getImagem());
        }
        // Primitivos são enums; registros têm identidade nominal por declaração.
        return false;
    }

    private static boolean vincular(TipoPolimorfico var, Tipo tipo) {
        if (ocorre(var, tipo, Collections.newSetFromMap(new IdentityHashMap<>()))) return false;
        // A Funcional 1 não possui funções como valores de primeira classe.
        if (tipo instanceof TipoFuncao) return false;
        if (tipo instanceof TipoPolimorfico outra) {
            // Ao unir restrições de campos, verifica ciclos também no sentido inverso.
            if (ocorre(outra, var, Collections.newSetFromMap(new IdentityHashMap<>()))) return false;
            if (var.restricao != TipoPolimorfico.Restricao.LIVRE
                    && outra.restricao != TipoPolimorfico.Restricao.LIVRE
                    && var.restricao != outra.restricao) return false;
            for (var campo : var.campos.entrySet()) {
                Tipo existente = outra.campos.get(campo.getKey());
                if (existente != null && !unificar(campo.getValue(), existente)) return false;
            }
            if (outra.restricao == TipoPolimorfico.Restricao.LIVRE) outra.restricao = var.restricao;
            var.campos.forEach(outra.campos::putIfAbsent);
        } else {
            if (var.restricao == TipoPolimorfico.Restricao.REGISTRO) {
                if (!(tipo instanceof TipoRegistro registro)) return false;
                for (var campo : var.campos.entrySet()) {
                    Tipo concreto = registro.campos().get(campo.getKey());
                    if (concreto == null || !unificar(campo.getValue(), concreto)) return false;
                }
            }
        }
        var.vinculo = tipo;
        return true;
    }

    private static boolean ocorre(TipoPolimorfico procurada, Tipo tipo, Set<Tipo> visitados) {
        tipo = resolver(tipo);
        if (tipo == procurada) return true;
        if (!visitados.add(tipo)) return false;
        if (tipo instanceof TipoLista lista) return ocorre(procurada, lista.elemento(), visitados);
        if (tipo instanceof TipoPolimorfico var)
            return var.campos.values().stream().anyMatch(t -> ocorre(procurada, t, visitados));
        if (tipo instanceof TipoFuncao funcao)
            return ocorre(procurada, funcao.getImagem(), visitados)
                || funcao.getDominio().stream().anyMatch(t -> ocorre(procurada, t, visitados));
        return false;
    }

    public static Set<TipoPolimorfico> livres(Tipo tipo) {
        Set<TipoPolimorfico> resultado = new HashSet<>();
        coletar(tipo, resultado, Collections.newSetFromMap(new IdentityHashMap<>()));
        return resultado;
    }

    private static void coletar(Tipo tipo, Set<TipoPolimorfico> resultado, Set<Tipo> visitados) {
        tipo = resolver(tipo);
        if (tipo == null || !visitados.add(tipo)) return;
        if (tipo instanceof TipoPolimorfico var) {
            resultado.add(var);
            var.campos.values().forEach(t -> coletar(t, resultado, visitados));
        } else if (tipo instanceof TipoLista lista) {
            coletar(lista.elemento(), resultado, visitados);
        } else if (tipo instanceof TipoFuncao funcao) {
            Set<TipoPolimorfico> locais = new HashSet<>();
            funcao.getDominio().forEach(t -> locais.addAll(livres(t)));
            locais.addAll(livres(funcao.getImagem()));
            locais.removeAll(funcao.quantificadas());
            resultado.addAll(locais);
        }
    }

    /** Copia somente as variáveis generalizadas, preservando as capturadas do ambiente. */
    public static Tipo copiar(Tipo tipo, Set<TipoPolimorfico> quantificadas,
                              Map<TipoPolimorfico, TipoPolimorfico> copias) {
        tipo = resolver(tipo);
        if (tipo instanceof TipoPolimorfico var) {
            if (!quantificadas.contains(var)) return var;
            if (copias.containsKey(var)) return copias.get(var);
            TipoPolimorfico copia = new TipoPolimorfico();
            copias.put(var, copia);
            copia.restricao = var.restricao;
            var.campos.forEach((nome, campo) -> copia.campos.put(nome, copiar(campo, quantificadas, copias)));
            return copia;
        }
        if (tipo instanceof TipoLista lista) return new TipoLista(copiar(lista.elemento(), quantificadas, copias));
        // Funções de primeira ordem: domínio e imagem não contêm valores de função.
        return tipo;
    }
}
