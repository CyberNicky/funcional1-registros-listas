# Protótipo histórico de registros e listas

O primeiro protótipo construía nós Java manualmente e possuía seus próprios
`ASTNode`, `TypeChecker`, `Interpreter` e `Value`. Ele não lia programas `.lf1`
nem participava dos ambientes ou da recursão da Funcional 1.

Seu código foi preservado em
[`br.ufpe.cin.lf1.prototipo`](../../src/main/java/br/ufpe/cin/lf1/prototipo/).
A mudança de pacote explicita que ele não é o interpretador integrado. O comando
anterior continua disponível, agora com uma mensagem identificando o histórico:

```sh
java -jar target/lf1-records-lists-1.0-SNAPSHOT.jar --demo-registros
```

Execute-o da raiz do projeto após `mvn clean verify`.
O protótipo mantém suas limitações originais, incluindo a tipagem simplificada
de listas vazias. Ele não é a referência para o comportamento da linguagem atual.

Os desenhos originais, anteriormente em `etc/`, foram preservados aqui:

- [Estrutura original do protótipo](estrutura-prototipo.txt)
- [Fluxo proposto originalmente](fluxo-prototipo.txt)

Esses desenhos são históricos: caminhos, nomes e fluxo não descrevem a versão
integrada. A arquitetura atual está no [guia de execução](../GUIA_EXECUCAO.md) e na
[documentação da implementação](../IMPLEMENTACAO.md).
