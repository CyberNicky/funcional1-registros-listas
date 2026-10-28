//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package lf1.plp.functional1.parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lf1.plp.expressions1.util.Tipo;
import lf1.plp.expressions1.util.TipoPrimitivo;
import lf1.plp.expressions2.expression.ExpAnd;
import lf1.plp.expressions2.expression.ExpConcat;
import lf1.plp.expressions2.expression.ExpEquals;
import lf1.plp.expressions2.expression.ExpLength;
import lf1.plp.expressions2.expression.ExpMenos;
import lf1.plp.expressions2.expression.ExpNot;
import lf1.plp.expressions2.expression.ExpOr;
import lf1.plp.expressions2.expression.ExpSoma;
import lf1.plp.expressions2.expression.ExpSub;
import lf1.plp.expressions2.expression.Expressao;
import lf1.plp.expressions2.expression.Id;
import lf1.plp.expressions2.expression.Valor;
import lf1.plp.expressions2.expression.ValorBooleano;
import lf1.plp.expressions2.expression.ValorInteiro;
import lf1.plp.expressions2.expression.ValorString;
import lf1.plp.functional1.Programa;
import lf1.plp.functional1.declaration.DecComposta;
import lf1.plp.functional1.declaration.DecFuncao;
import lf1.plp.functional1.declaration.DecVariavel;
import lf1.plp.functional1.declaration.DeclaracaoFuncional;
import lf1.plp.functional1.expression.Aplicacao;
import lf1.plp.functional1.expression.ExpDeclaracao;
import lf1.plp.functional1.expression.IfThenElse;
import lf1.plp.functional1.extension.ExpCampo;
import lf1.plp.functional1.extension.ExpDeclaracaoRegistro;
import lf1.plp.functional1.extension.ExpLista;
import lf1.plp.functional1.extension.ExpOperacaoLista;
import lf1.plp.functional1.extension.ExpRegistro;
import lf1.plp.functional1.extension.ExpressaoLocalizada;
import lf1.plp.functional1.extension.TipoLista;
import lf1.plp.functional1.extension.TipoNomeado;
import lf1.plp.functional1.extension.TipoRegistro;
import lf1.plp.functional1.extension.ExpOperacaoLista.Operacao;

public class Func1Parser implements Func1ParserConstants {
    public Func1ParserTokenManager token_source;
    JavaCharStream jj_input_stream;
    public Token token;
    public Token jj_nt;
    private int jj_ntk;
    private Token jj_scanpos;
    private Token jj_lastpos;
    private int jj_la;
    private int jj_gen;
    private final int[] jj_la1;
    private static int[] jj_la1_0;
    private static int[] jj_la1_1;
    private static int[] jj_la1_2;
    private final JJCalls[] jj_2_rtns;
    private boolean jj_rescan;
    private int jj_gc;
    private static final LookaheadSuccess jj_ls;
    private List<int[]> jj_expentries;
    private int[] jj_expentry;
    private int jj_kind;
    private int[] jj_lasttokens;
    private int jj_endpos;
    private boolean trace_enabled;

