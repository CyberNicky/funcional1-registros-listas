# Implementação da extensão da Funcional 1

Este documento acompanha a implementação de registros imutáveis e listas
genéricas homogêneas sobre a base `lf1.plp` do PLP. O README apresenta a proposta; aqui são
registradas as decisões, funcionalidades entregues, testes e próximas etapas.

## Estado atual

As etapas 1 e 2 implementam registros imutáveis, listas homogêneas e inferência
para processamento recursivo, sem anotações de tipo nos parâmetros. A etapa 3
consolida a documentação e os exemplos, melhora os diagnósticos e corrige a
ligação de tipos de registro sob sombreamento. A etapa 4 amplia as listas para
qualquer tipo de dado suportado e permite campos compostos nos registros.

O [roteiro de apresentação](APRESENTACAO.md) contém os programas e resultados
esperados. O [README](../README.md) preserva a proposta original; o
[guia de execução](GUIA_EXECUCAO.md) apresenta a sintaxe executável atual.

As seções abaixo preservam o histórico. A limitação de inferência e a rejeição
estática de `head([])` da etapa 1 foram substituídas pelas regras da etapa 2.
Na etapa 3, construtores passaram a guardar a ligação nominal verificada, e o
protótipo foi movido para o pacote `br.ufpe.cin.lf1.prototipo`. Na etapa 4, as
restrições anteriores a listas de registros e campos primitivos foram removidas.

## Etapa 1 — integração das construções básicas (22/09/2026)

- Declaração com escopo: `let record Pessoa { nome: String, idade: Int } in ...`.
- Campos primitivos: `Int`, `String` e `Boolean`. Registros e listas como campos
  ficam para uma etapa posterior.
- Tipos nominais: duas declarações distintas representam tipos distintos.
- Construção: `Pessoa { nome: "Monique", idade: 23 }`.
- Consulta: `pessoa.nome` e `head(pessoas).idade`.
- Listas homogêneas de registros, incluindo `[]`.
- Operações: `head`, `tail` e `isEmpty`; `head` e `tail` de lista vazia geram erro.
- Sem atribuição a campos ou elementos; valores protegidos por cópias imutáveis.
- Integração ao parser JavaCC e às interfaces `Expressao`, `Valor` e `Tipo`.

A inferência dos tipos dos parâmetros de funções que acessam campos ou usam
operações de listas será tratada na próxima etapa. Esta primeira entrega não
conclui o exemplo recursivo `somarIdades` da proposta.

### Sintaxe executável

Declarações de registro usam um `let` próprio. Para criar valores ou declarar
funções que dependam desse tipo, use outro `let` dentro do `in`:

```text
let record Pessoa { nome: String, idade: Int } in
let var pessoa = Pessoa { nome: "Monique", idade: 23 } in
pessoa.nome
```

Resultado: `Monique`.

```text
let record Pessoa { nome: String, idade: Int } in
let var pessoas = [
    Pessoa { nome: "Monique", idade: 23 },
    Pessoa { nome: "Bruno", idade: 25 }
] in
head(pessoas).idade + head(tail(pessoas)).idade
```

Resultado: `48`. Este exemplo soma dois elementos explicitamente; ainda não é
uma função recursiva.

- A sintaxe original `let var ... in ...` e `let fun f x = ... in ...` continua
  disponível. As declarações simultâneas originais, separadas por vírgula,
  mantêm seu comportamento. Declarações de registro não entram nessa sequência
  nesta etapa; para múltiplos tipos, aninhe `let record ... in let record ...`.
- `record`, `head`, `tail` e `isEmpty` são novas palavras reservadas. Programas
  antigos que as usavam como identificadores precisam renomeá-los.
- O acesso a campo tem precedência sobre os operadores binários e pode seguir
  uma chamada, um literal ou uma expressão entre parênteses.
- Valores de campos podem ser expressões, como `idade: n + 3`.
- A ordem dos campos na criação não precisa seguir a declaração; o conjunto
  de campos deve ser exatamente o mesmo.
