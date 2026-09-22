# Origem da Funcional 1

> O registro abaixo descreve a importação e a conferência da base antes da
> extensão. A gramática foi posteriormente estendida; as alterações e o estado
> atual estão em [Documentação da implementação](docs/IMPLEMENTACAO.md).

A implementação em `src/main/java/lf1/plp/` e a gramática
`src/main/javacc/Functional1.jj` foram importadas da pasta `Funcional1` do
repositório da disciplina de Augusto Sampaio:

https://github.com/AugustoSampaio/PLP/tree/4620faf5f6b08859d19e05c15711e6709d699ad9/Funcional1

Commit: `4620faf5f6b08859d19e05c15711e6709d699ad9`.

Os exemplos `fatorial.lf1` e `funcoes.lf1` correspondem respectivamente a
`input` e `input2` dessa versão. `variaveis.lf1` foi adicionado neste projeto.

## Adaptações para integração

- Fontes colocados na estrutura Maven `src/main/java`, preservando os pacotes.
- Arquivos de texto convertidos para UTF-8 quando necessário.
- Gramática colocada em `src/main/javacc`, com `STATIC = false` para permitir
  instâncias independentes do parser, inclusive nos testes.
- JavaCC executado automaticamente pelo Maven na geração de fontes.
- Entrada principal própria em `br.ufpe.cin.lf1.Main`, com leitura UTF-8,
  apresentação dos resultados e códigos de saída para erros.

As regras sintáticas e a implementação de avaliação e tipos da base foram
preservadas. Esta integração não incorpora ainda os registros e listas do
protótipo à linguagem original, nem corrige todas as limitações da base.

Na geração do parser, o JavaCC informa três avisos de ambiguidade nas repetições
que reconhecem `==`, `+` e `and`, presentes na gramática original. A geração e a
compilação terminam com sucesso; as regras foram mantidas nesta integração.

Validação: oito testes automatizados cobrem os exemplos, recursão, escopo,
operadores, UTF-8 e rejeição de erros sintáticos e de tipo. A execução do JAR
foi conferida com arquivos, entrada padrão e a demonstração de registros.

## Conferência com a cópia local PLP

Em 22/09/2026, a integração foi comparada com `PLP/Funcional1`, no mesmo
commit indicado acima:

- As 50 classes Java coincidem após normalizar codificação e quebras de linha;
  não há classes ausentes ou adicionais no pacote importado `lf1`.
- A única alteração na gramática é `STATIC = false`; as produções são iguais.
- Os exemplos `input` e `input2` coincidem com as cópias em `examples/funcional1`.
- O `Main.java` anterior foi preservado em `DemoRegistros.java`, mudando somente
  o nome da classe. A nova entrada chama o parser, `checaTipo()` e `executar()`.
- `mvn clean verify` passou com oito testes. Os arquivos originais
  `PLP/Funcional1/input`, `PLP/Funcional1/input2` e
  `PLP/Testes/TesteFuncional1.txt` retornaram respectivamente 120, 14 e 120.

O POM original usa Java 8 e JavaCC Maven Plugin 2.6. A integração usa o JDK 25
já exigido pelo projeto e JavaCC Maven Plugin 3.2.0. Os fontes gerados entram
na compilação pelo próprio plugin, dispensando o `build-helper` do POM original.
A execução pelo JAR substitui o uso de `exec:java` nas instruções deste projeto.

A pasta `PLP/` é uma referência independente: o Maven deste projeto compila
`src/main/`, não os módulos de interfaces e outras linguagens dentro de `PLP/`.
A extensão deve ser desenvolvida sobre a cópia integrada em `src/main/`.

Como conferência adicional, uma cópia temporária de `PLP/Funcional1` foi
compilada com seu POM original, Java 8 e
`-Dproject.build.sourceEncoding=ISO-8859-1`. A cópia fornecida em `PLP/` não foi
alterada. Em oito comparações de execução entre a base original e a integração,
os resultados coincidiram: cinco programas válidos (fatorial, funções, escopo,
condicional e booleanos) e três rejeições (tipo, sintaxe e quantidade de
argumentos). A entrada nova sinaliza falhas com código de saída não zero,
enquanto a entrada original usa código zero em alguns desses erros.