    public static void main(String[] args) {
        Func1Parser parser;
        if (args.length == 0) {
            System.out.println("Funcional 1 PLP Parser Version 0.0.1:  Reading from standard input . . .");
            parser = new Func1Parser(System.in);
        } else {
            if (args.length != 1) {
                System.out.println("Funcional 1 PLP Parser Version 0.0.1:  Usage is one of:");
                System.out.println("         java Func1Parser < inputfile");
                System.out.println("OR");
                System.out.println("         java Func1Parser inputfile");
                return;
            }

            System.out.println("Funcional 1 PLP Parser Version 0.0.1:  Reading from file " + args[0] + " . . .");

            try {
                parser = new Func1Parser(new FileInputStream(args[0]));
            } catch (FileNotFoundException var7) {
                System.out.println("Funcional 1 PLP Parser Version 0.0.1:  File " + args[0] + " not found.");
                return;
            }
        }

        Programa programa = null;

        try {
            programa = parser.Input();
            if (!programa.checaTipo()) {
                System.out.println("Funcional 1 PLP Parser Version 0.0.1: Erro de tipo.");
                System.exit(0);
            }

            System.out.println("Funcional 1 PLP Parser Version 0.0.1: Funcional1 program parsed successfully.");
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("Funcional 1 PLP Parser Version 0.0.1: Encountered errors during parse.");
            System.exit(0);
        }

        try {
            System.out.println("Funcional 1 PLP Parser Version 0.0.1: running...");
            Valor val = programa.executar();
            if (val instanceof ValorString valStr) {
                System.out.println("Funcional 1 PLP Parser Version 0.0.1: resultado=" + (String)valStr.valor());
            } else if (val instanceof ValorInteiro valInt) {
                System.out.println("Funcional 1 PLP Parser Version 0.0.1: resultado=" + String.valueOf(valInt.valor()));
            } else if (val instanceof ValorBooleano valBool) {
                System.out.println("Funcional 1 PLP Parser Version 0.0.1: resultado=" + String.valueOf(valBool.valor()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Funcional 1 PLP Parser Version 0.0.1:  Encountered errors during execution.");
        }

    }

    public final Programa Input() throws ParseException {
        Programa retorno = this.PPrograma();
        this.jj_consume_token(0);
        return retorno;
    }

    public final Valor PValorInteiro() throws ParseException {
        Token token = this.jj_consume_token(26);
        return new ValorInteiro(Integer.parseInt(token.toString()));
    }

    public final Valor PValorBooleano() throws ParseException {
        switch (this.jj_ntk == -1 ? this.jj_ntk_f() : this.jj_ntk) {
            case 13:
                this.jj_consume_token(13);
                return new ValorBooleano(true);
            case 14:
                this.jj_consume_token(14);
                return new ValorBooleano(false);
            default:
                this.jj_la1[0] = this.jj_gen;
                this.jj_consume_token(-1);
                throw new ParseException();
        }
    }

    public final Valor PValorString() throws ParseException {
        Token token = this.jj_consume_token(30);
        String tokenStr = token.toString();
        tokenStr = tokenStr.substring(1, tokenStr.length() - 1);
        return new ValorString(tokenStr);
    }

    public final Valor PValor() throws ParseException {
        Valor retorno;
        switch (this.jj_ntk == -1 ? this.jj_ntk_f() : this.jj_ntk) {
            case 13:
            case 14:
                retorno = this.PValorBooleano();
                break;
            case 26:
                retorno = this.PValorInteiro();
                break;
            case 30:
                retorno = this.PValorString();
                break;
            default:
                this.jj_la1[1] = this.jj_gen;
                this.jj_consume_token(-1);
                throw new ParseException();
        }

        return retorno;
    }

    public final Id PId() throws ParseException {
        Token token = this.jj_consume_token(31);
        String tokenStr = token.toString();
        return new Id(tokenStr);
    }

    public final Expressao PExpMenos() throws ParseException {
        this.jj_consume_token(58);
        Expressao retorno = this.PExpPrimaria();
        return new ExpMenos(retorno);
    }

    public final Expressao PExpNot() throws ParseException {
        this.jj_consume_token(11);
        Expressao retorno = this.PExpPrimaria();
        return new ExpNot(retorno);
    }

    public final Expressao PExpLength() throws ParseException {
        this.jj_consume_token(12);
        Expressao retorno = this.PExpPrimaria();
        if (retorno instanceof ValorString var2) {
            ;
        }

        return new ExpLength(retorno);
    }

    public final Expressao PExpPrimaria() throws ParseException {
        Expressao retorno;
        if (this.jj_2_1(Integer.MAX_VALUE)) {
            retorno = this.PRegistro();
        } else if (this.jj_2_2(Integer.MAX_VALUE)) {
            retorno = this.PAplicacao();
        } else {
            switch (this.jj_ntk == -1 ? this.jj_ntk_f() : this.jj_ntk) {
                case 13:
                case 14:
                case 26:
                case 30:
                    retorno = this.PValor();
                    break;
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 27:
                case 28:
                case 29:
                case 32:
                case 33:
                case 35:
                case 36:
                case 37:
                default:
                    this.jj_la1[2] = this.jj_gen;
                    this.jj_consume_token(-1);
                    throw new ParseException();
                case 23:
                case 24:
                case 25:
                    retorno = this.POperacaoLista();
                    break;
                case 31:
                    retorno = this.PId();
                    break;
                case 34:
                    this.jj_consume_token(34);
                    retorno = this.PExpressao();
                    this.jj_consume_token(35);
                    break;
                case 38:
                    retorno = this.PLista();
            }
        }

        while(true) {
            switch (this.jj_ntk == -1 ? this.jj_ntk_f() : this.jj_ntk) {
                case 42:
                    this.jj_consume_token(42);
                    Token campo = this.jj_consume_token(31);
                    retorno = new ExpressaoLocalizada(new ExpCampo(retorno, campo.image), campo.beginLine, campo.beginColumn, (String)null);
                    break;
                default:
                    this.jj_la1[3] = this.jj_gen;
                    return retorno;
            }
        }
    }

    public final Expressao PRegistro() throws ParseException {
        Token nome;
        Map<String, Expressao> campos;
        campos = new LinkedHashMap();
        nome = this.jj_consume_token(31);
        this.jj_consume_token(36);
        label30:
        switch (this.jj_ntk == -1 ? this.jj_ntk_f() : this.jj_ntk) {
            case 31:
                Token campo = this.jj_consume_token(31);
                this.jj_consume_token(49);
                Expressao valor = this.PExpressao();
                campos.put(campo.image, new ExpressaoLocalizada(valor, campo.beginLine, campo.beginColumn, (String)null));

                while(true) {
                    switch (this.jj_ntk == -1 ? this.jj_ntk_f() : this.jj_ntk) {
                        case 41:
                            this.jj_consume_token(41);
                            campo = this.jj_consume_token(31);
                            this.jj_consume_token(49);
                            valor = this.PExpressao();
                            if (campos.put(campo.image, new ExpressaoLocalizada(valor, campo.beginLine, campo.beginColumn, (String)null)) != null) {
                                throw new ParseException("Linha " + campo.beginLine + ", coluna " + campo.beginColumn + ": campo repetido: " + campo.image);
                            }
                            break;
                        default:
                            this.jj_la1[4] = this.jj_gen;
                            break label30;
                    }
                }
            default:
                this.jj_la1[5] = this.jj_gen;
        }

        this.jj_consume_token(37);
        return new ExpressaoLocalizada(new ExpRegistro(nome.image, campos), nome.beginLine, nome.beginColumn, (String)null);
    }

    public final Expressao PLista() throws ParseException {
        Token inicio;
        List<Expressao> elementos;
        elementos = new ArrayList();
        inicio = this.jj_consume_token(38);
        label28:
        switch (this.jj_ntk == -1 ? this.jj_ntk_f() : this.jj_ntk) {
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 18:
            case 23:
            case 24:
            case 25:
            case 26:
            case 30:
            case 31:
            case 34:
            case 38:
            case 58:
                Expressao valor = this.PExpressao();
                elementos.add(valor);

                while(true) {
                    switch (this.jj_ntk == -1 ? this.jj_ntk_f() : this.jj_ntk) {
                        case 41:
                            this.jj_consume_token(41);
                            valor = this.PExpressao();
                            elementos.add(valor);
                            break;
                        default:
                            this.jj_la1[6] = this.jj_gen;
                            break label28;
                    }
                }
            case 16:
            case 17:
            case 19:
            case 20:
            case 21:
            case 22:
            case 27:
            case 28:
            case 29:
            case 32:
            case 33:
            case 35:
            case 36:
            case 37:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            default:
                this.jj_la1[7] = this.jj_gen;
        }

        this.jj_consume_token(39);
        return new ExpressaoLocalizada(new ExpLista(elementos), inicio.beginLine, inicio.beginColumn, (String)null);
    }

    public final Expressao PDeclaracaoRegistro() throws ParseException {
        Token nome;
        Map<String, Tipo> campos;
        campos = new LinkedHashMap();
        this.jj_consume_token(15);
        this.jj_consume_token(22);
        nome = this.jj_consume_token(31);
        this.jj_consume_token(36);
        label30:
        switch (this.jj_ntk == -1 ? this.jj_ntk_f() : this.jj_ntk) {
            case 31:
                Token campo = this.jj_consume_token(31);
                this.jj_consume_token(49);
                Tipo tipo = this.PTipoCampo();
                campos.put(campo.image, tipo);

                while(true) {
                    switch (this.jj_ntk == -1 ? this.jj_ntk_f() : this.jj_ntk) {
                        case 41:
                            this.jj_consume_token(41);
                            campo = this.jj_consume_token(31);
                            this.jj_consume_token(49);
                            tipo = this.PTipoCampo();
                            if (campos.put(campo.image, tipo) != null) {
                                throw new ParseException("Linha " + campo.beginLine + ", coluna " + campo.beginColumn + ": campo repetido: " + campo.image);
                            }
                            break;
                        default:
                            this.jj_la1[8] = this.jj_gen;
                            break label30;
                    }
                }
            default:
                this.jj_la1[9] = this.jj_gen;
        }

        this.jj_consume_token(37);
        this.jj_consume_token(17);
        Expressao corpo = this.PExpressao();
        return new ExpressaoLocalizada(new ExpDeclaracaoRegistro(new TipoRegistro(nome.image, campos), corpo), nome.beginLine, nome.beginColumn, (String)null);
    }

    public final Tipo PTipoCampo() throws ParseException {
        switch (this.jj_ntk == -1 ? this.jj_ntk_f() : this.jj_ntk) {
            case 31:
                Token tipo = this.jj_consume_token(31);
                if (tipo.image.equals("Int")) {
                    return TipoPrimitivo.INTEIRO;
                } else if (tipo.image.equals("String")) {
                    return TipoPrimitivo.STRING;
                } else {
                    if (tipo.image.equals("Boolean")) {
                        return TipoPrimitivo.BOOLEANO;
                    }

                    return new TipoNomeado(tipo.image, tipo.beginLine, tipo.beginColumn);
                }
            case 38:
                this.jj_consume_token(38);
                Tipo elemento = this.PTipoCampo();
                this.jj_consume_token(39);
                return new TipoLista(elemento);
            default:
                this.jj_la1[10] = this.jj_gen;
                this.jj_consume_token(-1);
                throw new ParseException();
        }
    }

    public final List PListaId() throws ParseException {
        List<Id> retorno = null;

        while(true) {
            switch (this.jj_ntk == -1 ? this.jj_ntk_f() : this.jj_ntk) {
                case 31:
                    Id id = this.PId();
                    if (retorno == null) {
                        retorno = new ArrayList();
                        retorno.add(id);
                    } else {
                        retorno.add(id);
                    }
                    break;
                default:
                    this.jj_la1[11] = this.jj_gen;
                    return retorno;
            }
        }
    }

    public final DeclaracaoFuncional PDeclVar() throws ParseException {
        this.jj_consume_token(16);
        Id id = this.PId();
        this.jj_consume_token(43);
        Expressao expressao = this.PExpressao();
        return new DecVariavel(id, expressao);
    }

    public final DeclaracaoFuncional PDeclFuncao() throws ParseException {
        this.jj_consume_token(21);
        Id id = this.PId();
        List<Id> lista = this.PListaId();
        this.jj_consume_token(43);
        Expressao expressao = this.PExpressao();
        return new DecFuncao(id, lista, expressao);
    }

    public final DecComposta PDecComposta() throws ParseException {
        DeclaracaoFuncional d1;
        switch (this.jj_ntk == -1 ? this.jj_ntk_f() : this.jj_ntk) {
            case 16:
                d1 = this.PDeclVar();
                break;
            case 21:
                d1 = this.PDeclFuncao();
                break;
            default:
                this.jj_la1[12] = this.jj_gen;
                this.jj_consume_token(-1);
                throw new ParseException();
        }

        this.jj_consume_token(41);
        DeclaracaoFuncional d2 = this.PDeclFuncional();
        return new DecComposta(d1, d2);
    }

    public final DeclaracaoFuncional PDeclFuncional() throws ParseException {
        DeclaracaoFuncional decl;
        if (this.jj_2_3(Integer.MAX_VALUE)) {
            decl = this.PDecComposta();
        } else if (this.jj_2_4(Integer.MAX_VALUE)) {
            decl = this.PDecComposta();
        } else {
            switch (this.jj_ntk == -1 ? this.jj_ntk_f() : this.jj_ntk) {
                case 16:
                    decl = this.PDeclVar();
                    break;
                case 21:
                    decl = this.PDeclFuncao();
                    break;
                case 34:
                    this.jj_consume_token(34);
                    decl = this.PDeclFuncional();
                    this.jj_consume_token(35);
                    break;
                default:
                    this.jj_la1[13] = this.jj_gen;
                    this.jj_consume_token(-1);
                    throw new ParseException();
            }
        }

        return decl;
    }

    public final Expressao PExpDeclaracao() throws ParseException {
        this.jj_consume_token(15);
        DeclaracaoFuncional declaracoes = this.PDeclFuncional();
        this.jj_consume_token(17);
        Expressao expressao = this.PExpressao();
        return new ExpDeclaracao(declaracoes, expressao);
    }

    public final Expressao PExpCondicional() throws ParseException {
        this.jj_consume_token(18);
        Expressao expCond = this.PExpressao();
        this.jj_consume_token(19);
        Expressao expThen = this.PExpressao();
        this.jj_consume_token(20);
        Expressao expElse = this.PExpressao();
        return new IfThenElse(expCond, expThen, expElse);
    }

    public final Expressao PAplicacao() throws ParseException {
        List expressoes = null;
        Token inicio = this.getToken(1);
        Id id = this.PId();
        this.jj_consume_token(34);
        Expressao expressao = this.PExpressao();
        expressoes = new ArrayList();
        expressoes.add(expressao);

        while(true) {
            switch (this.jj_ntk == -1 ? this.jj_ntk_f() : this.jj_ntk) {
                case 41:
                    this.jj_consume_token(41);
                    expressao = this.PExpressao();
                    expressoes.add(expressao);
                    break;
                default:
                    this.jj_la1[14] = this.jj_gen;
                    this.jj_consume_token(35);
                    return new ExpressaoLocalizada(new Aplicacao(id, expressoes), inicio.beginLine, inicio.beginColumn, "função '" + String.valueOf(id) + "'");
            }
        }
    }

    public final Expressao PExpUnaria() throws ParseException {
        Expressao retorno;
        if (this.jj_2_5(Integer.MAX_VALUE)) {
            retorno = this.PExpMenos();
        } else if (this.jj_2_6(Integer.MAX_VALUE)) {
            retorno = this.PExpNot();
        } else if (this.jj_2_7(Integer.MAX_VALUE)) {
            retorno = this.PExpLength();
        } else if (this.jj_2_8(Integer.MAX_VALUE)) {
            retorno = this.PDeclaracaoRegistro();
        } else if (this.jj_2_9(Integer.MAX_VALUE)) {
            retorno = this.PExpDeclaracao();
        } else if (this.jj_2_10(Integer.MAX_VALUE)) {
            retorno = this.PExpCondicional();
        } else {
            if (!this.jj_2_11(Integer.MAX_VALUE)) {
                this.jj_consume_token(-1);
                throw new ParseException();
            }

            retorno = this.PExpPrimaria();
        }

        return retorno;
    }

    public final Expressao PExpBinaria() throws ParseException {
        Expressao retorno = this.PExpBinaria2();

        while(true) {
            switch (this.jj_ntk == -1 ? this.jj_ntk_f() : this.jj_ntk) {
                case 50:
                    this.jj_consume_token(50);
                    Expressao param2 = this.PExpBinaria2();
                    retorno = new ExpEquals(retorno, param2);
                    break;
                default:
                    this.jj_la1[15] = this.jj_gen;
                    return retorno;
            }
        }
    }

    public final Expressao PExpBinaria2() throws ParseException {
        Expressao retorno = this.PExpBinaria3();

        while(true) {
            switch (this.jj_ntk == -1 ? this.jj_ntk_f() : this.jj_ntk) {
                case 10:
                case 56:
                case 57:
                case 58:
                    Expressao param2 = null;
                    switch (this.jj_ntk == -1 ? this.jj_ntk_f() : this.jj_ntk) {
                        case 10:
                            this.jj_consume_token(10);
                            param2 = this.PExpBinaria3();
                            retorno = new ExpOr(retorno, param2);
                            continue;
                        case 56:
                            this.jj_consume_token(56);
                            param2 = this.PExpBinaria3();
                            retorno = new ExpConcat(retorno, param2);
                            continue;
                        case 57:
                            this.jj_consume_token(57);
                            param2 = this.PExpBinaria3();
                            retorno = new ExpSoma(retorno, param2);
                            continue;
                        case 58:
                            this.jj_consume_token(58);
                            param2 = this.PExpBinaria3();
                            retorno = new ExpSub(retorno, param2);
                            continue;
                        default:
                            this.jj_la1[17] = this.jj_gen;
                            this.jj_consume_token(-1);
                            throw new ParseException();
                    }
                default:
                    this.jj_la1[16] = this.jj_gen;
                    return retorno;
            }
        }
    }

    public final Expressao PExpBinaria3() throws ParseException {
        Expressao retorno = this.PExpUnaria();

        while(true) {
            switch (this.jj_ntk == -1 ? this.jj_ntk_f() : this.jj_ntk) {
                case 9:
                    this.jj_consume_token(9);
                    Expressao param2 = this.PExpUnaria();
                    retorno = new ExpAnd(retorno, param2);
                    break;
                default:
                    this.jj_la1[18] = this.jj_gen;
                    return retorno;
            }
        }
    }

    public final Expressao PExpressao() throws ParseException {
        Expressao retorno = this.PExpBinaria();
        return retorno;
    }

    public final Programa PPrograma() throws ParseException {
        Expressao retorno = this.PExpressao();
        return new Programa(retorno);
    }

    public final Expressao POperacaoLista() throws ParseException {
        ExpOperacaoLista.Operacao op;
        Token inicio;
        switch (this.jj_ntk == -1 ? this.jj_ntk_f() : this.jj_ntk) {
            case 23:
                inicio = this.jj_consume_token(23);
                op = Operacao.HEAD;
                break;
            case 24:
                inicio = this.jj_consume_token(24);
                op = Operacao.TAIL;
                break;
            case 25:
                inicio = this.jj_consume_token(25);
                op = Operacao.IS_EMPTY;
                break;
            default:
                this.jj_la1[19] = this.jj_gen;
                this.jj_consume_token(-1);
                throw new ParseException();
        }

        this.jj_consume_token(34);
        Expressao argumento = this.PExpressao();
        this.jj_consume_token(35);
        return new ExpressaoLocalizada(new ExpOperacaoLista(op, argumento), inicio.beginLine, inicio.beginColumn, (String)null);
    }

    private boolean jj_2_1(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;

        boolean var3;
        try {
            boolean var2 = !this.jj_3_1();
            return var2;
        } catch (LookaheadSuccess var7) {
            var3 = true;
        } finally {
            this.jj_save(0, xla);
        }

        return var3;
    }

    private boolean jj_2_2(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;

        boolean var3;
        try {
            boolean var2 = !this.jj_3_2();
            return var2;
        } catch (LookaheadSuccess var7) {
            var3 = true;
        } finally {
            this.jj_save(1, xla);
        }

        return var3;
    }

    private boolean jj_2_3(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;

        boolean var3;
        try {
            boolean var2 = !this.jj_3_3();
            return var2;
        } catch (LookaheadSuccess var7) {
            var3 = true;
        } finally {
            this.jj_save(2, xla);
        }

        return var3;
    }

    private boolean jj_2_4(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;

        boolean var3;
        try {
            boolean var2 = !this.jj_3_4();
            return var2;
        } catch (LookaheadSuccess var7) {
            var3 = true;
        } finally {
            this.jj_save(3, xla);
        }

        return var3;
    }

    private boolean jj_2_5(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;

        boolean var3;
        try {
            boolean var2 = !this.jj_3_5();
            return var2;
        } catch (LookaheadSuccess var7) {
            var3 = true;
        } finally {
            this.jj_save(4, xla);
        }

        return var3;
    }

    private boolean jj_2_6(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;

        boolean var3;
        try {
            boolean var2 = !this.jj_3_6();
            return var2;
        } catch (LookaheadSuccess var7) {
            var3 = true;
        } finally {
            this.jj_save(5, xla);
        }

        return var3;
    }

    private boolean jj_2_7(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;

        boolean var3;
        try {
            boolean var2 = !this.jj_3_7();
            return var2;
        } catch (LookaheadSuccess var7) {
            var3 = true;
        } finally {
            this.jj_save(6, xla);
        }

        return var3;
    }

    private boolean jj_2_8(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;

        boolean var3;
        try {
            boolean var2 = !this.jj_3_8();
            return var2;
        } catch (LookaheadSuccess var7) {
            var3 = true;
        } finally {
            this.jj_save(7, xla);
        }

        return var3;
    }

    private boolean jj_2_9(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;

        boolean var3;
        try {
            boolean var2 = !this.jj_3_9();
            return var2;
        } catch (LookaheadSuccess var7) {
            var3 = true;
        } finally {
            this.jj_save(8, xla);
        }

        return var3;
    }

    private boolean jj_2_10(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;

        boolean var3;
        try {
            boolean var2 = !this.jj_3_10();
            return var2;
        } catch (LookaheadSuccess var7) {
            var3 = true;
        } finally {
            this.jj_save(9, xla);
        }

        return var3;
    }

    private boolean jj_2_11(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;

        boolean var3;
        try {
            boolean var2 = !this.jj_3_11();
            return var2;
        } catch (LookaheadSuccess var7) {
            var3 = true;
        } finally {
            this.jj_save(10, xla);
        }

        return var3;
    }

    private boolean jj_3R_PExpCondicional_564_3_16() {
        if (this.jj_scan_token(18)) {
            return true;
        } else if (this.jj_3R_PExpressao_699_3_19()) {
            return true;
        } else if (this.jj_scan_token(19)) {
            return true;
        } else if (this.jj_3R_PExpressao_699_3_19()) {
            return true;
        } else if (this.jj_scan_token(20)) {
            return true;
        } else {
            return this.jj_3R_PExpressao_699_3_19();
        }
    }

    private boolean jj_3R_PExpLength_362_3_14() {
        if (this.jj_scan_token(12)) {
            return true;
        } else {
            return this.jj_3R_PExpPrimaria_380_3_17();
        }
    }

    private boolean jj_3R_PExpDeclaracao_553_3_15() {
        if (this.jj_scan_token(15)) {
            return true;
        } else if (this.jj_3R_PDeclFuncional_533_3_21()) {
            return true;
        } else if (this.jj_scan_token(17)) {
            return true;
        } else {
            return this.jj_3R_PExpressao_699_3_19();
        }
    }

    private boolean jj_3_4() {
        if (this.jj_3R_PDeclFuncao_506_3_11()) {
            return true;
        } else {
            return this.jj_scan_token(41);
        }
    }

    private boolean jj_3_3() {
        if (this.jj_3R_PDeclVar_490_3_10()) {
            return true;
        } else {
            return this.jj_scan_token(41);
        }
    }

    private boolean jj_3R_PExpNot_351_3_13() {
        if (this.jj_scan_token(11)) {
            return true;
        } else {
            return this.jj_3R_PExpPrimaria_380_3_17();
        }
    }

    private boolean jj_3R_PDeclFuncional_540_5_36() {
        if (this.jj_scan_token(34)) {
            return true;
        } else if (this.jj_3R_PDeclFuncional_533_3_21()) {
            return true;
        } else {
            return this.jj_scan_token(35);
        }
    }

    private boolean jj_3R_PDeclFuncional_539_5_35() {
        return this.jj_3R_PDeclFuncao_506_3_11();
    }

    private boolean jj_3R_PDeclFuncional_538_5_34() {
        return this.jj_3R_PDeclVar_490_3_10();
    }

    private boolean jj_3R_PDeclFuncional_536_5_33() {
        return this.jj_3R_PDecComposta_520_9_44();
    }

    private boolean jj_3R_POperacaoLista_726_5_49() {
        return this.jj_scan_token(25);
    }

    private boolean jj_3R_PDeclFuncional_534_5_32() {
        return this.jj_3R_PDecComposta_520_9_44();
    }

    private boolean jj_3R_POperacaoLista_725_5_48() {
        return this.jj_scan_token(24);
    }

    private boolean jj_3R_POperacaoLista_724_5_47() {
        return this.jj_scan_token(23);
    }

    private boolean jj_3R_PDeclFuncional_533_3_21() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_PDeclFuncional_534_5_32()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_PDeclFuncional_536_5_33()) {
                this.jj_scanpos = xsp;
                if (this.jj_3R_PDeclFuncional_538_5_34()) {
                    this.jj_scanpos = xsp;
                    if (this.jj_3R_PDeclFuncional_539_5_35()) {
                        this.jj_scanpos = xsp;
                        if (this.jj_3R_PDeclFuncional_540_5_36()) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    private boolean jj_3R_POperacaoLista_724_3_39() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_POperacaoLista_724_5_47()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_POperacaoLista_725_5_48()) {
                this.jj_scanpos = xsp;
                if (this.jj_3R_POperacaoLista_726_5_49()) {
                    return true;
                }
            }
        }

        if (this.jj_scan_token(34)) {
            return true;
        } else if (this.jj_3R_PExpressao_699_3_19()) {
            return true;
        } else {
            return this.jj_scan_token(35);
        }
    }

    private boolean jj_3R_PExpMenos_340_3_12() {
        if (this.jj_scan_token(58)) {
            return true;
        } else {
            return this.jj_3R_PExpPrimaria_380_3_17();
        }
    }

    private boolean jj_3R_PDecComposta_522_11_57() {
        return this.jj_3R_PDeclFuncao_506_3_11();
    }

    private boolean jj_3R_PDecComposta_521_11_56() {
        return this.jj_3R_PDeclVar_490_3_10();
    }

    private boolean jj_3R_PDecComposta_520_9_44() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_PDecComposta_521_11_56()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_PDecComposta_522_11_57()) {
                return true;
            }
        }

