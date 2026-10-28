# Extensão da Linguagem Funcional 1: Registros Imutáveis e Listas Genéricas

O projeto estende o interpretador da Funcional 1 do PLP com registros imutáveis,
listas genéricas homogêneas e verificação de tipos. As novas estruturas usam
as expressões, os ambientes e as funções da linguagem-base.

- [Guia de execução e exemplos](docs/GUIA_EXECUCAO.md)
- [Implementação, decisões e histórico do projeto](docs/IMPLEMENTACAO.md)
- [Roteiro de apresentação](docs/APRESENTACAO.md)
- [Origem da base e adaptações](UPSTREAM.md)
- [Protótipo histórico](docs/historico/README.md)

## Funcionalidades implementadas

- Declaração e construção de registros com campos nomeados de tipos diferentes.
- Acesso a campos, inclusive em estruturas aninhadas.
- Campos de tipos primitivos, registros nomeados e listas.
- Listas de inteiros, strings, booleanos, registros e outras listas.
- Operações `head`, `tail` e `isEmpty`, preservando a lista original.
- Funções externas, inferência dos tipos dos parâmetros e processamento recursivo.
- Verificação de tipos e diagnóstico de erros de sintaxe, tipo e execução.

Registros e listas são imutáveis. Registros agrupam dados, sem métodos, e as
funções da linguagem realizam seu processamento.

## Registros e acesso a campos

Um registro reúne campos nomeados, de modo semelhante a uma `struct` em C.
A declaração `let record ... in ...` define o tipo em um escopo, e a construção
`Pessoa { ... }` cria um valor desse tipo.

Este programa completo retorna `Monique`:

```text
let record Pessoa { nome: String, idade: Int } in
let var pessoa = Pessoa { nome: "Monique", idade: 23 } in
pessoa.nome
```

Os campos aceitam `Int`, `String`, `Boolean`, registros nomeados no escopo e
listas desses tipos. A criação exige todos os campos declarados, sem campos
extras ou repetidos. A ordem dos campos pode variar.

Cada declaração de registro possui identidade nominal. Registros declarados
separadamente não se tornam intercambiáveis apenas por terem os mesmos campos.

## Listas genéricas e operações

Uma lista tem tipo `[T]`, em que `T` é o tipo de seus elementos. A implementação
aceita tipos primitivos, registros e listas aninhadas. Exemplos válidos:

| Valor | Tipo |
| --- | --- |
| `[1, 2, 3]` | `[Int]` |
| `["Monique", "Bruno"]` | `[String]` |
| `[true, false]` | `[Boolean]` |
| `[[1, 2], [3]]` | `[[Int]]` |

Listas também aceitam registros do mesmo tipo nominal. Cada lista exige
compatibilidade entre seus elementos: `[1, true]` e `[[1], [true]]` são rejeitadas.

| Operação | Comportamento |
| --- | --- |
| `head(lista)` | Retorna o primeiro elemento, preservando seu tipo `T` |
| `tail(lista)` | Retorna uma nova lista de tipo `[T]`, sem o primeiro elemento |
| `isEmpty(lista)` | Retorna `true` se a lista está vazia e `false` caso contrário |

Este programa retorna `2`:

```text
head(tail([1, 2, 3]))
```

A lista vazia é escrita como `[]`, e o contexto pode determinar o tipo de seus
elementos. `head([])` e `tail([])` geram erro se forem executados. A verificação
de tipos não garante que uma lista contenha elementos.

## Listas nos campos de registros

Cada campo pode receber uma lista compatível com seu tipo declarado.
O programa abaixo declara `notas: [Int]` e retorna `8`:

```text
let record Aluno { nome: String, notas: [Int] } in
let var aluno = Aluno { nome: "Monique", notas: [8, 9, 10] } in
head(aluno.notas)
```

Também são aceitos campos como `pessoas: [Pessoa]`, `matriz: [[Int]]` e
`responsavel: Pessoa`, desde que o tipo nomeado esteja declarado no escopo.
Um campo `[Int]` aceita `[]`, mas rejeita uma lista de booleanos.

