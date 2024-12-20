package model;

public class Bitcoin extends Moedas implements Tarifacao {
    public Bitcoin(double cotacaoAtual) {
        super(cotacaoAtual);
    }

    @Override
    public double calcularTaxaCompra(double valor) {
        return valor * 0.02;
    }

    @Override
    public double calcularTaxaVenda(double valor) {
        return valor * 0.03;
    }
}
