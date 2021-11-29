package br.com.rchlo.test;

import br.com.rchlo.domain.Cor;
import br.com.rchlo.domain.ListaDeProdutos;
import br.com.rchlo.domain.Produto;
import br.com.rchlo.domain.Tamanho;

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

    private static Map<Tamanho, List<Produto>> exibeTamanhoDeCadaProduto() {

        return Arrays.stream(Tamanho.values())
                .collect(toMap(Function.identity(), TestaMetodosProdutos::produtosPorTamanho));

    }

    private static Map<Cor, Integer> exibeQuantidadeDeCorPorProduto() {

        return Arrays.stream(Cor.values())
                .collect(toMap(Function.identity(), TestaMetodosProdutos::quantidadePorCor));
    }

    private static void exibeProdutoComMaiorPreco() {

        Produto maiorPreco = ListaDeProdutos.lista().stream()
                .max(comparing(Produto::getPreco))
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado!"));

        System.out.println(maiorPreco);
    }

    private static void exibeMenorPrecoDeUmProdutoComDesconto() {

        Produto produtoComMenorDesconto = ListaDeProdutos.lista().stream()
                .filter(Produto::temDesconto)
                .min(comparing(Produto::getPreco))
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado!"));

        System.out.println(produtoComMenorDesconto.getPreco());
    }

    private static void temCamisetaCinza() {

        boolean camisetaCinza = ListaDeProdutos.lista().stream()
                .anyMatch(p -> p.getNome().contains("Camiseta") && p.getCor().equals(Cor.CINZA));
        System.out.println(camisetaCinza);
    }

    private static void exibeTodasCamisetasBrancas() {

        ListaDeProdutos.lista().stream()
                .filter(p -> p.getNome().contains("Camiseta") && p.getCor().equals(Cor.BRANCA))
                .forEach(System.out::println);
    }

    private static void exibeTodasCamisetas() {

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