## Funções, inferência e recursão

Funções usam parâmetros separados por espaço na declaração e argumentos entre
parênteses na chamada. Os parâmetros não precisam de anotações explícitas de
tipo: o corpo da função determina as restrições verificadas nas chamadas.

Este programa combina um registro contendo uma lista de registros com uma
função recursiva. O resultado é `48`:

```text
let record Pessoa { nome: String, idade: Int } in
let record Turma { pessoas: [Pessoa] } in
let fun somarIdades pessoas =
    if isEmpty(pessoas) then 0
    else head(pessoas).idade + somarIdades(tail(pessoas))
in
let var turma = Turma { pessoas: [
    Pessoa { nome: "Monique", idade: 23 },
    Pessoa { nome: "Bruno", idade: 25 }
] } in
somarIdades(turma.pessoas)
```

`isEmpty` estabelece o caso-base, que retorna `0`. `head` acessa a pessoa atual,
e `tail` fornece o restante da lista à próxima chamada. A função exige elementos
com o campo `idade` inteiro. A chamada `somarIdades([])` retorna `0`.

Funções que apenas contam elementos podem processar listas de tipos diferentes
em chamadas independentes. A inferência preserva as relações entre parâmetros
e resultados e exige uma assinatura compatível nas chamadas recursivas.

## Imutabilidade

As operações não alteram os valores já criados. Neste programa, `tail` produz
outra lista, e a primeira pessoa da lista original continua sendo Monique:

```text
let record Pessoa { nome: String, idade: Int } in
let var pessoas = [
    Pessoa { nome: "Monique", idade: 23 },
    Pessoa { nome: "Bruno", idade: 25 }
] in
let var restantes = tail(pessoas) in
head(pessoas).nome ++ " / " ++ head(restantes).nome
```

Resultado: `Monique / Bruno`. A linguagem não possui atribuição a campos ou
operações de inserção e atualização de elementos das listas.

## Gramática da extensão: EBNF clicável

Clique no nome de uma produção, em um não terminal ou no nome de uma classe
para abrir a implementação correspondente. Os links são relativos ao projeto.

A notação usa `[...]` para uma parte opcional e `{...}` para repetição.
Símbolos entre aspas são tokens da linguagem. Por exemplo, `"["` e `"]"`
são os delimitadores literais de uma lista.

