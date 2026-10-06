# Guia de execução da extensão da Funcional 1

Projeto baseado na **Funcional 1 do PLP**, com registros imutáveis, listas
homogêneas genéricas e processamento por funções recursivas.

- [Documentação da implementação e histórico das etapas](IMPLEMENTACAO.md)
- [Roteiro de apresentação e resultados esperados](APRESENTACAO.md)
- [Origem da base e adaptações](../UPSTREAM.md)
- [Protótipo histórico](historico/README.md)

## Proposta e funcionalidades

Um registro agrupa campos nomeados de tipos diferentes. Sua organização se
assemelha à de uma `struct` em C, mas os campos não podem ser alterados depois
da criação. Os registros contêm dados, sem métodos; funções externas realizam
seu processamento.

A extensão implementa declaração e criação de registros, acesso a campos,
listas imutáveis genéricas, `head`, `tail`, `isEmpty`, inferência dos parâmetros de funções,
recursão e verificação de tipos. Usa o parser, os ambientes e as interfaces
`Expressao`, `Valor` e `Tipo` da Funcional 1.

## Registros e acesso a campos

Este programa completo declara um tipo, cria uma pessoa e retorna seu nome:

```text
let record Pessoa { nome: String, idade: Int } in
let var pessoa = Pessoa { nome: "Monique", idade: 23 } in
pessoa.nome
```

Resultado: `Monique`. A expressão `pessoa.idade` retornaria `23`.

Os campos aceitam `Int`, `String`, `Boolean`, registros nomeados e listas, como
`[Int]`, `[Pessoa]` e `[[String]]`. Cada declaração define um tipo
nominal: registros de tipos diferentes não são intercambiáveis apenas por terem
os mesmos campos. A ordem dos campos na criação pode variar; todos os campos
declarados precisam estar presentes, sem extras ou repetições.

A declaração `let record ... in ...` delimita o escopo do tipo. Para declarar
outro tipo, use outro `let record` dentro do corpo. A criação de variáveis
preserva a sintaxe `let var ... in ...` da linguagem-base.

## Funções e inferência

Parâmetros não precisam de anotações de tipos:

```text
let record Pessoa { nome: String, idade: Int } in
let fun obterNome pessoa = pessoa.nome in
obterNome(Pessoa { nome: "Monique", idade: 23 })
```

Resultado: `Monique`. O acesso a `pessoa.nome` exige que o argumento seja um
registro com esse campo. Funções são declaradas com parâmetros separados por
espaço, como `fun somar x y = x + y`; chamadas usam `somar(2, 3)`.

A inferência mantém as restrições de cada função e permite chamadas independentes
com tipos compatíveis. Uma função que apenas consulta `nome` pode receber
registros de tipos diferentes em chamadas distintas. Isso não permite misturar
esses tipos em uma mesma lista.

## Listas e imutabilidade

Listas podem conter inteiros, strings, booleanos, registros ou outras listas.
Os elementos de cada lista precisam ter tipos compatíveis: `[1, 2]` e
`[[1], [2, 3]]` são válidos; `[1, true]` é rejeitado.
As operações disponíveis são:

| Operação | Resultado |
| --- | --- |
| `head(lista)` | Primeiro elemento, preservando seu tipo |
| `tail(lista)` | Nova lista sem o primeiro elemento |
| `isEmpty(lista)` | `true` se a lista estiver vazia; `false` caso contrário |

```text
let record Pessoa { nome: String, idade: Int } in
let var pessoas = [
    Pessoa { nome: "Monique", idade: 23 },
    Pessoa { nome: "Bruno", idade: 25 }
] in
let var restantes = tail(pessoas) in
head(pessoas).nome ++ " / " ++ head(restantes).nome
```

Resultado: `Monique / Bruno`. O primeiro elemento da lista original continua
sendo Monique depois de criar `restantes`.

A lista vazia é escrita como `[]`, e seu tipo de elemento é inferido pelo contexto.
`head` e `tail` de lista vazia geram erro se forem executados. Não existe atribuição
a campos ou elementos, nem operações de inserção ou atualização.

