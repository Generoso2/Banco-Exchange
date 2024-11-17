
package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Carteira {
    private double saldoReais;
    private Map<Class<? extends Moedas>, Double> saldosCripto = new HashMap<>();
    private List<String> extrato = new ArrayList<>();

    public Carteira() {
        saldosCripto.put(Bitcoin.class, 0.0);
        saldosCripto.put(Ethereum.class, 0.0);
        saldosCripto.put(Ripple.class, 0.0);
    }

    
    
    public Map<Class<? extends Moedas>, Double> getSaldosCripto() {
        return saldosCripto;
    }
    


    // Método de compra de criptomoedas
    public boolean comprarCripto(Moedas moeda, double quantidade, Tarifacao tarifacao) {
        double valorCompra = quantidade * moeda.getCotacaoAtual();
        double taxa = tarifacao.calcularTaxaCompra(valorCompra);
        double totalCompra = valorCompra + taxa;

        if (saldoReais >= totalCompra) {
            saldoReais -= totalCompra;
            saldosCripto.put(moeda.getClass(), saldosCripto.get(moeda.getClass()) + quantidade);
            extrato.add("Compra de " + quantidade + " " + moeda.getClass().getSimpleName() + " - Valor: " + valorCompra + " - Taxa: " + taxa);
            return true;
        } else {
            return false; // Saldo insuficiente
        }
    }

    // Método de venda de criptomoedas
    public boolean venderCripto(Moedas moeda, double quantidade, Tarifacao tarifacao) {
        if (saldosCripto.get(moeda.getClass()) >= quantidade) {
            double valorVenda = quantidade * moeda.getCotacaoAtual();
            double taxa = tarifacao.calcularTaxaVenda(valorVenda);
            double totalVenda = valorVenda - taxa;

            saldoReais += totalVenda;
            saldosCripto.put(moeda.getClass(), saldosCripto.get(moeda.getClass()) - quantidade);
            extrato.add("Venda de " + quantidade + " " + moeda.getClass().getSimpleName() + " - Valor: " + valorVenda + " - Taxa: " + taxa);
            return true;
        } else {
            return false; // Quantidade insuficiente para venda
        }
    }

    // Método para consultar o saldo
    public String consultarSaldo() {
        return "Saldo em Reais: " + saldoReais + "\n" +
               "Saldo Bitcoin: " + saldosCripto.get(Bitcoin.class) + "\n" +
               "Saldo Ethereum: " + saldosCripto.get(Ethereum.class) + "\n" +
               "Saldo Ripple: " + saldosCripto.get(Ripple.class);
    }

    // Método para consultar o extrato
    public List<String> consultarExtrato() {
        return new ArrayList<>(extrato);
    }

    public double getSaldoReais() {
        return saldoReais;
    }

    public void setSaldoReais(double saldoReais) {
        this.saldoReais = saldoReais;
    }
    
}