<table>
<thead><tr><th>Produção clicável</th><th>EBNF</th><th>Implementação</th></tr></thead>
<tbody>
<tr><td><a href="src/main/java/lf1/plp/functional1/extension/ExpDeclaracaoRegistro.java#L11">DeclRegistro</a></td><td><code>&quot;let&quot; &quot;record&quot; <a href="src/main/java/lf1/plp/expressions2/expression/Id.java">ID</a> &quot;{&quot; [ <a href="src/main/java/lf1/plp/functional1/extension/ExpDeclaracaoRegistro.java#L11">DeclCampos</a> ] &quot;}&quot; &quot;in&quot; <a href="src/main/java/lf1/plp/expressions2/expression/Expressao.java">Expressao</a></code></td><td><a href="src/main/java/lf1/plp/functional1/extension/ExpDeclaracaoRegistro.java#L11">ExpDeclaracaoRegistro</a></td></tr>
<tr><td><a href="src/main/java/lf1/plp/functional1/extension/ExpDeclaracaoRegistro.java#L11">DeclCampos</a></td><td><code><a href="src/main/java/lf1/plp/functional1/extension/ExpDeclaracaoRegistro.java#L11">DeclCampo</a> { &quot;,&quot; <a href="src/main/java/lf1/plp/functional1/extension/ExpDeclaracaoRegistro.java#L11">DeclCampo</a> }</code></td><td><a href="src/main/java/lf1/plp/functional1/extension/TipoRegistro.java#L12">TipoRegistro</a></td></tr>
<tr><td><a href="src/main/java/lf1/plp/functional1/extension/ExpDeclaracaoRegistro.java#L11">DeclCampo</a></td><td><code><a href="src/main/java/lf1/plp/expressions2/expression/Id.java">ID</a> &quot;:&quot; <a href="src/main/javacc/Functional1.jj#L444">TipoCampo</a></code></td><td><a href="src/main/java/lf1/plp/functional1/extension/TipoRegistro.java#L12">TipoRegistro</a></td></tr>
<tr><td><a href="src/main/javacc/Functional1.jj#L444">TipoCampo</a></td><td><code>&quot;Int&quot; | &quot;String&quot; | &quot;Boolean&quot; | <a href="src/main/java/lf1/plp/functional1/extension/TipoNomeado.java#L8">ID</a> | &quot;[&quot; <a href="src/main/javacc/Functional1.jj#L444">TipoCampo</a> &quot;]&quot;</code></td><td><a href="src/main/java/lf1/plp/expressions1/util/TipoPrimitivo.java#L12">TipoPrimitivo</a><br/><a href="src/main/java/lf1/plp/functional1/extension/TipoLista.java#L8">TipoLista</a><br/><a href="src/main/java/lf1/plp/functional1/extension/TipoNomeado.java#L8">TipoNomeado</a></td></tr>
<tr><td><a href="src/main/java/lf1/plp/functional1/extension/ExpRegistro.java#L13">InstanciacaoRegistro</a></td><td><code><a href="src/main/java/lf1/plp/expressions2/expression/Id.java">ID</a> &quot;{&quot; [ <a href="src/main/java/lf1/plp/functional1/extension/ExpRegistro.java#L13">AtribuicaoCampos</a> ] &quot;}&quot;</code></td><td><a href="src/main/java/lf1/plp/functional1/extension/ExpRegistro.java#L13">ExpRegistro</a></td></tr>
<tr><td><a href="src/main/java/lf1/plp/functional1/extension/ExpRegistro.java#L13">AtribuicaoCampos</a></td><td><code><a href="src/main/java/lf1/plp/functional1/extension/ExpRegistro.java#L13">AtribuicaoCampo</a> { &quot;,&quot; <a href="src/main/java/lf1/plp/functional1/extension/ExpRegistro.java#L13">AtribuicaoCampo</a> }</code></td><td><a href="src/main/java/lf1/plp/functional1/extension/ExpRegistro.java#L13">ExpRegistro</a></td></tr>
<tr><td><a href="src/main/java/lf1/plp/functional1/extension/ExpRegistro.java#L13">AtribuicaoCampo</a></td><td><code><a href="src/main/java/lf1/plp/expressions2/expression/Id.java">ID</a> &quot;:&quot; <a href="src/main/java/lf1/plp/expressions2/expression/Expressao.java">Expressao</a></code></td><td><a href="src/main/java/lf1/plp/functional1/extension/ExpRegistro.java#L13">ExpRegistro</a></td></tr>
<tr><td><a href="src/main/java/lf1/plp/functional1/extension/ExpLista.java#L13">LiteralLista</a></td><td><code>&quot;[&quot; [ <a href="src/main/java/lf1/plp/expressions2/expression/Expressao.java">Expressao</a> { &quot;,&quot; <a href="src/main/java/lf1/plp/expressions2/expression/Expressao.java">Expressao</a> } ] &quot;]&quot;</code></td><td><a href="src/main/java/lf1/plp/functional1/extension/ExpLista.java#L13">ExpLista</a><br/><a href="src/main/java/lf1/plp/functional1/extension/ValorLista.java#L8">ValorLista</a></td></tr>
<tr><td><a href="src/main/java/lf1/plp/functional1/extension/ExpOperacaoLista.java#L12">OperacaoLista</a></td><td><code>( &quot;head&quot; | &quot;tail&quot; | &quot;isEmpty&quot; ) &quot;(&quot; <a href="src/main/java/lf1/plp/expressions2/expression/Expressao.java">Expressao</a> &quot;)&quot;</code></td><td><a href="src/main/java/lf1/plp/functional1/extension/ExpOperacaoLista.java#L12">ExpOperacaoLista</a></td></tr>
<tr><td><a href="src/main/java/lf1/plp/functional1/extension/ExpCampo.java#L10">Primaria</a></td><td><code><a href="src/main/javacc/Functional1.jj#L374">Atomo</a> { &quot;.&quot; <a href="src/main/java/lf1/plp/expressions2/expression/Id.java">ID</a> }</code></td><td><a href="src/main/java/lf1/plp/functional1/extension/ExpCampo.java#L10">ExpCampo</a></td></tr>
<tr><td><a href="src/main/javacc/Functional1.jj#L374">Atomo</a></td><td><code><a href="src/main/java/lf1/plp/expressions2/expression/Id.java">ID</a> | <a href="src/main/javacc/Functional1.jj#L305">ValorPrimitivo</a> | &quot;(&quot; <a href="src/main/java/lf1/plp/expressions2/expression/Expressao.java">Expressao</a> &quot;)&quot; | <a href="src/main/java/lf1/plp/functional1/expression/Aplicacao.java#L26">ChamadaFuncao</a> | <a href="src/main/java/lf1/plp/functional1/extension/ExpRegistro.java#L13">InstanciacaoRegistro</a> | <a href="src/main/java/lf1/plp/functional1/extension/ExpLista.java#L13">LiteralLista</a> | <a href="src/main/java/lf1/plp/functional1/extension/ExpOperacaoLista.java#L12">OperacaoLista</a></code></td><td><a href="src/main/javacc/Functional1.jj#L374">PExpPrimaria</a></td></tr>
<tr><td><a href="src/main/java/lf1/plp/functional1/expression/Aplicacao.java#L26">ChamadaFuncao</a></td><td><code><a href="src/main/java/lf1/plp/expressions2/expression/Id.java">ID</a> &quot;(&quot; <a href="src/main/java/lf1/plp/expressions2/expression/Expressao.java">Expressao</a> { &quot;,&quot; <a href="src/main/java/lf1/plp/expressions2/expression/Expressao.java">Expressao</a> } &quot;)&quot;</code></td><td><a href="src/main/java/lf1/plp/functional1/expression/Aplicacao.java#L26">Aplicacao</a></td></tr>
</tbody>
</table>