## Listas como campos de registros

A sintaxe `[T]` declara uma lista cujos elementos têm tipo `T`:

```text
let record Grupo { nomes: [String], idades: [Int] } in
let var grupo = Grupo { nomes: ["Monique", "Bruno"], idades: [23, 25] } in
head(tail(grupo.idades))
```

Resultado: `25`. Um campo declarado `[Int]` rejeita `[true]`, mas aceita `[]`.

Registros também podem conter listas de outros registros:

```text
let record Pessoa { nome: String, idade: Int } in
let record Turma { pessoas: [Pessoa] } in
let var turma = Turma { pessoas: [Pessoa { nome: "Monique", idade: 23 }] } in
head(turma.pessoas).nome
```

Resultado: `Monique`. Um campo pode referenciar diretamente outro registro, como
`pessoa: Pessoa`, ou uma lista de listas, como `matriz: [[Int]]`.
Nomes de tipos são resolvidos no escopo da declaração do registro.

A inferência também atende funções genéricas e recursivas sobre listas primitivas:

```text
let fun soma xs = if isEmpty(xs) then 0 else head(xs) + soma(tail(xs)) in
soma([1, 2, 3])
```

Resultado: `6`. Uma função que apenas conta elementos pode receber `[Int]`,
`[String]` ou `[Pessoa]` em chamadas distintas. Funções como valores continuam
fora da Funcional 1; a mudança generaliza as listas para seus tipos de dados.

## Processamento recursivo

```text
let record Pessoa { nome: String, idade: Int } in
let fun somarIdades pessoas =
    if isEmpty(pessoas)
    then 0
    else head(pessoas).idade + somarIdades(tail(pessoas))
in
let var pessoas = [
    Pessoa { nome: "Monique", idade: 23 },
    Pessoa { nome: "Bruno", idade: 25 }
] in
somarIdades(pessoas)
```

Resultado: `48`. O `isEmpty` define o caso-base; `head` acessa uma pessoa e `tail`
fornece as demais. A chamada `somarIdades([])` retorna `0`.

## Como executar

Requisitos: **JDK 25** e **Apache Maven**. Confira `java -version` e
`mvn -version`; ambos devem usar Java 25. Não é necessário instalar JavaCC
separadamente: o Maven gera o parser.

Na pasta `funcional1-registros-listas`, compile do zero, execute os testes e gere
o JAR:

```sh
mvn clean verify
```

Execute os exemplos:

```sh
java -jar target/lf1-records-lists-1.0-SNAPSHOT.jar examples/registros/pessoa.lf1
java -jar target/lf1-records-lists-1.0-SNAPSHOT.jar examples/registros/obter-nome.lf1
java -jar target/lf1-records-lists-1.0-SNAPSHOT.jar examples/registros/imutabilidade.lf1
java -jar target/lf1-records-lists-1.0-SNAPSHOT.jar examples/registros/somar-idades.lf1
java -jar target/lf1-records-lists-1.0-SNAPSHOT.jar examples/registros/contar.lf1
java -jar target/lf1-records-lists-1.0-SNAPSHOT.jar examples/registros/lista-vazia.lf1
```

Resultados, na ordem: `Monique`, `Monique`, `Monique / Bruno`, `48`, `2` e `0`.
Os programas originais também estão em `examples/funcional1/`.

Os exemplos da ampliação de escopo são:

```sh
java -jar target/lf1-records-lists-1.0-SNAPSHOT.jar examples/listas/inteiros.lf1
java -jar target/lf1-records-lists-1.0-SNAPSHOT.jar examples/listas/textos.lf1
java -jar target/lf1-records-lists-1.0-SNAPSHOT.jar examples/listas/booleanos.lf1
java -jar target/lf1-records-lists-1.0-SNAPSHOT.jar examples/listas/aninhadas.lf1
java -jar target/lf1-records-lists-1.0-SNAPSHOT.jar examples/registros/campos-lista.lf1
java -jar target/lf1-records-lists-1.0-SNAPSHOT.jar examples/registros/lista-no-registro.lf1
```