- São aceitos registros sem campos: `let record Vazio {} in Vazio {}`.
- `Int`, `String` e `Boolean` são reconhecidos como nomes de tipos somente na
  declaração de campos. Outros tipos de campo são rejeitados nesta etapa.

### Tipos, escopo e lista vazia

Cada declaração cria uma identidade nominal de tipo. `Pessoa` e `Produto`
continuam diferentes mesmo que tenham os mesmos campos. Uma declaração interna
de `Pessoa` também representa outro tipo, sem mudar o tipo de valores externos.
O nome do registro fica disponível somente no corpo do seu `let`.

O ambiente existente do PLP guarda os tipos de registro sob chaves internas
`record:<nome>`, impossíveis de escrever como identificadores. Assim, uma
variável e um tipo podem compartilhar um nome sem colidir. A execução guarda
a definição sob a mesma chave; não há registro global estático de tipos nem
dependência de um cache criado pela verificação.

Uma lista não vazia tem tipo `[Pessoa]`, por exemplo. `[]` tem tipo interno
`[?registro]`, compatível com qualquer lista de registros, sem ser uma lista de
inteiros. Em um condicional entre `[]` e uma lista tipada, a interseção dos tipos
preserva o tipo conhecido, independentemente da ordem dos ramos.

- `isEmpty([])` retorna `true`.
- `head([])` é rejeitado na verificação: não existe tipo de registro conhecido
  para o resultado, e o argumento é uma lista vazia sem tipo definido.
- `tail([])` passa pela verificação e falha na execução.
- `head(tail([Pessoa { ... }]))` tem tipo conhecido, mas falha na execução
  porque o `tail` retornou uma lista vazia.
- `tail` preserva o tipo do elemento, inclusive quando retorna uma lista vazia.

O operador existente `==` funciona com os novos valores: compara o conteúdo dos
registros e das listas, após a compatibilidade de tipos ser verificada. Registros
de tipos nominais distintos não podem ser comparados em programas bem tipados.

### Organização do código

As classes integradas ficam em `src/main/java/lf1/plp/functional1/extension/`:

| Classes | Responsabilidade |
| --- | --- |
| `TipoComposto`, `TipoRegistro`, `TipoLista` | Tipos compatíveis com a interface `Tipo` do PLP |
| `ValorRegistro`, `ValorLista` | Valores compatíveis com `ValorConcreto`, com coleções imutáveis |
| `ExpressaoExtensao` | Comportamento comum de verificação das novas expressões |
| `ExpDeclaracaoRegistro` | Escopo do tipo nos ambientes de compilação e execução |
| `ExpRegistro`, `ExpCampo` | Construção de registros e consulta a campos |
| `ExpLista`, `ExpOperacaoLista` | Listas e avaliação de `head`, `tail`, `isEmpty` |
| `ErroExtensao` | Mensagens de erro específicas da extensão |

A gramática `src/main/javacc/Functional1.jj` instancia essas expressões. O fluxo
do CLI permanece `Func1Parser.Input()` → `Programa.checaTipo()` →
`Programa.executar()`. As 50 classes originais importadas da base não foram
modificadas nesta etapa. Os arquivos gerados pelo JavaCC não são editados.

O protótipo em `br.ufpe.cin.lf1` continua disponível em `--demo-registros`, mas
não participa da execução dos programas `.lf1`. Suas classes e limitações não
devem ser confundidas com a implementação integrada em `lf1.plp`.

### Imutabilidade e erros

Os construtores dos valores copiam as coleções recebidas e expõem apenas
coleções não modificáveis. `tail` cria um novo valor e preserva a lista original.
Os campos são primitivos nesta etapa, sem coleções mutáveis acessíveis dentro
dos registros. Não existe sintaxe de atribuição a campos ou elementos.

São rejeitados:

- campos repetidos na declaração ou na criação (erro de sintaxe);
- tipos de campo fora do escopo inicial (erro de sintaxe);
- tipos de registro não declarados ou usados fora do escopo;
- campos ausentes, extras ou com valores incompatíveis;
- acesso a campo inexistente ou em valor que não seja registro;
- listas primitivas, listas aninhadas ou mistura de tipos de registro;
- operações de lista sobre outros valores;
- quantidade inválida de argumentos nas operações de lista (erro de sintaxe);
- `head` e `tail` sobre listas vazias, conforme as regras acima.

### Testes e execução

Na pasta `funcional1-registros-listas`, usando JDK 25 e Maven:

```sh
mvn clean verify
java -jar target/lf1-records-lists-1.0-SNAPSHOT.jar examples/registros/pessoa.lf1
java -jar target/lf1-records-lists-1.0-SNAPSHOT.jar examples/registros/listas.lf1
```

Os testes estão em `src/test/java/br/ufpe/cin/lf1/RegistrosListasTest.java`.
São 18 testes novos, além dos oito testes de regressão de `Funcional1Test`:

- criação, acesso e expressões em campos dos três tipos primitivos;
- operações de lista, preservação da lista original e imutabilidade das coleções;
- lista vazia nos dois ramos possíveis de condicionais;
- registros e listas passando por uma função identidade da linguagem-base;
- escopo, sombreamento nominal e coexistência de nome de tipo e variável;
- rejeições de campos, listas e operações inválidas;
- igualdade, repetição da verificação/execução e clonagem da AST;
- execução dos dois arquivos de exemplo;
- mensagem explícita para a inferência de parâmetros ainda não implementada.

Validação desta etapa: `mvn clean verify` passou com **26 testes, sem falhas**.
Pelo JAR, os exemplos `pessoa.lf1`, `listas.lf1` e o fatorial original retornaram
respectivamente `Monique`, `48` e `120`, com código de saída zero. Pela entrada
padrão, `head([])`, `tail([])` e um campo `Int` preenchido com `true` retornaram
mensagens de erro e código de saída 1. Permanecem os três avisos de ambiguidade
do JavaCC nos operadores `==`, `+` e `and`, já presentes na gramática-base.

### Próximas etapas registradas ao concluir a etapa 1

1. Estender a inferência de tipos dos parâmetros, permitindo `p.nome`,
   `head(pessoas)` e demais operações dentro de funções que recebam esses dados.
   A função identidade já consegue transportar registros e listas; isso não
   significa que a inferência para inspecioná-los esteja pronta.
2. Integrar as restrições de tipos às chamadas recursivas e validar `obterNome`,
   contagem de registros e `somarIdades`, inclusive com lista vazia.
3. Adicionar testes de funções usadas com tipos de registro diferentes e de
   chamadas incompatíveis, preservando a inferência original da Funcional 1.
4. Avaliar campos compostos somente se forem incluídos no escopo da proposta.
5. Ao concluir a extensão, consolidar os exemplos ilustrativos do README com
   a sintaxe definitiva e decidir sobre a remoção do protótipo independente.

Esta entrega não implementa atualização de registros, inserção em listas,
listas genéricas ou métodos. As limitações preexistentes da linguagem-base
continuam fora desta etapa.


## Etapa 2 — inferência de parâmetros e recursão (22/09/2026)

### O que foi implementado

- Inferência a partir do uso de parâmetros, incluindo campos de registros,
  `head`, `tail`, `isEmpty` e construção de listas com parâmetros.
- Relação entre o tipo do argumento e o retorno: uma função que retorna
  `head(xs)` preserva o tipo do registro recebido em `xs`.
- Acúmulo das restrições de campos: `p.nome` exige um registro com `nome`;
  `p.idade + 1` também exige `idade` de tipo `Int`.
- Assinatura compartilhada entre corpo e chamadas recursivas, incluindo todos
  os parâmetros e o retorno. Uma chamada recursiva não pode trocar uma lista
  por um inteiro ou retornar um tipo incompatível com o caso-base.