O resumo corresponde a [etc/EBNF.txt](etc/EBNF.txt). A gramática executável
completa está em [Functional1.jj](src/main/javacc/Functional1.jj).

`AtribuicaoCampo` representa o preenchimento de um campo durante a construção
do registro, sem permitir a alteração de um registro já criado. `TipoCampo`
abre a produção `PTipoCampo` do JavaCC; suas representações são `TipoPrimitivo`,
`TipoNomeado` e `TipoLista`, também acessíveis na tabela.

A gramática define a sintaxe. A compatibilidade dos elementos das listas,
a identidade nominal dos registros e a conferência dos campos são regras
semânticas implementadas nas classes indicadas e em
[Inferencia](src/main/java/lf1/plp/functional1/util/Inferencia.java).

## Verificação de tipos e erros

O fluxo do interpretador é: leitura do programa, parser JavaCC,
`Programa.checaTipo()`, `Programa.executar()` e apresentação do resultado.
A avaliação só ocorre após a aprovação da verificação de tipos.

O CLI distingue erros de sintaxe, tipo e execução. Erros da extensão e de
chamadas incluem linha e coluna quando a expressão vem do parser. Os exemplos
abaixo estão em `examples/erros/`:

| Arquivo | Comportamento |
| --- | --- |
| `tipo-campo.lf1` | Rejeita um booleano no campo `idade: Int` |
| `campo-inexistente.lf1` | Rejeita o acesso a um campo inexistente |
| `lista-heterogenea.lf1` | Rejeita registros de tipos nominais diferentes na mesma lista |
| `lista-tipos-incompativeis.lf1` | Rejeita `[1, true]` |
| `campo-lista.lf1` | Rejeita `[Boolean]` em um campo declarado `[Int]` |
| `argumento-funcao.lf1` | Rejeita um argumento incompatível com as restrições da função |
| `head-vazia.lf1` | Gera erro de execução ao avaliar `head([])` |
| `atribuicao-campo.lf1` | Rejeita a tentativa de alterar um campo |

