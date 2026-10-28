# Extensão da Linguagem Funcional 1: Registros Imutáveis e Listas de Registros

- [Documentação da implementação e histórico das etapas](docs/IMPLEMENTACAO.md)
- [Roteiro de apresentação e resultados esperados](docs/APRESENTACAO.md)
- [Origem da base e adaptações](UPSTREAM.md)
- [Protótipo histórico](docs/historico/README.md)
- [Guia de execução e exemplos implementados](docs/GUIA_EXECUCAO.md)


> **Atualização de escopo — 06/10/2026:** por orientação do professor, a extensão
> passou a aceitar listas de qualquer tipo de dado da Funcional 1, incluindo
> listas aninhadas, e campos de registro com tipos compostos como `[Int]` e
> `[Pessoa]`. Os elementos de cada lista continuam com tipos compatíveis.
> A seção "Proposta original" preserva o texto inicial. O funcionamento atual está no
> [guia de execução](docs/GUIA_EXECUCAO.md) e na
> [documentação da implementação](docs/IMPLEMENTACAO.md).

## Gramática da extensão: EBNF clicável

Clique no nome de uma produção, em um não terminal ou no nome de uma classe
para abrir a implementação correspondente. Os links são relativos ao projeto,
para acompanhar a versão do código no repositório.

A notação usa `[...]` para uma parte opcional e `{...}` para repetição.
Símbolos entre aspas são tokens da linguagem. Por exemplo, `"["` e `"]"`
são os delimitadores literais de uma lista.

<table>
<thead><tr><th>Produção clicável</th><th>EBNF</th><th>Implementação</th></tr></thead>
<tbody>
<tr><td><a href="src/main/java/lf1/plp/functional1/extension/ExpDeclaracaoRegistro.java#L11">DeclRegistro</a></td><td><code>&quot;let&quot; &quot;record&quot; <a href="src/main/java/lf1/plp/expressions2/expression/Id.java">ID</a> &quot;{&quot; [ <a href="src/main/java/lf1/plp/functional1/extension/ExpDeclaracaoRegistro.java#L11">DeclCampos</a> ] &quot;}&quot; &quot;in&quot; <a href="src/main/java/lf1/plp/expressions2/expression/Expressao.java">Expressao</a></code></td><td><a href="src/main/java/lf1/plp/functional1/extension/ExpDeclaracaoRegistro.java#L11">ExpDeclaracaoRegistro</a></td></tr>
<tr><td><a href="src/main/java/lf1/plp/functional1/extension/ExpDeclaracaoRegistro.java#L11">DeclCampos</a></td><td><code><a href="src/main/java/lf1/plp/functional1/extension/ExpDeclaracaoRegistro.java#L11">DeclCampo</a> { &quot;,&quot; <a href="src/main/java/lf1/plp/functional1/extension/ExpDeclaracaoRegistro.java#L11">DeclCampo</a> }</code></td><td><a href="src/main/java/lf1/plp/functional1/extension/TipoRegistro.java#L12">TipoRegistro</a></td></tr>
<tr><td><a href="src/main/java/lf1/plp/functional1/extension/ExpDeclaracaoRegistro.java#L11">DeclCampo</a></td><td><code><a href="src/main/java/lf1/plp/expressions2/expression/Id.java">ID</a> &quot;:&quot; <a href="src/main/javacc/Functional1.jj#L444">TipoCampo</a></code></td><td><a href="src/main/java/lf1/plp/functional1/extension/TipoRegistro.java#L12">TipoRegistro</a></td></tr>
<tr><td><a href="src/main/javacc/Functional1.jj#L444">TipoCampo</a></td><td><code>&quot;Int&quot; | &quot;String&quot; | &quot;Boolean&quot; | <a href="src/main/java/lf1/plp/expressions2/expression/Id.java">ID</a> | &quot;[&quot; <a href="src/main/javacc/Functional1.jj#L444">TipoCampo</a> &quot;]&quot;</code></td><td><a href="src/main/java/lf1/plp/expressions1/util/TipoPrimitivo.java#L12">TipoPrimitivo</a><br/><a href="src/main/java/lf1/plp/functional1/extension/TipoLista.java#L8">TipoLista</a><br/><a href="src/main/java/lf1/plp/functional1/extension/TipoNomeado.java#L8">TipoNomeado</a></td></tr>
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