- Instanciação independente dos tipos genéricos em cada chamada de função,
  preservando relações entre parâmetros, elementos de lista, campos e retorno.
- Preservação das restrições dos parâmetros capturados por funções internas.
- Rejeição de tipos recursivos impossíveis, evitando ciclos na inferência.

### Exemplos executáveis

`examples/registros/somar-idades.lf1`:

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

Resultado: `48`. Com `somarIdades([])`, o resultado é `0`.

`examples/registros/obter-nome.lf1`:

```text
let record Pessoa { nome: String, idade: Int } in
let fun obterNome pessoa = pessoa.nome in
obterNome(Pessoa { nome: "Monique", idade: 23 })
```

Resultado: `Monique`. O arquivo `examples/registros/contar.lf1` percorre a lista
recursivamente, sem depender de seus campos, e retorna `2`.

### Regras da inferência

**Restrições de campos e identidade nominal.** Uma função como `obterNome` não
escolhe um tipo de registro apenas porque ele possui um campo chamado `nome`.
Ela exige esse campo no argumento e relaciona seu tipo ao retorno. Assim, pode
receber `Pessoa` e `Produto` em chamadas distintas, se ambos tiverem `nome`.
Se o corpo concatenar esse campo com uma string, o campo deverá ser `String`.
Sem essa operação, o campo pode ter qualquer um dos tipos primitivos permitidos.

Isso não torna `Pessoa` e `Produto` o mesmo tipo: uma lista com valores dos dois
continua inválida. Parâmetros relacionados pelo corpo também precisam concordar.
Por exemplo, `fun par a b = [a, b]` exige dois registros do mesmo tipo nominal,
assim como `fun escolher a b = if true then a else b` exige resultados de tipos
compatíveis.

**Chamadas independentes.** Cada chamada recebe uma cópia das variáveis de tipo
generalizadas e de suas restrições. Uma chamada de `contar` com `[Pessoa]` não
impede outra chamada com `[Produto]`. Campos e retornos compartilham as mesmas
variáveis dentro da cópia, preservando sua relação.

**Recursão.** Enquanto o corpo é verificado, sua assinatura ainda não está
generalizada. As chamadas recursivas usam os mesmos tipos do corpo, impedindo
mudança de tipo entre uma chamada e a próxima. O sistema não implementa
recursão polimórfica nem adiciona recursão mútua entre declarações simultâneas.

**Funções internas.** Apenas variáveis de tipo que não estão livres no ambiente
externo são generalizadas. Se uma função interna consulta `p.idade`, a restrição
continua vinculada ao parâmetro `p` da função externa. Isso evita aceitar uma
chamada externa com um registro sem esse campo.

**Lista vazia.** `[]` agora recebe uma variável de elemento restrita a registros,
que pode ser refinada pelo contexto. Por isso, `somarIdades([])` é aceito mesmo
que a lista não contenha um elemento do qual extrair um tipo nominal.

`head([])` passa pela verificação e gera erro somente se executado, como
`tail([])`. Essa mudança em relação à etapa 1 permite verificar funções com
caso-base sem rejeitar um ramo que não será executado. Por exemplo:

```text
if true then 1 else head([]).idade
```

O resultado é `1`. Já `head([]) + 1` continua sendo um erro de tipo: o resultado
de `head` precisa ser registro, não inteiro. A inferência não comprova que uma
lista é não vazia; `head` e `tail` mantêm a proteção em tempo de execução.

### Alterações na implementação