Execuções válidas terminam com código `0`; falhas de programa, com `1`;
e opções inválidas do CLI, com `2`. Resultados usam `stdout`, e erros de programa
usam `stderr`.

## Como executar

Requisitos: **JDK 25** e **Apache Maven**. Confira `java -version` e
`mvn -version`; o Maven precisa usar Java 25. A pasta `funcional1-registros-listas`
é o projeto executável. A pasta `PLP` contém a referência da disciplina e não
participa dessa compilação.

O comando de compilação, testes e empacotamento é:

```sh
mvn clean verify
```

O empacotamento produz `target/lf1-records-lists-1.0-SNAPSHOT.jar`.
Este comando executa a soma das idades:

```sh
java -jar target/lf1-records-lists-1.0-SNAPSHOT.jar examples/registros/lista-no-registro.lf1
```

Resultado: `Resultado: 48`. Os exemplos da ampliação de escopo incluem:

| Arquivo em `examples/` | Resultado |
| --- | --- |
| `listas/inteiros.lf1` | `6` |
| `listas/textos.lf1` | `Bruno` |
| `listas/booleanos.lf1` | `false` |
| `listas/aninhadas.lf1` | `3` |
| `registros/campos-lista.lf1` | `25` |
| `registros/lista-no-registro.lf1` | `48` |

O CLI também aceita um programa pela entrada padrão, até EOF. A opção `--help`
mostra o uso. A opção `--demo-registros` executa o protótipo histórico em Java,
que é independente da execução dos programas `.lf1`.

## Testes e exemplos

O projeto contém 83 métodos de teste JUnit. A validação em uma cópia limpa
concluiu a compilação e executou os 83 testes sem falhas, erros ou testes
ignorados. Eles estão distribuídos nestas classes:

| Classe | Testes | Cobertura principal |
| --- | --- | --- |
| `Funcional1Test` | 8 | Comportamento da linguagem-base |
| `RegistrosListasTest` | 18 | Registros, listas, tipos e imutabilidade |
| `InferenciaTest` | 24 | Funções, restrições de tipos e recursão |
| `ConsolidacaoTest` | 11 | Integração, diagnósticos e CLI |
| `ListasGenericasTest` | 22 | Tipos primitivos, aninhamento e campos compostos |

O [manifesto de resultados](examples/resultados.tsv) reúne 24 programas:
16 válidos e 8 com erros esperados. Os resultados e códigos de saída desses
programas também foram conferidos pelo JAR. O guia de execução apresenta
os comandos, e a documentação da implementação registra o histórico.

A suíte usa `mvn test`. A seleção de uma classe ou método usa o Maven Surefire:

```sh
mvn -Dtest=ListasGenericasTest test
mvn -Dtest="ListasGenericasTest#registroComListaDeRegistros" test
```

## Organização do projeto

| Caminho | Conteúdo |
| --- | --- |
| `src/main/javacc/Functional1.jj` | Gramática usada na geração do parser |
| `src/main/java/lf1/plp/` | Base PLP e extensão integrada |
| `src/main/java/lf1/plp/functional1/extension/` | Expressões, valores e tipos da extensão |
| `src/main/java/lf1/plp/functional1/util/` | Inferência e tipos de funções |
| `src/main/java/br/ufpe/cin/lf1/Main.java` | Entrada do CLI |
| `src/main/java/br/ufpe/cin/lf1/prototipo/` | Protótipo histórico independente |
| `src/test/java/` | Testes automatizados |
| `examples/` | Programas válidos, erros e resultados esperados |
| `etc/EBNF.txt` | Resumo da sintaxe |
| `docs/` | Execução, implementação e histórico |

## Limites do escopo

- Listas genéricas e homogêneas, sem tipos união para misturar elementos incompatíveis.
- Registros e listas imutáveis, sem métodos ou operações de atualização e inserção.
- Funções de primeira ordem, sem funções como valores ou closures.
- Recursão direta, sem introduzir recursão mútua ou polimórfica.
- `record`, `head`, `tail` e `isEmpty` são palavras reservadas da extensão.

## Autores

- Monique Campos
- Efraim Tenório
