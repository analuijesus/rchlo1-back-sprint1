package br.com.rchlo.test;

import br.com.rchlo.domain.Cor;
import br.com.rchlo.domain.ListaDeProdutos;
import br.com.rchlo.domain.Produto;
import br.com.rchlo.domain.Tamanho;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toMap;

public class TestaMetodosProdutos {

    public static void main(String[] args) {

    }

    public static Map<Tamanho, List<Produto>> buscaTamanhoDeCadaProduto() {

        return Arrays.stream(Tamanho.values())
                .collect(toMap(Function.identity(), TestaMetodosProdutos::produtosPorTamanho));

    }

    public static Map<Cor, Integer> buscaQuantidadeDeCorPorProduto() {

        return Arrays.stream(Cor.values())
                .collect(toMap(Function.identity(), TestaMetodosProdutos::quantidadePorCor));

    }

    public static void exibeProdutoComMaiorPreco() {

        Produto maiorPreco = ListaDeProdutos.lista().stream()
                .max(comparing(Produto::getPreco))
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado!"));

        System.out.println(maiorPreco);
    }

    public static void exibeMenorPrecoDeUmProdutoComDesconto() {

        BigDecimal menorPreco = ListaDeProdutos.lista().stream()
                .filter(Produto::temDesconto)
                .map(Produto::getPreco)
                .min(comparing(Function.identity()))
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado!"));

        System.out.println(menorPreco);
    }

    public static void temCamisetaCinza() {

        boolean camisetaCinza = ListaDeProdutos.lista().stream()
                .anyMatch(p -> p.getNome().contains("Camiseta") && Cor.CINZA.equals(p.getCor()));
        System.out.println(camisetaCinza);
    }

    public static void exibeTodasCamisetasBrancas() {

        ListaDeProdutos.lista().stream()
                .filter(p -> p.getNome().contains("Camiseta") && Cor.BRANCA.equals(p.getCor()))
                .forEach(System.out::println);
    }

    public static void exibeTodasCamisetas() {

        ListaDeProdutos.lista().stream()
                .filter(p -> p.getNome().contains("Camiseta"))
                .forEach(System.out::println);
    }

    private static List<Produto> produtosPorTamanho(Tamanho tamanho) {

        return ListaDeProdutos.lista().stream()
                .filter(p -> p.getTamanhosDisponiveis().contains(tamanho))
                .collect(Collectors.toList());
    }

    private static int quantidadePorCor(Cor cor) {

        return (int) ListaDeProdutos.lista().stream()
                .filter(p -> p.getCor().equals(cor)).count();
    }
}