# Extensão da Linguagem Funcional 1: Registros e Listas Imutáveis

## Tema

Extensão da Linguagem Funcional 1 com suporte a **registros imutáveis**, semelhantes às `structs` da linguagem C, e **listas imutáveis de registros**.

## Descrição da proposta

Este projeto propõe ampliar a Linguagem Funcional 1 por meio da implementação de registros capazes de agrupar, em uma única estrutura, campos com nomes e tipos diferentes.

Um registro poderá representar, por exemplo, uma pessoa que possui nome, idade e situação:

```text
Pessoa {
    nome: String,
    idade: Int,
    ativo: Bool
}
```

Um valor desse tipo poderia ser criado da seguinte forma:

```text
pessoa = Pessoa {
    nome: "Monique",
    idade: 23,
    ativo: true
}
```

Embora o registro seja semelhante a uma classe por reunir informações relacionadas, ele conterá apenas dados. No contexto funcional, não haverá métodos associados ao registro: seu processamento será realizado por funções externas.

Além disso, os registros serão imutáveis. Portanto, seus campos não poderão ser modificados diretamente após a criação. Uma operação de atualização deverá produzir um novo registro, preservando o original.

## Listas imutáveis

A linguagem também será estendida para aceitar listas imutáveis, incluindo listas vazias e listas de registros:

```text
[]
[1, 2, 3]

pessoas = [
    Pessoa { nome: "Monique", idade: 23, ativo: true },
    Pessoa { nome: "Bruno", idade: 25, ativo: true }
]
```

As listas deverão possuir elementos de tipos compatíveis. Por exemplo, uma lista de pessoas será representada pelo tipo `List<Pessoa>`.

## Operações sobre listas

Inicialmente, serão implementadas as seguintes operações:

- `head(lista)`: retorna o primeiro elemento;
- `tail(lista)`: retorna uma nova lista sem o primeiro elemento;
- `isEmpty(lista)`: verifica se a lista está vazia;
- `size(lista)`: retorna a quantidade de elementos;
- `append(lista, elemento)`: retorna uma nova lista com o elemento adicionado, sem alterar a lista original.

Exemplo:

```text
listaOriginal = [1, 2, 3]
novaLista = append(listaOriginal, 4)
```

Resultado:

```text
listaOriginal = [1, 2, 3]
novaLista      = [1, 2, 3, 4]
```

## Funções e recursão

Como a Linguagem Funcional 1 já possui funções recursivas, as novas estruturas poderão ser percorridas e processadas sem a necessidade de laços de repetição.

O exemplo abaixo demonstra uma função que soma as idades de uma lista de pessoas:

```text
fun somarIdades(pessoas) =
    if isEmpty(pessoas)
    then 0
    else head(pessoas).idade + somarIdades(tail(pessoas))
```

As funções também poderão acessar os campos dos registros:

```text
fun obterNome(pessoa) =
    pessoa.nome
```

## Objetivo geral

Implementar registros e listas imutáveis na Linguagem Funcional 1, permitindo a representação de dados estruturados e seu processamento por meio de funções recursivas.

## Objetivos específicos

- permitir a declaração de tipos de registro;
- permitir a criação de valores de registros;
- permitir o acesso aos campos, como `pessoa.nome`;
- aceitar campos de tipos diferentes dentro de um mesmo registro;
- garantir a validação dos nomes e tipos dos campos;
- preservar a imutabilidade dos registros e das listas;
- implementar listas de valores simples e listas de registros;
- adicionar operações básicas para manipulação de listas;
- integrar registros e listas às funções recursivas existentes;
- tratar erros de tipo e operações inválidas.

## Alterações previstas na linguagem

A implementação deverá envolver mudanças nas seguintes partes:

1. **Gramática e análise léxica:** reconhecimento da sintaxe de registros, listas, acesso a campos e novas operações.
2. **Parser e AST:** criação das representações de registros, campos, listas e operações sobre listas.
3. **Sistema de tipos:** inclusão dos tipos de registro e `List<T>`, com verificação da compatibilidade dos valores.
4. **Interpretador:** avaliação da criação de registros, acesso aos campos e operações sobre listas.
5. **Tratamento de erros:** identificação de campos inexistentes, tipos incompatíveis e operações inválidas, como `head([])`.
6. **Testes:** validação individual e integrada das novas funcionalidades.

## Exemplos de erros que deverão ser tratados

```text
Pessoa { nome: "Monique", idade: true }
```

Erro esperado: o campo `idade` deveria receber um valor do tipo `Int`.

```text
pessoa.endereco
```

Erro esperado: o campo `endereco` não foi declarado no registro `Pessoa`.

```text
head([])
```

Erro esperado: não é possível obter o primeiro elemento de uma lista vazia.

```text
append([1, 2], true)
```

Erro esperado: não é possível adicionar um valor `Bool` a uma lista do tipo `List<Int>`.

## Escopo inicial

A primeira versão do projeto deverá contemplar:

- registros imutáveis;
- campos nomeados com tipos diferentes;
- criação de registros e acesso aos campos;
- listas imutáveis;
- listas de registros;
- operações `head`, `tail`, `isEmpty`, `size` e `append`;
- processamento recursivo de listas;
- verificação de tipos;
- testes e exemplos de utilização.

Operações de ordem superior, como `map`, `filter` e `fold`, poderão ser consideradas extensões futuras caso seja necessário ampliar o projeto.

## Tecnologias

As tecnologias e ferramentas utilizadas seguirão a implementação-base disponibilizada para a Linguagem Funcional 1 na disciplina.

## Autores

- Monique Campos
- Integrante da dupla

## Observação

Os exemplos apresentados neste documento representam a sintaxe proposta. A sintaxe definitiva poderá ser ajustada durante a alteração da gramática da linguagem.
