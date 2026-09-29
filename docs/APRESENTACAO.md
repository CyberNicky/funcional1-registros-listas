# Roteiro de apresentação

Execute os comandos a partir da pasta `funcional1-registros-listas`, usando
JDK 25 e Maven. Todos os exemplos listados estão no
[manifesto de resultados](../examples/resultados.tsv), conferido pelos testes.

## Preparação

```sh
mvn clean verify
```

A compilação gera `target/lf1-records-lists-1.0-SNAPSHOT.jar`.

## Sequência sugerida

| Ordem | Arquivo | Resultado | O que explicar |
| --- | --- | --- | --- |
| 1 | `examples/funcional1/fatorial.lf1` | `120` | A linguagem-base e sua recursão continuam funcionando |
| 2 | `examples/registros/pessoa.lf1` | `Monique` | Tipo de registro, construção e acesso a campo |
| 3 | `examples/registros/obter-nome.lf1` | `Monique` | Processamento externo ao registro, sem métodos ou anotações de parâmetro |
| 4 | `examples/registros/listas.lf1` | `48` | Lista homogênea, `head` e `tail` |
| 5 | `examples/registros/imutabilidade.lf1` | `Monique / Bruno` | `tail` não altera a lista original |
| 6 | `examples/registros/somar-idades.lf1` | `48` | Caso-base, acesso a campos e chamada recursiva |
| 7 | `examples/registros/contar.lf1` | `2` | Recursão que depende da lista, sem consultar campos |
| 8 | `examples/registros/lista-vazia.lf1` | `0` | Inferência do elemento e término no caso-base |

Exemplo de comando:

```sh
java -jar target/lf1-records-lists-1.0-SNAPSHOT.jar examples/registros/somar-idades.lf1
```

Para os demais, substitua o último argumento pelo caminho da tabela. Todos
esses programas devem terminar com código 0.

## Demonstração dos erros

| Arquivo | Fase | Evidência |
| --- | --- | --- |
| `examples/erros/tipo-campo.lf1` | Tipos | Campo `idade`, tipo esperado e recebido, linha e coluna |
| `examples/erros/campo-inexistente.lf1` | Tipos | Campo que não pertence a `Pessoa` |
| `examples/erros/lista-heterogenea.lf1` | Tipos | Registros de tipos nominais diferentes |
| `examples/erros/argumento-funcao.lf1` | Tipos | Função `somarIdades` e argumento 1 incompatível |
| `examples/erros/head-vazia.lf1` | Execução | Lista vazia não possui primeiro elemento |
| `examples/erros/atribuicao-campo.lf1` | Sintaxe | Não existe atribuição a campos |

```sh
java -jar target/lf1-records-lists-1.0-SNAPSHOT.jar examples/erros/argumento-funcao.lf1
java -jar target/lf1-records-lists-1.0-SNAPSHOT.jar examples/erros/atribuicao-campo.lf1
```

Todos os exemplos de erro devem terminar com código 1. São falhas esperadas da
linguagem, e não falhas da apresentação. `head([])` passa pela verificação de
tipos e falha se executado; a verificação não prova que uma lista é não vazia.

## Relação com o PLP

O fluxo é `Main` → parser JavaCC → `Programa.checaTipo()` →
`Programa.executar()` → resultado. As novas expressões implementam as interfaces
da Funcional 1. A inferência mantém restrições de campos e relações entre
parâmetros e resultados; chamadas recursivas compartilham uma assinatura.

A implementação não depende do interpretador do protótipo histórico. Para
mostrar a extensão, utilize os arquivos `.lf1`, não `--demo-registros`.

## Pontos para a conclusão da apresentação

- Registros agrupam dados heterogêneos e permanecem imutáveis.
- Listas contêm registros de um único tipo nominal.
- Funções externas e recursão processam esses dados.
- Os testes incluem casos válidos, erros, escopo, imutabilidade e regressão da base.
- Campos compostos, listas genéricas e novas operações não fazem parte da entrega.