| Arquivo/classe | Mudança |
| --- | --- |
| `functional1/util/Inferencia.java` | Resolução de variáveis, unificação, restrições de campos, verificação de ciclos, coleta de variáveis livres e cópia de tipos |
| `TipoPolimorfico` | Variáveis ligadas a outros tipos, com restrições livre, primitivo ou registro e campos exigidos |
| `TipoFuncao` | Generalização e instanciação por chamada, validação de aridade e relação entre argumentos e retorno |
| `DefFuncao`, `DecFuncao` | Inferência unificada do corpo e da assinatura recursiva, com restauração dos escopos em caso de erro |
| `AmbienteCompilacao`, `ContextoCompilacao` | Consulta dos tipos visíveis para preservar tipos capturados |
| `TipoPrimitivo` | Comparação por unificação, propagando restrições para variáveis de tipo |
| `TipoLista`, `ExpLista` | Elementos com tipos inferidos, sempre restritos a registros |
| `ExpCampo`, `ExpOperacaoLista` | Inferência sobre parâmetros em lugar da rejeição provisória da etapa 1 |

Diferentemente da etapa 1, esta etapa adapta algumas classes importadas do PLP,
pois o mecanismo anterior separava os parâmetros do corpo dos parâmetros da
assinatura recursiva e não tratava as novas restrições. A cópia de referência
`../PLP/` não foi alterada. A sintaxe JavaCC não precisou mudar nesta etapa.

A antiga limpeza de tipos instanciados entre chamadas foi substituída por cópias
de variáveis generalizadas. As relações internas são preservadas por um mapa
único de cópia para domínio e imagem da função. Não existe cache global de tipos
inferidos; uma nova verificação de programa começa com um ambiente novo.

### Testes e reprodução

```sh
mvn clean verify
java -jar target/lf1-records-lists-1.0-SNAPSHOT.jar examples/registros/somar-idades.lf1
java -jar target/lf1-records-lists-1.0-SNAPSHOT.jar examples/registros/contar.lf1
java -jar target/lf1-records-lists-1.0-SNAPSHOT.jar examples/registros/obter-nome.lf1
```

`InferenciaTest` acrescenta 24 testes. Eles cobrem soma recursiva, contagem,
projeção de campos, retornos de registros/listas, chamadas independentes,
construção de registros e listas com parâmetros, restrições compartilhadas,
funções internas, recursão com acumulador, lista vazia, conflitos de tipos,
aridade, tipos impossíveis, repetição da verificação e clonagem do programa.

Dois testes da etapa 1 foram atualizados: a inferência antes rejeitada agora é
exercitada como funcionalidade; `head([])` agora tem seu erro validado na
execução. Os oito testes originais da linguagem-base continuam na suíte.

Validação concluída: `mvn clean verify` passou com **50 testes, sem falhas**.
Pelo JAR, `somar-idades.lf1`, `contar.lf1` e `obter-nome.lf1` retornaram `48`,
`2` e `Monique`, respectivamente. Pela entrada padrão, a soma de `[]` retornou
`0`; um campo `idade: Boolean`, uma chamada recursiva com inteiro no lugar da
lista e a execução de `head([])` terminaram com código 1 e mensagens de erro.
Os três avisos já conhecidos do JavaCC permanecem, sem novos avisos de gramática.

### Limites mantidos e próximos trabalhos

- Campos aceitam somente `Int`, `String` e `Boolean`; registros e listas como
  campos não foram adicionados.
- Listas continuam contendo somente registros do mesmo tipo nominal.
- A etapa mantém funções de primeira ordem e a semântica de execução da LF1;
  não implementa funções como valores, closures ou outro modelo de escopo.
- Não há atualização de campos, inserção de elementos ou listas genéricas.
- Declarações de tipos continuam usando `let record ... in ...` próprios.
- O protótipo Java independente permanece disponível, mas não é usado pelo
  parser e interpretador integrados.

Ao concluir a etapa 2, o trabalho seguinte previsto era consolidar a apresentação e os exemplos,
revisar mensagens de diagnóstico e avaliar se campos compostos entrariam no escopo.
O processamento recursivo de listas de registros descrito na proposta está
implementado nesta etapa.


## Etapa 3 — consolidação, diagnósticos e revisão (22/09/2026)

### Documentação e apresentação

