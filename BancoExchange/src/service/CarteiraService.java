package service;

import DAO.CarteiraDAO;
import DAO.InvestidorDAO;
import java.util.List;
import model.Carteira;
import model.Investidor;
import model.Moedas;
import model.Tarifacao;
import model.SessaoUsuario;

public class CarteiraService {
    private final CarteiraDAO carteiraDAO;
    private final InvestidorDAO investidorDAO;

    public CarteiraService() {
        this.carteiraDAO = new CarteiraDAO();
        this.investidorDAO = new InvestidorDAO();
    }

    public Carteira sincronizarCarteira(String cpf) {
        // Obter dados da carteira do banco
        Carteira carteira = carteiraDAO.buscarCarteiraPorCpf(cpf);
        if (carteira == null) {
            throw new IllegalArgumentException("Carteira não encontrada para o CPF: " + cpf);
        }

        // Atualizar a carteira no objeto da sessão
        SessaoUsuario.getInvestidorLogado().setCarteira(carteira);
        return carteira;
    }
    
    public double depositar(String cpf, double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor do depósito deve ser maior que zero.");
        }

        Investidor investidor = investidorDAO.buscarPorCpf(cpf);
        if (investidor == null) {
            throw new IllegalArgumentException("Investidor não encontrado.");
        }

        Carteira carteira = investidor.getCarteira();
        double novoSaldo = carteira.getSaldoReais() + valor;
        carteira.setSaldoReais(novoSaldo);

        // Atualizar saldo no banco de dados
        carteiraDAO.atualizarSaldoReais(novoSaldo, cpf);

        // Registrar transação no extrato
        carteiraDAO.registrarTransacao("Depósito de R$ " + valor, cpf);

        // Atualizar o saldo do investidor na sessão
        SessaoUsuario.getInvestidorLogado().getCarteira().setSaldoReais(novoSaldo);

        return novoSaldo;
    }

    public boolean sacar(String cpf, double valor) {
        if (valor <= 0) {
            return false; // Saque inválido
        }

        Investidor investidor = investidorDAO.buscarPorCpf(cpf);
        if (investidor == null) {
            throw new IllegalArgumentException("Investidor não encontrado.");
        }

        Carteira carteira = investidor.getCarteira();
        if (carteira.getSaldoReais() < valor) {
            return false; // Saldo insuficiente
        }

        double novoSaldo = carteira.getSaldoReais() - valor;
        carteira.setSaldoReais(novoSaldo);

        // Atualizar saldo no banco de dados
        carteiraDAO.atualizarSaldoReais(novoSaldo, cpf);
        carteiraDAO.registrarTransacao("Saque de R$ " + valor, cpf);

        return true;
    }

    public String consultarSaldo(String cpf) {
        Investidor investidor = investidorDAO.buscarPorCpf(cpf);
        if (investidor == null) {
            throw new IllegalArgumentException("Investidor não encontrado.");
        }

        return investidor.getCarteira().consultarSaldo();
    }

    public boolean comprarCriptomoeda(String cpf, Moedas moeda, double quantidade) {
        Investidor investidor = investidorDAO.buscarPorCpf(cpf);
        if (investidor == null) {
            throw new IllegalArgumentException("Investidor não encontrado.");
        }

        Carteira carteira = investidor.getCarteira();

        // Confirma que a moeda implementa Tarifacao
        if (!(moeda instanceof Tarifacao)) {
            throw new IllegalArgumentException("A moeda não suporta tarifação.");
        }

        boolean sucesso = carteira.comprarCripto(moeda, quantidade, (Tarifacao) moeda);
        if (sucesso) {
            carteiraDAO.atualizarSaldoReais(carteira.getSaldoReais(), cpf);
            carteiraDAO.registrarTransacao("Compra de " + quantidade + " " + moeda.getClass().getSimpleName(), cpf);
        }

        return sucesso;
    }

    public boolean venderCriptomoeda(String cpf, Moedas moeda, double quantidade) {
        Investidor investidor = investidorDAO.buscarPorCpf(cpf);
        if (investidor == null) {
            throw new IllegalArgumentException("Investidor não encontrado.");
        }

        Carteira carteira = investidor.getCarteira();
        if (!(moeda instanceof Tarifacao)) {
            throw new IllegalArgumentException("A moeda não suporta tarifação.");
        }
        boolean sucesso = carteira.venderCripto(moeda, quantidade, (Tarifacao)moeda);
        if (sucesso) {
            carteiraDAO.atualizarSaldoReais(carteira.getSaldoReais(), cpf);
            carteiraDAO.registrarTransacao("Venda de " + quantidade + " " + moeda.getClass().getSimpleName(), cpf);
        }

        return sucesso;
    }
    public List<String> consultarExtrato(String cpf) {
        return carteiraDAO.buscarExtrato(cpf); // O DAO lida com a persistência
    }
    public Carteira consultarCarteira(String cpf) {
        Investidor investidor = investidorDAO.buscarPorCpf(cpf);
        if (investidor == null) {
            throw new IllegalArgumentException("Investidor não encontrado.");
        }
        return investidor.getCarteira();
    }
}