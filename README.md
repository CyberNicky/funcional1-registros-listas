# Extensão da Linguagem Funcional 1: Registros Imutáveis e Listas de Registros

## Documentação da implementação

O andamento, as decisões técnicas, a sintaxe executável, os testes e as próximas
etapas estão em [Documentação da implementação](docs/IMPLEMENTACAO.md).
Registros, acesso a campos, listas e suas operações já estão integrados à
linguagem. A inferência para funções que inspecionam registros e listas e o
processamento recursivo da proposta ainda estão pendentes.

## Tema

Extensão da Linguagem Funcional 1 com suporte a **registros imutáveis** e **listas de registros**.

## Descrição da proposta

O projeto propõe adicionar à Linguagem Funcional 1 uma estrutura de registro semelhante à `struct` da linguagem C. Um registro permitirá reunir, em um único valor, campos com nomes e tipos diferentes.

Por exemplo, um registro poderá representar uma pessoa com nome e idade:

```text
record Pessoa {
    nome: String,
    idade: Int
}
```

Um valor desse tipo poderá ser criado da seguinte forma:

```text
pessoa = Pessoa {
    nome: "Monique",
    idade: 23
}
```

Os registros conterão somente dados e não possuirão métodos. Seu processamento será realizado por funções externas, seguindo o paradigma funcional.

Os registros também serão imutáveis: depois de criado, um valor não poderá ter seus campos modificados diretamente.

## Acesso aos campos

A linguagem permitirá acessar os campos de um registro por meio de seus nomes:

```text
pessoa.nome
pessoa.idade
```

Também será possível utilizar esse acesso dentro de funções:

```text
fun obterNome(pessoa) =
    pessoa.nome
```

## Listas de registros

A linguagem permitirá criar listas imutáveis contendo registros do mesmo tipo:

```text
pessoas = [
    Pessoa { nome: "Monique", idade: 23 },
    Pessoa { nome: "Bruno", idade: 25 }
]
```

Nesse exemplo, `pessoas` será uma lista de valores do tipo `Pessoa`.

Para manter o escopo reduzido, esta proposta tratará especificamente de listas de registros. A implementação de listas genéricas para todos os tipos não faz parte da versão inicial.

## Operações sobre listas

Serão implementadas somente três operações:

- `head(lista)`: retorna o primeiro registro da lista;
- `tail(lista)`: retorna uma nova lista sem o primeiro registro;
- `isEmpty(lista)`: verifica se a lista está vazia.

Exemplos:

```text
head(pessoas)
tail(pessoas)
isEmpty(pessoas)
```

Como as listas são imutáveis, nenhuma dessas operações modificará a lista original.

## Processamento recursivo

As operações adicionadas poderão ser combinadas com as funções recursivas já existentes na Linguagem Funcional 1.

O exemplo abaixo representa uma função para somar as idades das pessoas presentes em uma lista:

```text
fun somarIdades(pessoas) =
    if isEmpty(pessoas)
    then 0
    else head(pessoas).idade + somarIdades(tail(pessoas))
```

O `isEmpty` define o caso-base da recursão. O `head` acessa o primeiro registro e o `tail` fornece os demais registros para a próxima chamada da função.

## Objetivo geral

Implementar registros imutáveis e listas de registros na Linguagem Funcional 1, possibilitando a representação de dados estruturados e seu processamento por meio de funções recursivas.

## Objetivos específicos

- permitir a declaração de tipos de registro;
- permitir a criação de valores de registros;
- aceitar campos com nomes e tipos diferentes dentro de um registro;
- permitir o acesso aos campos;
- garantir a imutabilidade dos registros;
- permitir listas imutáveis de registros do mesmo tipo;
- implementar as operações `head`, `tail` e `isEmpty`;
- permitir o processamento recursivo das listas;
- validar os tipos dos campos e dos elementos das listas;
- tratar campos inexistentes e operações inválidas.

## Alterações previstas

A implementação envolverá mudanças nas seguintes partes da linguagem:

1. **Gramática:** reconhecimento da declaração e criação de registros, listas, acesso a campos e operações sobre listas.
2. **Parser e AST:** representação interna dos registros, campos, listas e novas operações.
3. **Sistema de tipos:** validação dos campos e garantia de que uma lista contenha somente registros do mesmo tipo.
4. **Interpretador:** criação dos registros, acesso aos campos e execução de `head`, `tail` e `isEmpty`.
5. **Tratamento de erros:** identificação de campos inexistentes, valores com tipos incorretos e operações inválidas.
6. **Testes:** verificação das novas funcionalidades e de sua integração com a recursão.

## Exemplos de erros

### Tipo de campo incompatível

```text
Pessoa { nome: "Monique", idade: true }
```

Erro esperado: o campo `idade` deve receber um valor do tipo `Int`.

### Campo inexistente

```text
pessoa.endereco
```

Erro esperado: o campo `endereco` não existe no registro `Pessoa`.

