# Implementação da extensão da Funcional 1

Este documento acompanha a implementação de registros imutáveis e listas de
registros sobre a base `lf1.plp` do PLP. O README apresenta a proposta; aqui são
registradas as decisões, funcionalidades entregues, testes e próximas etapas.

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

### Próximas etapas

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