        if (this.jj_scan_token(41)) {
            return true;
        } else {
            return this.jj_3R_PDeclFuncional_533_3_21();
        }
    }

    private boolean jj_3R_PId_325_3_18() {
        return this.jj_scan_token(31);
    }

    private boolean jj_3R_PExpressao_699_3_19() {
        return this.jj_3R_PExpBinaria_630_3_30();
    }

    private boolean jj_3R_PValor_313_5_53() {
        return this.jj_3R_PValorString_296_3_62();
    }

    private boolean jj_3R_PDeclFuncao_506_3_11() {
        if (this.jj_scan_token(21)) {
            return true;
        } else if (this.jj_3R_PId_325_3_18()) {
            return true;
        } else if (this.jj_3R_PListaId_464_3_20()) {
            return true;
        } else if (this.jj_scan_token(43)) {
            return true;
        } else {
            return this.jj_3R_PExpressao_699_3_19();
        }
    }

    private boolean jj_3R_PValor_312_5_52() {
        return this.jj_3R_PValorBooleano_281_3_61();
    }

    private boolean jj_3R_PValor_311_5_51() {
        return this.jj_3R_PValorInteiro_271_3_60();
    }

    private boolean jj_3R_PValor_310_3_41() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_PValor_311_5_51()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_PValor_312_5_52()) {
                this.jj_scanpos = xsp;
                if (this.jj_3R_PValor_313_5_53()) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean jj_3R_PExpBinaria3_683_7_64() {
        if (this.jj_scan_token(9)) {
            return true;
        } else {
            return this.jj_3R_PExpUnaria_603_3_63();
        }
    }

    private boolean jj_3R_PDeclVar_490_3_10() {
        if (this.jj_scan_token(16)) {
            return true;
        } else if (this.jj_3R_PId_325_3_18()) {
            return true;
        } else if (this.jj_scan_token(43)) {
            return true;
        } else {
            return this.jj_3R_PExpressao_699_3_19();
        }
    }

    private boolean jj_3R_PExpBinaria3_680_3_54() {
        if (this.jj_3R_PExpUnaria_603_3_63()) {
            return true;
        } else {
            Token xsp;
            do {
                xsp = this.jj_scanpos;
            } while(!this.jj_3R_PExpBinaria3_683_7_64());

            this.jj_scanpos = xsp;
            return false;
        }
    }

    private boolean jj_3R_PValorString_296_3_62() {
        return this.jj_scan_token(30);
    }

    private boolean jj_3R_PExpBinaria2_664_7_68() {
        if (this.jj_scan_token(56)) {
            return true;
        } else {
            return this.jj_3R_PExpBinaria3_680_3_54();
        }
    }

    private boolean jj_3R_PValorBooleano_285_3_70() {
        return this.jj_scan_token(13);
    }

    private boolean jj_3R_PExpBinaria2_660_7_67() {
        if (this.jj_scan_token(10)) {
            return true;
        } else {
            return this.jj_3R_PExpBinaria3_680_3_54();
        }
    }

    private boolean jj_3R_PValorBooleano_281_3_61() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_PValorBooleano_281_3_69()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_PValorBooleano_285_3_70()) {
                return true;
            }
        }

        return false;
    }

    private boolean jj_3R_PValorBooleano_281_3_69() {
        return this.jj_scan_token(14);
    }

    private boolean jj_3R_PExpBinaria2_656_7_66() {
        if (this.jj_scan_token(58)) {
            return true;
        } else {
            return this.jj_3R_PExpBinaria3_680_3_54();
        }
    }

    private boolean jj_3R_PListaId_465_5_31() {
        return this.jj_3R_PId_325_3_18();
    }

    private boolean jj_3R_PExpBinaria2_652_7_55() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_PExpBinaria2_652_7_65()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_PExpBinaria2_656_7_66()) {
                this.jj_scanpos = xsp;
                if (this.jj_3R_PExpBinaria2_660_7_67()) {
                    this.jj_scanpos = xsp;
                    if (this.jj_3R_PExpBinaria2_664_7_68()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean jj_3R_PExpBinaria2_652_7_65() {
        if (this.jj_scan_token(57)) {
            return true;
        } else {
            return this.jj_3R_PExpBinaria3_680_3_54();
        }
    }

    private boolean jj_3R_PListaId_464_3_20() {
        Token xsp;
        do {
            xsp = this.jj_scanpos;
        } while(!this.jj_3R_PListaId_465_5_31());

        this.jj_scanpos = xsp;
        return false;
    }

    private boolean jj_3R_PValorInteiro_271_3_60() {
        return this.jj_scan_token(26);
    }

    private boolean jj_3R_PExpBinaria2_649_3_42() {
        if (this.jj_3R_PExpBinaria3_680_3_54()) {
            return true;
        } else {
            Token xsp;
            do {
                xsp = this.jj_scanpos;
            } while(!this.jj_3R_PExpBinaria2_652_7_55());

            this.jj_scanpos = xsp;
            return false;
        }
    }

    private boolean jj_3R_PTipoCampo_454_3_83() {
        if (this.jj_scan_token(38)) {
            return true;
        } else if (this.jj_3R_PTipoCampo_447_3_80()) {
            return true;
        } else {
            return this.jj_scan_token(39);
        }
    }

    private boolean jj_3R_PTipoCampo_447_3_82() {
        return this.jj_scan_token(31);
    }

    private boolean jj_3R_PTipoCampo_447_3_80() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_PTipoCampo_447_3_82()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_PTipoCampo_454_3_83()) {
                return true;
            }
        }

        return false;
    }

    private boolean jj_3R_PExpBinaria_633_7_43() {
        if (this.jj_scan_token(50)) {
            return true;
        } else {
            return this.jj_3R_PExpBinaria2_649_3_42();
        }
    }

    private boolean jj_3R_PDeclaracaoRegistro_437_7_81() {
        if (this.jj_scan_token(41)) {
            return true;
        } else if (this.jj_scan_token(31)) {
            return true;
        } else if (this.jj_scan_token(49)) {
            return true;
        } else {
            return this.jj_3R_PTipoCampo_447_3_80();
        }
    }

    private boolean jj_3R_PExpBinaria_630_3_30() {
        if (this.jj_3R_PExpBinaria2_649_3_42()) {
            return true;
        } else {
            Token xsp;
            do {
                xsp = this.jj_scanpos;
            } while(!this.jj_3R_PExpBinaria_633_7_43());

            this.jj_scanpos = xsp;
            return false;
        }
    }

    private boolean jj_3R_PDeclaracaoRegistro_435_5_79() {
        if (this.jj_scan_token(31)) {
            return true;
        } else if (this.jj_scan_token(49)) {
            return true;
        } else if (this.jj_3R_PTipoCampo_447_3_80()) {
            return true;
        } else {
            Token xsp;
            do {
                xsp = this.jj_scanpos;
            } while(!this.jj_3R_PDeclaracaoRegistro_437_7_81());

            this.jj_scanpos = xsp;
            return false;
        }
    }

    private boolean jj_3_11() {
        return this.jj_3R_PExpPrimaria_380_3_17();
    }

    private boolean jj_3_10() {
        return this.jj_3R_PExpCondicional_564_3_16();
    }

    private boolean jj_3R_PDeclaracaoRegistro_434_3_78() {
        if (this.jj_scan_token(15)) {
            return true;
        } else if (this.jj_scan_token(22)) {
            return true;
        } else if (this.jj_scan_token(31)) {
            return true;
        } else if (this.jj_scan_token(36)) {
            return true;
        } else {
            Token xsp = this.jj_scanpos;
            if (this.jj_3R_PDeclaracaoRegistro_435_5_79()) {
                this.jj_scanpos = xsp;
            }

            if (this.jj_scan_token(37)) {
                return true;
            } else if (this.jj_scan_token(17)) {
                return true;
            } else {
                return this.jj_3R_PExpressao_699_3_19();
            }
        }
    }

    private boolean jj_3_9() {
        return this.jj_3R_PExpDeclaracao_553_3_15();
    }

    private boolean jj_3_8() {
        if (this.jj_scan_token(15)) {
            return true;
        } else {
            return this.jj_scan_token(22);
        }
    }

    private boolean jj_3_7() {
        return this.jj_3R_PExpLength_362_3_14();
    }

    private boolean jj_3R_PExpUnaria_616_5_77() {
        return this.jj_3R_PExpPrimaria_380_3_17();
    }

    private boolean jj_3_6() {
        return this.jj_3R_PExpNot_351_3_13();
    }

    private boolean jj_3R_PExpUnaria_614_5_76() {
        return this.jj_3R_PExpCondicional_564_3_16();
    }

    private boolean jj_3R_PLista_421_7_59() {
        if (this.jj_scan_token(41)) {
            return true;
        } else {
            return this.jj_3R_PExpressao_699_3_19();
        }
    }

    private boolean jj_3_5() {
        return this.jj_3R_PExpMenos_340_3_12();
    }

    private boolean jj_3R_PExpUnaria_612_5_75() {
        return this.jj_3R_PExpDeclaracao_553_3_15();
    }

    private boolean jj_3R_PLista_420_5_50() {
        if (this.jj_3R_PExpressao_699_3_19()) {
            return true;
        } else {
            Token xsp;
            do {
                xsp = this.jj_scanpos;
            } while(!this.jj_3R_PLista_421_7_59());

            this.jj_scanpos = xsp;
            return false;
        }
    }

    private boolean jj_3R_PExpUnaria_610_5_74() {
        return this.jj_3R_PDeclaracaoRegistro_434_3_78();
    }

    private boolean jj_3R_PExpUnaria_608_5_73() {
        return this.jj_3R_PExpLength_362_3_14();
    }

    private boolean jj_3R_PLista_419_3_40() {
        if (this.jj_scan_token(38)) {
            return true;
        } else {
            Token xsp = this.jj_scanpos;
            if (this.jj_3R_PLista_420_5_50()) {
                this.jj_scanpos = xsp;
            }

            return this.jj_scan_token(39);
        }
    }

    private boolean jj_3R_PExpUnaria_606_5_72() {
        return this.jj_3R_PExpNot_351_3_13();
    }

    private boolean jj_3R_PExpUnaria_604_5_71() {
        return this.jj_3R_PExpMenos_340_3_12();
    }

    private boolean jj_3R_PExpUnaria_603_3_63() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_PExpUnaria_604_5_71()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_PExpUnaria_606_5_72()) {
                this.jj_scanpos = xsp;
                if (this.jj_3R_PExpUnaria_608_5_73()) {
                    this.jj_scanpos = xsp;
                    if (this.jj_3R_PExpUnaria_610_5_74()) {
                        this.jj_scanpos = xsp;
                        if (this.jj_3R_PExpUnaria_612_5_75()) {
                            this.jj_scanpos = xsp;
                            if (this.jj_3R_PExpUnaria_614_5_76()) {
                                this.jj_scanpos = xsp;
                                if (this.jj_3R_PExpUnaria_616_5_77()) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    private boolean jj_3R_PRegistro_404_7_58() {
        if (this.jj_scan_token(41)) {
            return true;
        } else if (this.jj_scan_token(31)) {
            return true;
        } else if (this.jj_scan_token(49)) {
            return true;
        } else {
            return this.jj_3R_PExpressao_699_3_19();
        }
    }

    private boolean jj_3R_PRegistro_402_5_45() {
        if (this.jj_scan_token(31)) {
            return true;
        } else if (this.jj_scan_token(49)) {
            return true;
        } else if (this.jj_3R_PExpressao_699_3_19()) {
            return true;
        } else {
            Token xsp;
            do {
                xsp = this.jj_scanpos;
            } while(!this.jj_3R_PRegistro_404_7_58());

            this.jj_scanpos = xsp;
            return false;
        }
    }

    private boolean jj_3R_PRegistro_400_3_37() {
        if (this.jj_scan_token(31)) {
            return true;
        } else if (this.jj_scan_token(36)) {
            return true;
        } else {
            Token xsp = this.jj_scanpos;
            if (this.jj_3R_PRegistro_402_5_45()) {
                this.jj_scanpos = xsp;
            }

            return this.jj_scan_token(37);
        }
    }

    private boolean jj_3R_PAplicacao_586_7_46() {
        if (this.jj_scan_token(41)) {
            return true;
        } else {
            return this.jj_3R_PExpressao_699_3_19();
        }
    }

    private boolean jj_3_2() {
        if (this.jj_scan_token(31)) {
            return true;
        } else {
            return this.jj_scan_token(34);
        }
    }

    private boolean jj_3_1() {
        if (this.jj_scan_token(31)) {
            return true;
        } else {
            return this.jj_scan_token(36);
        }
    }

    private boolean jj_3R_PExpPrimaria_389_5_29() {
        if (this.jj_scan_token(42)) {
            return true;
        } else {
            return this.jj_scan_token(31);
        }
    }

    private boolean jj_3R_PExpPrimaria_387_5_28() {
        if (this.jj_scan_token(34)) {
            return true;
        } else if (this.jj_3R_PExpressao_699_3_19()) {
            return true;
        } else {
            return this.jj_scan_token(35);
        }
    }

    private boolean jj_3R_PExpPrimaria_386_5_27() {
        return this.jj_3R_PId_325_3_18();
    }

    private boolean jj_3R_PExpPrimaria_385_5_26() {
        return this.jj_3R_PValor_310_3_41();
    }

    private boolean jj_3R_PAplicacao_578_3_38() {
        if (this.jj_3R_PId_325_3_18()) {
            return true;
        } else if (this.jj_scan_token(34)) {
            return true;
        } else if (this.jj_3R_PExpressao_699_3_19()) {
            return true;
        } else {
            Token xsp;
            do {
                xsp = this.jj_scanpos;
            } while(!this.jj_3R_PAplicacao_586_7_46());

            this.jj_scanpos = xsp;
            return this.jj_scan_token(35);
        }
    }

    private boolean jj_3R_PExpPrimaria_384_5_25() {
        return this.jj_3R_PLista_419_3_40();
    }

    private boolean jj_3R_PExpPrimaria_383_5_24() {
        return this.jj_3R_POperacaoLista_724_3_39();
    }

    private boolean jj_3R_PExpPrimaria_382_5_23() {
        return this.jj_3R_PAplicacao_578_3_38();
    }

    private boolean jj_3R_PExpPrimaria_381_5_22() {
        return this.jj_3R_PRegistro_400_3_37();
    }

    private boolean jj_3R_PExpPrimaria_380_3_17() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_PExpPrimaria_381_5_22()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_PExpPrimaria_382_5_23()) {
                this.jj_scanpos = xsp;
                if (this.jj_3R_PExpPrimaria_383_5_24()) {
                    this.jj_scanpos = xsp;
                    if (this.jj_3R_PExpPrimaria_384_5_25()) {
                        this.jj_scanpos = xsp;
                        if (this.jj_3R_PExpPrimaria_385_5_26()) {
                            this.jj_scanpos = xsp;
                            if (this.jj_3R_PExpPrimaria_386_5_27()) {
                                this.jj_scanpos = xsp;
                                if (this.jj_3R_PExpPrimaria_387_5_28()) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }

        do {
            xsp = this.jj_scanpos;
        } while(!this.jj_3R_PExpPrimaria_389_5_29());

        this.jj_scanpos = xsp;
        return false;
    }

    private static void jj_la1_init_0() {
        jj_la1_0 = new int[]{24576, 1140875264, -947888128, 0, 0, Integer.MIN_VALUE, 0, -947587072, 0, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, 2162688, 2162688, 0, 0, 1024, 1024, 512, 58720256};
    }

    private static void jj_la1_init_1() {
        jj_la1_1 = new int[]{0, 0, 68, 1024, 512, 0, 512, 67108932, 512, 0, 64, 0, 0, 4, 512, 262144, 117440512, 117440512, 0, 0};
    }

    private static void jj_la1_init_2() {
        jj_la1_2 = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    }

    public Func1Parser(InputStream stream) {
        this(stream, (String)null);
    }

    public Func1Parser(InputStream stream, String encoding) {
        this.jj_la1 = new int[20];
        this.jj_2_rtns = new JJCalls[11];
        this.jj_rescan = false;
        this.jj_gc = 0;
        this.jj_expentries = new ArrayList();
        this.jj_kind = -1;
        this.jj_lasttokens = new int[100];

        try {
            this.jj_input_stream = new JavaCharStream(stream, encoding, 1, 1);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        this.token_source = new Func1ParserTokenManager(this.jj_input_stream);
        this.token = new Token();
        this.jj_ntk = -1;
        this.jj_gen = 0;

        for(int i = 0; i < 20; ++i) {
            this.jj_la1[i] = -1;
        }

        for(int i = 0; i < this.jj_2_rtns.length; ++i) {
            this.jj_2_rtns[i] = new JJCalls();
        }

    }

    public void ReInit(InputStream stream) {
        this.ReInit(stream, (String)null);
    }

    public void ReInit(InputStream stream, String encoding) {
        try {
            this.jj_input_stream.ReInit(stream, encoding, 1, 1);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        this.token_source.ReInit(this.jj_input_stream);
        this.token = new Token();
        this.jj_ntk = -1;
        this.jj_gen = 0;

        for(int i = 0; i < 20; ++i) {
            this.jj_la1[i] = -1;
        }

        for(int i = 0; i < this.jj_2_rtns.length; ++i) {
            this.jj_2_rtns[i] = new JJCalls();
        }

    }

    public Func1Parser(Reader stream) {
        this.jj_la1 = new int[20];
        this.jj_2_rtns = new JJCalls[11];
        this.jj_rescan = false;
        this.jj_gc = 0;
        this.jj_expentries = new ArrayList();
        this.jj_kind = -1;
        this.jj_lasttokens = new int[100];
        this.jj_input_stream = new JavaCharStream(stream, 1, 1);
        this.token_source = new Func1ParserTokenManager(this.jj_input_stream);
        this.token = new Token();
        this.jj_ntk = -1;
        this.jj_gen = 0;

        for(int i = 0; i < 20; ++i) {
            this.jj_la1[i] = -1;
        }

        for(int i = 0; i < this.jj_2_rtns.length; ++i) {
            this.jj_2_rtns[i] = new JJCalls();
        }

    }

    public void ReInit(Reader stream) {
        if (this.jj_input_stream == null) {
            this.jj_input_stream = new JavaCharStream(stream, 1, 1);
        } else {
            this.jj_input_stream.ReInit(stream, 1, 1);
        }

        if (this.token_source == null) {
            this.token_source = new Func1ParserTokenManager(this.jj_input_stream);
        }

        this.token_source.ReInit(this.jj_input_stream);
        this.token = new Token();
        this.jj_ntk = -1;
        this.jj_gen = 0;

        for(int i = 0; i < 20; ++i) {
            this.jj_la1[i] = -1;
        }

        for(int i = 0; i < this.jj_2_rtns.length; ++i) {
            this.jj_2_rtns[i] = new JJCalls();
        }

    }

    public Func1Parser(Func1ParserTokenManager tm) {
        this.jj_la1 = new int[20];
        this.jj_2_rtns = new JJCalls[11];
        this.jj_rescan = false;
        this.jj_gc = 0;
        this.jj_expentries = new ArrayList();
        this.jj_kind = -1;
        this.jj_lasttokens = new int[100];
        this.token_source = tm;
        this.token = new Token();
        this.jj_ntk = -1;
        this.jj_gen = 0;

        for(int i = 0; i < 20; ++i) {
            this.jj_la1[i] = -1;
        }

        for(int i = 0; i < this.jj_2_rtns.length; ++i) {
            this.jj_2_rtns[i] = new JJCalls();
        }

    }

    public void ReInit(Func1ParserTokenManager tm) {
        this.token_source = tm;
        this.token = new Token();
        this.jj_ntk = -1;
        this.jj_gen = 0;

        for(int i = 0; i < 20; ++i) {
            this.jj_la1[i] = -1;
        }

        for(int i = 0; i < this.jj_2_rtns.length; ++i) {
            this.jj_2_rtns[i] = new JJCalls();
        }

    }

    private Token jj_consume_token(int kind) throws ParseException {
        Token oldToken;
        if ((oldToken = this.token).next != null) {
            this.token = this.token.next;
        } else {
            this.token = this.token.next = this.token_source.getNextToken();
        }

        this.jj_ntk = -1;
        if (this.token.kind != kind) {
            this.token = oldToken;
            this.jj_kind = kind;
            throw this.generateParseException();
        } else {
            ++this.jj_gen;
            if (++this.jj_gc > 100) {
                this.jj_gc = 0;

                for(int i = 0; i < this.jj_2_rtns.length; ++i) {
                    for(JJCalls c = this.jj_2_rtns[i]; c != null; c = c.next) {
                        if (c.gen < this.jj_gen) {
                            c.first = null;
                        }
                    }
                }
            }

            return this.token;
        }
    }

    private boolean jj_scan_token(int kind) {
        if (this.jj_scanpos == this.jj_lastpos) {
            --this.jj_la;
            if (this.jj_scanpos.next == null) {
                this.jj_lastpos = this.jj_scanpos = this.jj_scanpos.next = this.token_source.getNextToken();
            } else {
                this.jj_lastpos = this.jj_scanpos = this.jj_scanpos.next;
            }
        } else {
            this.jj_scanpos = this.jj_scanpos.next;
        }

        if (this.jj_rescan) {
            int i = 0;

            Token tok;
            for(tok = this.token; tok != null && tok != this.jj_scanpos; tok = tok.next) {
                ++i;
            }

            if (tok != null) {
                this.jj_add_error_token(kind, i);
            }
        }

        if (this.jj_scanpos.kind != kind) {
            return true;
        } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
            throw jj_ls;
        } else {
            return false;
        }
    }

    public final Token getNextToken() {
        if (this.token.next != null) {
            this.token = this.token.next;
        } else {
            this.token = this.token.next = this.token_source.getNextToken();
        }

        this.jj_ntk = -1;
        ++this.jj_gen;
        return this.token;
    }

    public final Token getToken(int index) {
        Token t = this.token;

        for(int i = 0; i < index; ++i) {
            if (t.next != null) {
                t = t.next;
            } else {
                t = t.next = this.token_source.getNextToken();
            }
        }

        return t;
    }

    private int jj_ntk_f() {
        return (this.jj_nt = this.token.next) == null ? (this.jj_ntk = (this.token.next = this.token_source.getNextToken()).kind) : (this.jj_ntk = this.jj_nt.kind);
    }

    private void jj_add_error_token(int kind, int pos) {
        if (pos < 100) {
            if (pos == this.jj_endpos + 1) {
                this.jj_lasttokens[this.jj_endpos++] = kind;
            } else if (this.jj_endpos != 0) {
                this.jj_expentry = new int[this.jj_endpos];

                for(int i = 0; i < this.jj_endpos; ++i) {
                    this.jj_expentry[i] = this.jj_lasttokens[i];
                }

                for(int[] oldentry : this.jj_expentries) {
                    if (oldentry.length == this.jj_expentry.length) {
                        boolean isMatched = true;

                        for(int i = 0; i < this.jj_expentry.length; ++i) {
                            if (oldentry[i] != this.jj_expentry[i]) {
                                isMatched = false;
                                break;
                            }
                        }

                        if (isMatched) {
                            this.jj_expentries.add(this.jj_expentry);
                            break;
                        }
                    }
                }

                if (pos != 0) {
                    this.jj_lasttokens[(this.jj_endpos = pos) - 1] = kind;
                }
            }

        }
    }

    public ParseException generateParseException() {
        this.jj_expentries.clear();
        boolean[] la1tokens = new boolean[65];
        if (this.jj_kind >= 0) {
            la1tokens[this.jj_kind] = true;
            this.jj_kind = -1;
        }

        for(int i = 0; i < 20; ++i) {
            if (this.jj_la1[i] == this.jj_gen) {
                for(int j = 0; j < 32; ++j) {
                    if ((jj_la1_0[i] & 1 << j) != 0) {
                        la1tokens[j] = true;
                    }

                    if ((jj_la1_1[i] & 1 << j) != 0) {
                        la1tokens[32 + j] = true;
                    }

                    if ((jj_la1_2[i] & 1 << j) != 0) {
                        la1tokens[64 + j] = true;
                    }
                }
            }
        }

        for(int i = 0; i < 65; ++i) {
            if (la1tokens[i]) {
                this.jj_expentry = new int[1];
                this.jj_expentry[0] = i;
                this.jj_expentries.add(this.jj_expentry);
            }
        }

        this.jj_endpos = 0;
        this.jj_rescan_token();
        this.jj_add_error_token(0, 0);
        int[][] exptokseq = new int[this.jj_expentries.size()][];

        for(int i = 0; i < this.jj_expentries.size(); ++i) {
            exptokseq[i] = (int[])this.jj_expentries.get(i);
        }

        return new ParseException(this.token, exptokseq, tokenImage);
    }

    public final boolean trace_enabled() {
        return this.trace_enabled;
    }

    public final void enable_tracing() {
    }

    public final void disable_tracing() {
    }

    private void jj_rescan_token() {
        this.jj_rescan = true;

        for(int i = 0; i < 11; ++i) {
            try {
                JJCalls p = this.jj_2_rtns[i];

                while(true) {
                    if (p.gen > this.jj_gen) {
                        this.jj_la = p.arg;
                        this.jj_lastpos = this.jj_scanpos = p.first;
                        switch (i) {
                            case 0 -> this.jj_3_1();
                            case 1 -> this.jj_3_2();
                            case 2 -> this.jj_3_3();
                            case 3 -> this.jj_3_4();
                            case 4 -> this.jj_3_5();
                            case 5 -> this.jj_3_6();
                            case 6 -> this.jj_3_7();
                            case 7 -> this.jj_3_8();
                            case 8 -> this.jj_3_9();
                            case 9 -> this.jj_3_10();
                            case 10 -> this.jj_3_11();
                        }
                    }

                    p = p.next;
                    if (p == null) {
                        break;
                    }
                }
            } catch (LookaheadSuccess var3) {
            }
        }

        this.jj_rescan = false;
    }

    private void jj_save(int index, int xla) {
        JJCalls p;
        for(p = this.jj_2_rtns[index]; p.gen > this.jj_gen; p = p.next) {
            if (p.next == null) {
                p = p.next = new JJCalls();
                break;
            }
        }

        p.gen = this.jj_gen + xla - this.jj_la;
        p.first = this.token;
        p.arg = xla;
    }

    static {
        jj_la1_init_0();
        jj_la1_init_1();
        jj_la1_init_2();
        jj_ls = new LookaheadSuccess();
    }

    private static final class LookaheadSuccess extends Error {
        private LookaheadSuccess() {
        }

        public Throwable fillInStackTrace() {
            return this;
        }
    }

    static final class JJCalls {
        int gen;
        Token first;
        int arg;
        JJCalls next;

        JJCalls() {
        }
    }
}
