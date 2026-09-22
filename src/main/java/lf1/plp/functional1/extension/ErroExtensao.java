package lf1.plp.functional1.extension;

/** Erro de tipo ou de execução das construções da extensão. */
public class ErroExtensao extends RuntimeException {
    public ErroExtensao(String mensagem) { super(mensagem); }
}