O resumo acima corresponde a [etc/EBNF.txt](etc/EBNF.txt).
A gramática completa que gera o parser está em
[Functional1.jj](src/main/javacc/Functional1.jj).
`TipoCampo` abre a produção `PTipoCampo` do JavaCC; suas representações são
`TipoPrimitivo`, `TipoNomeado` e `TipoLista`, também acessíveis na tabela.

A gramática define a sintaxe. A compatibilidade dos elementos das listas,
a identidade nominal dos registros e a conferência dos campos são regras
semânticas implementadas nas classes indicadas e em
[Inferencia](src/main/java/lf1/plp/functional1/util/Inferencia.java).
Listas aceitam qualquer tipo de dado suportado pela LF1, mantendo tipos
compatíveis entre seus elementos. Funções como valores permanecem fora do escopo.

## Proposta original

O texto a seguir preserva a proposta da primeira apresentação. As restrições
iniciais a listas de registros foram ampliadas na implementação atual,
conforme a atualização de 06/10/2026 e a gramática acima.

## Tema

Extensão da Linguagem Funcional 1 com suporte a registros imutáveis e listas de registros.

## Descrição da proposta

O projeto propõe adicionar à Linguagem Funcional 1 uma estrutura de registro semelhante à struct da linguagem C. Um registro permitirá reunir, em um único valor, campos com nomes e tipos diferentes.

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

Nesse exemplo, pessoas será uma lista de valores do tipo Pessoa.

Para manter o escopo reduzido, esta proposta tratará especificamente de listas de registros. A implementação de listas genéricas para todos os tipos não faz parte da versão inicial.

## Operações sobre listas

Serão implementadas somente três operações:

- head(lista): retorna o primeiro registro da lista;
- tail(lista): retorna uma nova lista sem o primeiro registro;
- isEmpty(lista): verifica se a lista está vazia.
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

O isEmpty define o caso-base da recursão. O head acessa o primeiro registro e o tail fornece os demais registros para a próxima chamada da função.

## Objetivo geral

Implementar registros imutáveis e listas de registros na Linguagem Funcional 1, possibilitando a representação de dados estruturados e seu processamento por meio de funções recursivas.

## Objetivos específicos

- permitir a declaração de tipos de registro;
- permitir a criação de valores de registros;
- aceitar campos com nomes e tipos diferentes dentro de um registro;
- permitir o acesso aos campos;
- garantir a imutabilidade dos registros;
- permitir listas imutáveis de registros do mesmo tipo;
- implementar as operações head, tail e isEmpty;
- permitir o processamento recursivo das listas;
- validar os tipos dos campos e dos elementos das listas;
- tratar campos inexistentes e operações inválidas.

## Alterações previstas

A implementação envolverá mudanças nas seguintes partes da linguagem:

1. Gramática: reconhecimento da declaração e criação de registros, listas, acesso a campos e operações sobre listas.
2. Parser e AST: representação interna dos registros, campos, listas e novas operações.
3. Sistema de tipos: validação dos campos e garantia de que uma lista contenha somente registros do mesmo tipo.
4. Interpretador: criação dos registros, acesso aos campos e execução de head, tail e isEmpty.
5. Tratamento de erros: identificação de campos inexistentes, valores com tipos incorretos e operações inválidas.
6. Testes: verificação das novas funcionalidades e de sua integração com a recursão.

## Exemplos de erros

### Tipo de campo incompatível

```text
Pessoa { nome: "Monique", idade: true }
```

Erro esperado: o campo idade deve receber um valor do tipo Int.

### Campo inexistente

```text
pessoa.endereco
```

Erro esperado: o campo endereco não existe no registro Pessoa.

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
- operações head, tail e isEmpty;
- funções recursivas para processar as listas;
- verificação básica de tipos;
- tratamento de erros e testes.

## Tecnologias

As tecnologias e ferramentas seguirão a implementação-base da Linguagem Funcional 1 disponibilizada na disciplina.

## Autores

- Monique Campos
- Efraim Tenório

## Observação

Os exemplos deste documento apresentam uma sintaxe inicial para explicar a proposta. A sintaxe definitiva poderá ser ajustada de acordo com a gramática e a implementação-base da Linguagem Funcional 1.
