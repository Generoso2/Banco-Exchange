
package model;

public abstract class Moedas {
    protected double cotacaoAtual;

    public Moedas(double cotacaoAtual) {
        this.cotacaoAtual = cotacaoAtual;
    }

    public double getCotacaoAtual() {
        return cotacaoAtual;
    }

    public void setCotacaoAtual(double cotacaoAtual) {
        this.cotacaoAtual = cotacaoAtual;
    }
}