O README foi reescrito para refletir as funcionalidades implementadas, com
programas completos na sintaxe real, resultados esperados, limites e comandos
de execução. Os trechos de código são exercitados por um teste de integração.
A analogia com `struct` explica a organização dos campos e explicita a diferença
de imutabilidade.

O arquivo [APRESENTACAO.md](APRESENTACAO.md) organiza uma demonstração da base,
dos registros, das funções, das listas, da imutabilidade, da recursão e dos erros.
Foram adicionados exemplos de preservação da lista original e de caso-base com
lista vazia, além de seis programas inválidos em `examples/erros/`.

O manifesto `examples/resultados.tsv` lista 16 programas com código de saída
e trecho de saída esperado. Os testes executam todos pelo CLI em processos Java
separados; a validação final também os executa pelo JAR empacotado.

### Revisão da integração e correções

**Identidade nominal sob sombreamento.** Foi reproduzida uma falha em uma função
que criava `P { x: n }`: se a chamada acontecesse dentro de outro `let record P`,
o avaliador usava a definição de `P` do chamador, embora a inferência tivesse
usado a definição visível na declaração da função. Isso podia tornar falsa a
igualdade de dois valores que deveriam ser iguais.

`ExpRegistro` agora guarda o tipo nominal resolvido durante a verificação e o
utiliza ao criar o valor. A clonagem da expressão preserva essa ligação. Não
se trata de estado global: a ligação pertence ao nó da AST. Para ASTs avaliadas
diretamente sem verificação, permanece a resolução pelo ambiente de execução;
o caminho suportado pelo CLI sempre verifica o programa antes de executá-lo.

Essa mudança substitui a resolução exclusivamente dinâmica do construtor
registrada na etapa 1. Não altera o modelo geral de escopo de variáveis e funções
da base, nem implementa closures.

**Restauração de ambientes.** `Aplicacao.avaliar`, `ExpDeclaracao.avaliar` e
`ExpDeclaracao.getTipo` agora restauram os escopos com `finally`, inclusive
quando ocorre um erro. Testes verificam que uma variável externa volta a ser
visível após a falha de uma função e após a falha de um `let`. A busca de função
na avaliação também passou a usar a exceção de identificador não declarado,
em vez de identificador já declarado, no respectivo caminho de erro.

### Diagnósticos

- O CLI separa `Erro de sintaxe`, `Erro de tipo` e `Erro de execução`, mantendo
  saída 1 em falhas de programa e saída 2 para opções inválidas.
- `ExpressaoLocalizada` envolve as expressões da extensão e chamadas de função
  com posição obtida dos tokens do JavaCC. A expressão mantém seu tipo e valor;
  a posição apenas acompanha os erros.
- `ErroExtensao` preserva a posição mais interna disponível. Chamadas acrescentam
  o nome da função, sem repetir uma cadeia inteira de contextos recursivos.
- Campos incompatíveis incluem o registro, o campo, o tipo esperado e o recebido.
  A posição aponta para o início do campo na construção.
- Campos repetidos e tipos de campo não suportados incluem posição nos erros
  sintáticos gerados manualmente pela gramática.

Por exemplo, `examples/erros/argumento-funcao.lf1` identifica a função
`somarIdades`, o argumento 1 e sua incompatibilidade com a exigência de `idade`
inteira. `examples/erros/head-vazia.lf1` produz um erro de execução na linha 1,
coluna 1.

A localização cobre as construções da extensão e chamadas. Erros herdados de
outras classes da base podem continuar sem posição; esta etapa não reescreve
todo o sistema de diagnósticos do PLP.

### Organização do protótipo

O código independente foi movido de `br.ufpe.cin.lf1.{ast,eval,types,values,
typechecker}` para `br.ufpe.cin.lf1.prototipo`, junto com `DemoRegistros`.
Seu comportamento histórico foi preservado. O comando `--demo-registros`
continua disponível e agora identifica explicitamente que não executa `.lf1`.

Os dois desenhos antigos de arquitetura foram movidos de `etc/` para
`docs/historico/`, com uma [explicação do histórico](historico/README.md).
O pacote integrado `lf1.plp` continua sem depender dessas classes.

