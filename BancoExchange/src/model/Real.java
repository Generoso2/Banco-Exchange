
package model;

public class Real extends Moedas implements Tarifacao {
    public Real(double cotacaoAtual) {
        super(cotacaoAtual);
    }

    @Override
    public double calcularTaxaCompra(double valor) {
        return valor;
    }

    @Override
    public double calcularTaxaVenda(double valor) {
        return valor;
    }
}