Resultados, na ordem: `6`, `Bruno`, `false`, `3`, `25` e `48`.

A entrada padrão aceita um programa completo:

```sh
echo 'let fun somar x y = x + y in somar(2, 3)' | java -jar target/lf1-records-lists-1.0-SNAPSHOT.jar
```

Resultado: `5`. Sem arquivo, a leitura termina em EOF (Ctrl+D no terminal do
macOS). Arquivos e entrada padrão usam UTF-8.

## Erros e exemplos inválidos

O CLI distingue erros de sintaxe, de tipo e de execução. Erros da extensão e de
chamadas de funções incluem linha e coluna quando a expressão foi lida pelo
parser. Falhas de argumentos identificam a função e a posição do argumento;
campos incompatíveis indicam o nome do campo e os tipos esperado e recebido.

| Arquivo em `examples/erros/` | Comportamento esperado |
| --- | --- |
| `tipo-campo.lf1` | Rejeita `true` em um campo `Int` |
| `campo-inexistente.lf1` | Rejeita acesso ao campo `endereco` |
| `lista-heterogenea.lf1` | Rejeita `Pessoa` e `Produto` na mesma lista |
| `argumento-funcao.lf1` | Rejeita lista com `idade: Boolean` em `somarIdades` |
| `head-vazia.lf1` | Falha na execução de `head([])` |
| `atribuicao-campo.lf1` | Rejeita tentativa de alterar `pessoa.idade` |
| `lista-tipos-incompativeis.lf1` | Rejeita `[1, true]` |
| `campo-lista.lf1` | Rejeita `[Boolean]` em campo declarado `[Int]` |

```sh
java -jar target/lf1-records-lists-1.0-SNAPSHOT.jar examples/erros/tipo-campo.lf1
java -jar target/lf1-records-lists-1.0-SNAPSHOT.jar examples/erros/head-vazia.lf1
```

Esses dois comandos devem terminar com código **1**. Execuções bem-sucedidas
terminam com **0**; opções inválidas do CLI, com **2**. Erros vão para `stderr`;
resultados, para `stdout`.

## Organização

| Caminho | Conteúdo |
| --- | --- |
| `src/main/java/lf1/plp/` | Base PLP e extensão integrada |
| `src/main/java/lf1/plp/functional1/extension/` | Expressões, valores, tipos compostos e localização de erros |
| `src/main/java/lf1/plp/functional1/util/` | Inferência e tipos de funções |
| `src/main/javacc/Functional1.jj` | Gramática executável; edite este arquivo, não o parser gerado |
| `src/main/java/br/ufpe/cin/lf1/Main.java` | Entrada de linha de comando |
| `src/main/java/br/ufpe/cin/lf1/prototipo/` | Protótipo histórico independente |
| `src/test/java/` | Testes da base, extensão, inferência e CLI |
| `examples/` | Programas válidos, inválidos e manifesto de resultados esperados |
| `docs/IMPLEMENTACAO.md` | Decisões, mudanças, validações e limitações por etapa |
| `etc/EBNF.txt` | Resumo da sintaxe; o `.jj` é a fonte executável |

A pasta de referência `../PLP/`, quando presente no workspace, é independente
e não participa da compilação. A demonstração `--demo-registros` é histórica;
consulte [sua documentação](historico/README.md) para não confundi-la com
a execução de arquivos `.lf1`.

## Limites do escopo

- Listas genéricas e homogêneas: aceitam qualquer tipo de dado suportado, com tipos compatíveis entre seus elementos.
- Campos podem ser primitivos, registros nomeados ou listas desses tipos. Não há tipos união para misturar inteiros e booleanos na mesma lista.
- Sem métodos, atualização de registros, inserção em listas ou funções como valores.
- Recursão direta, sem introduzir recursão mútua ou polimórfica.
- `record`, `head`, `tail` e `isEmpty` são palavras reservadas da extensão.
- Os avisos conhecidos da gramática e os limites herdados da base estão
  registrados na documentação da implementação.

## Autores

- Monique Campos
- Efraim Tenório