### Validação

Foram acrescentados 11 testes em `ConsolidacaoTest`: identidade nominal em
funções sob sombreamento, clonagem, recuperação de escopo após erros de tipo e
execução, localização de campos e chamadas, erros sintáticos manuais, fases do
CLI, UTF-8, arquivo ausente, opções inválidas, identificação do protótipo,
manifesto dos exemplos e programas completos do README.

As funcionalidades permanecem no escopo inicial: campos primitivos, listas de
registros, três operações de listas e funções de primeira ordem. Campos
compostos e novas operações não foram adicionados nesta consolidação.

Validação final concluída com JDK 25 e Maven:

- `mvn clean verify`: **61 testes, sem falhas ou erros**.
- **16 exemplos executados pelo JAR**, com resultados e códigos de saída
  conferidos contra `examples/resultados.tsv`, incluindo os seis erros esperados.
- Entrada padrão, `--help` e `--demo-registros` conferidos no JAR.
- Links locais do README, UPSTREAM e documentos de `docs/` conferidos.
- JAR inspecionado: protótipo presente no pacote novo, sem classes residuais dos
  pacotes anteriores após a compilação limpa.
- `git diff --check` sem problemas de espaços. Nenhum commit foi criado.

Permanecem os três avisos conhecidos do JavaCC em `==`, `+` e `and` e o aviso de
operações não verificadas em `StackHandler`, herdados da base; a geração do
parser, a compilação e os testes terminam com sucesso.


## Restauração do README da proposta (29/09/2026)

O README foi restaurado a partir do texto original fornecido pela autora,
preservando seus objetivos, escopo, exemplos ilustrativos e observação sobre
adaptação da sintaxe. Os links de documentação foram mantidos.

O conteúdo consolidado da implementação foi preservado em
[GUIA_EXECUCAO.md](GUIA_EXECUCAO.md), com os links relativos ajustados. O teste
que executava os exemplos completos do README agora lê esse guia. A linguagem
e seus exemplos executáveis não foram alterados nesta reorganização.


## Etapa 4 — listas genéricas e campos compostos (06/10/2026)

A orientação do professor amplia o escopo: listas passam a ser uma construção
independente dos registros. Um registro pode estar dentro de uma lista, e uma
lista pode ser o valor de um campo de registro.

### Regras da linguagem

- Uma lista tem tipo `[T]`, em que `T` pode ser `Int`, `String`, `Boolean`, um
  tipo de registro ou outro tipo de lista. Exemplos: `[1, 2]`, `["a", "b"]`,
  `[true, false]` e `[[1, 2], [3]]`.
- Cada lista continua homogênea: `[1, true]` é rejeitada. A generalização permite
  escolher o tipo do elemento, sem misturar tipos incompatíveis na mesma lista.
- Campos aceitam tipos primitivos, nomes de registros em escopo e tipos de lista
  escritos recursivamente: `idades: [Int]`, `pessoas: [Pessoa]`,
  `matriz: [[Int]]` ou `responsavel: Pessoa`.
- Tipos de registro continuam nominais. Declarações distintas com o mesmo nome
  não se tornam intercambiáveis quando usadas em campos ou listas.
- `[]` começa com elemento de tipo ainda desconhecido; o contexto pode determinar
  esse tipo. `head`, `tail` e `isEmpty` funcionam com qualquer lista.
- `head` retorna um elemento do tipo `T`; `tail` retorna `[T]`; `isEmpty` retorna
  `Boolean`. `head([])` e `tail([])` continuam erros de execução.
- Registros e listas permanecem imutáveis, inclusive quando aninhados.
- A extensão mantém as funções de primeira ordem da LF1. Funções não são valores
  que possam ser armazenados em listas; novas operações de lista não foram
  acrescentadas.

A EBNF e a gramática JavaCC agora incluem a produção recursiva:

