
package model;

public abstract class Criptomoeda {
    protected String nome;
    protected double taxaCompra;
    protected double taxaVenda;
    protected double cotacaoAtual;

    public Criptomoeda(String nome, double taxaCompra, double taxaVenda, double cotacaoInicial) {
        this.nome = nome;
        this.taxaCompra = taxaCompra;
        this.taxaVenda = taxaVenda;
        this.cotacaoAtual = cotacaoInicial;
    }
    public double comprar(double valorReais) {
        double valorTaxa = valorReais * taxaCompra;
        double valorLiquido = valorReais - valorTaxa;
        return valorLiquido / cotacaoAtual; // Retorna a quantidade de criptomoeda comprada
    }

    public double vender(double quantidade) {
        double valorBruto = quantidade * cotacaoAtual;
        double valorTaxa = valorBruto * taxaVenda;
        return valorBruto - valorTaxa; // Retorna o valor em reais após a venda
    }

    // Getters e Setters para cotação
    public double getCotacaoAtual() {
        return cotacaoAtual;
    }

    public void setCotacaoAtual(double cotacaoAtual) {
        this.cotacaoAtual = cotacaoAtual;
    }
}
