/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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