```ebnf
TipoCampo ::= "Int" | "String" | "Boolean" | ID | "[" TipoCampo "]"
```

### Alterações no código

- `TipoLista` usa uma variável de tipo livre para elementos desconhecidos;
  `ExpLista` unifica os tipos dos elementos sem exigir registros.
- `ValorLista` armazena valores da interface `Valor`, mantendo cópia imutável.
  A avaliação também confere a compatibilidade dos tipos dos elementos.
- `Inferencia.lista` reconhece listas genéricas. `Inferencia.campo` permite
  inferir campos que são listas ou registros, além dos tipos primitivos.
- A unificação verifica ciclos nas restrições de campos nos dois sentidos,
  evitando tipos infinitos e recursão indevida durante a inferência.
- `TipoNomeado` resolve nomes de tipos de campo no ambiente da declaração;
  `TipoRegistro` resolve seus campos antes de verificar o corpo do `let`.
  Nomes inexistentes produzem erro de tipo com linha e coluna.
- `Functional1.jj` aceita nomes de registro e `[TipoCampo]` nas anotações.
  As operações existentes reaproveitam o tipo genérico, sem nova sintaxe.

Essas alterações chegam a `checaTipo` pelo percurso de verificação das
expressões. Por exemplo, `idades: [Int]` aceita `[23, 25]`, mas rejeita
`[true, false]` antes de executar o programa. Uma função que usa
`head(pessoa.idades)` passa a inferir que o campo `idades` precisa ser uma lista.

### Exemplos para executar e apresentar

| Programa | Demonstração | Resultado |
| --- | --- | --- |
| [inteiros.lf1](../examples/listas/inteiros.lf1) | Soma recursiva de uma lista de inteiros | `6` |
| [textos.lf1](../examples/listas/textos.lf1) | `head` e `tail` em strings | `Bruno` |
| [booleanos.lf1](../examples/listas/booleanos.lf1) | Lista de booleanos | `false` |
| [aninhadas.lf1](../examples/listas/aninhadas.lf1) | Lista de listas | `3` |
| [campos-lista.lf1](../examples/registros/campos-lista.lf1) | Campos com listas de diferentes tipos | `25` |
| [lista-no-registro.lf1](../examples/registros/lista-no-registro.lf1) | Registro contendo uma lista de registros, processada recursivamente | `48` |
| [lista-tipos-incompativeis.lf1](../examples/erros/lista-tipos-incompativeis.lf1) | Lista heterogênea | Erro de tipo |
| [campo-lista.lf1](../examples/erros/campo-lista.lf1) | Campo `[Int]` recebendo booleanos | Erro de tipo |

O README mantém o texto original e seus links, com uma nota datada sobre a
ampliação. O guia de execução, a EBNF e o roteiro de apresentação descrevem o
escopo atual. O protótipo histórico permanece separado e inalterado.

### Testes

`ListasGenericasTest` acrescenta 22 testes para tipos primitivos, listas
aninhadas, campos compostos, inferência, recursão, escopo nominal, imutabilidade,
igualdade, erros e proteção contra ciclos de inferência. Os testes anteriores
foram ajustados somente onde a restrição antiga deixou de valer. O manifesto
passa a conter 24 exemplos, e os programas completos do guia também são
executados pelos testes de consolidação.

Validação final desta etapa, com JDK 25 e Maven:

- `mvn clean verify`: **83 testes, sem falhas, erros ou testes ignorados**.
- **24 exemplos executados pelo JAR**, com resultado e código de saída
  conferidos contra o manifesto, incluindo os erros esperados.
- Programa `Aluno` com campo `notas: [Int]` executado por entrada padrão;
  `head(aluno.notas)` sobre `[8, 9, 10]` retornou `8`.
- **33 links locais** da documentação conferidos.
- `git diff --check` sem problemas. Nenhum commit foi criado.

Permanecem os três avisos conhecidos do JavaCC e o aviso de operações não
verificadas da base; a compilação e os testes concluíram com sucesso.
