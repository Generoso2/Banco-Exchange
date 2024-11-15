
package controller;

import model.Moedas;

public class CotacaoControl {

    // Cotações das criptomoedas (em valores iniciais, podem ser ajustados)
    private double cotacaoBitcoin = 25000.00;
    private double cotacaoEthereum = 1500.00;
    private double cotacaoRipple = 0.25;

    // Método para atualizar a cotação das criptomoedas com uma variação de até ±5%
    public void atualizarCotacoes() {
        cotacaoBitcoin *= 1 + (Math.random() * 0.1 - 0.05); // Variação de ±5%
        cotacaoEthereum *= 1 + (Math.random() * 0.1 - 0.05);
        cotacaoRipple *= 1 + (Math.random() * 0.1 - 0.05);
    }

    // Getters para acessar as cotações atualizadas das criptomoedas
    public double getCotacaoBitcoin() {
        return cotacaoBitcoin;
    }

    public double getCotacaoEthereum() {
        return cotacaoEthereum;
    }

    public double getCotacaoRipple() {
        return cotacaoRipple;
    }
}