### Registros de tipos diferentes na mesma lista

```text
[
    Pessoa { nome: "Monique", idade: 23 },
    Produto { nome: "Notebook", preco: 4000 }
]
```

Erro esperado: todos os registros da lista devem possuir o mesmo tipo.

### Operação em lista vazia

```text
head([])
```

Erro esperado: não é possível obter o primeiro elemento de uma lista vazia.

## Escopo 

- declaração de registros;
- criação de registros imutáveis;
- acesso aos campos;
- listas imutáveis de registros do mesmo tipo;
- operações `head`, `tail` e `isEmpty`;
- funções recursivas para processar as listas;
- verificação básica de tipos;
- tratamento de erros e testes.

## Tecnologias

As tecnologias e ferramentas seguirão a implementação-base da Linguagem Funcional 1 disponibilizada na disciplina.

## Como executar

Requisitos: **JDK 25** e **Apache Maven**. O código usa `IO.println`, disponível a partir do Java 25.

No macOS com Homebrew:

```sh
brew install openjdk@25 maven
export JAVA_HOME="$(brew --prefix openjdk@25)/libexec/openjdk.jdk/Contents/Home"
export PATH="$JAVA_HOME/bin:$PATH"
```

Na pasta do projeto, compile, execute os testes e gere o JAR:

```sh
mvn clean verify
```

Execute um programa da Funcional 1:

```sh
java -jar target/lf1-records-lists-1.0-SNAPSHOT.jar examples/funcional1/variaveis.lf1
# Resultado: 25

java -jar target/lf1-records-lists-1.0-SNAPSHOT.jar examples/funcional1/funcoes.lf1
# Resultado: 14

java -jar target/lf1-records-lists-1.0-SNAPSHOT.jar examples/funcional1/fatorial.lf1
# Resultado: 120
```

Também é possível passar um programa pela entrada padrão:

```sh
echo 'let fun somar x y = x + y in somar(2, 3)' | java -jar target/lf1-records-lists-1.0-SNAPSHOT.jar
```

Sem argumentos, o programa lê a entrada até EOF (Ctrl+D no terminal do macOS).
Os arquivos são lidos em UTF-8. Erros de sintaxe, tipos ou execução encerram o
comando com código diferente de zero.

### Organização da implementação

- `src/main/java/lf1/plp/`: implementação-base da disciplina, com expressões,
  declarações, tipos e ambientes de execução.
- `src/main/javacc/Functional1.jj`: gramática JavaCC da base, estendida com registros e listas. O Maven gera o
  parser automaticamente em `target/generated-sources/javacc`; edite o `.jj`,
  não os arquivos gerados.
- `src/main/java/br/ufpe/cin/lf1/Main.java`: entrada de linha de comando.
- `src/main/java/br/ufpe/cin/lf1/`: também contém o protótipo de registros e listas.
- `../PLP/`: cópia local de referência da disciplina; não participa da compilação
  deste projeto. Alterações para a extensão devem ser feitas em `src/main/`.
- `examples/funcional1/`: programas executáveis da linguagem-base.
- `examples/registros/`: programas executáveis da extensão.
- `src/main/java/lf1/plp/functional1/extension/`: tipos, valores e expressões da extensão integrada.
- `src/test/java/`: testes de integração do parser, tipos e interpretador.

A Funcional 1 e as construções básicas de registros e listas estão integradas.
Os exemplos acima continuam ilustrando a proposta; consulte a
[documentação da implementação](docs/IMPLEMENTACAO.md) para a sintaxe aceita
nesta etapa. Os exemplos completos podem ser executados com:

```sh
java -jar target/lf1-records-lists-1.0-SNAPSHOT.jar examples/registros/pessoa.lf1
# Resultado: Monique

java -jar target/lf1-records-lists-1.0-SNAPSHOT.jar examples/registros/listas.lf1
# Resultado: 48
```

A demonstração Java do protótipo independente continua disponível:

```sh
java -jar target/lf1-records-lists-1.0-SNAPSHOT.jar --demo-registros
```

Na sintaxe original, funções são declaradas como `fun somar x y = x + y` dentro
de `let ... in`; chamadas usam `somar(2, 3)`. O arquivo `etc/EBNF.txt` resume as
produções da extensão implementada; a gramática executada é o arquivo `.jj`.

Para conferir o ambiente, use `java -version` e `mvn -version`. Ambos devem indicar
Java 25. O Maven baixa automaticamente o JavaCC e os plugins necessários;
não é necessário instalar JavaCC separadamente.

A origem e as adaptações da base estão documentadas em [UPSTREAM.md](UPSTREAM.md).

## Autores

- Monique Campos
- Efraim Tenório

## Observação

Os exemplos deste documento apresentam uma sintaxe inicial para explicar a proposta. A sintaxe definitiva poderá ser ajustada de acordo com a gramática e a implementação-base da Linguagem Funcional 1.
