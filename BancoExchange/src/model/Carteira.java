
package model;

import java.util.ArrayList;
import java.util.List;

public class Carteira {
    private double saldoReais;
    private double saldoBitcoin;
    private double saldoEthereum;
    private double saldoRipple;
    private List<String> extrato;

    public Carteira() {
        this.saldoReais = 0.0;
        this.saldoBitcoin = 0.0;
        this.saldoEthereum = 0.0;
        this.saldoRipple = 0.0;
        this.extrato = new ArrayList<>();
    }
    public void adicionarSaldoReais(double valor) {
        this.saldoReais += valor;
        adicionarAoExtrato("Depósito: + " + valor + " reais");
    }

    public void sacarSaldoReais(double valor) {
        if (valor <= this.saldoReais) {
            this.saldoReais -= valor;
            adicionarAoExtrato("Saque: - " + valor + " reais");
        } else {
            System.out.println("Saldo insuficiente para saque.");
        }
    }

    public void adicionarSaldoBitcoin(double quantidade) {
        this.saldoBitcoin += quantidade;
        adicionarAoExtrato("Compra de Bitcoin: + " + quantidade + " BTC");
    }

    public void adicionarSaldoEthereum(double quantidade) {
        this.saldoEthereum += quantidade;
        adicionarAoExtrato("Compra de Ethereum: + " + quantidade + " ETH");
    }

    public void adicionarSaldoRipple(double quantidade) {
        this.saldoRipple += quantidade;
        adicionarAoExtrato("Compra de Ripple: + " + quantidade + " XRP");
    }

    // Método para adicionar uma entrada ao extrato
    private void adicionarAoExtrato(String operacao) {
        extrato.add(operacao);
    }

    // Getters para os saldos
    public double getSaldoReais() {
        return saldoReais;
    }

    public double getSaldoBitcoin() {
        return saldoBitcoin;
    }

    public double getSaldoEthereum() {
        return saldoEthereum;
    }

    public double getSaldoRipple() {
        return saldoRipple;
    }

    public List<String> getExtrato() {
        return extrato;
    }
}

