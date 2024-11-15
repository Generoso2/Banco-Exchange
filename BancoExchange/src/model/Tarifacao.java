package model;

public interface Tarifacao {
    double calcularTaxaCompra(double valor);
    double calcularTaxaVenda(double valor);
}

