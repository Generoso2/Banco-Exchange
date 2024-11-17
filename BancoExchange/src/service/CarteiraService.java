package service;

import DAO.CarteiraDAO;
import DAO.InvestidorDAO;
import java.util.List;
import model.Carteira;
import model.Investidor;
import model.Moedas;
import model.Tarifacao;
import model.SessaoUsuario;
import java.util.Random;

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

        // Sincronizar o saldo com o banco antes de fazer o depósito
        Investidor investidor = investidorDAO.buscarPorCpf(cpf);
        if (investidor == null) {
            throw new IllegalArgumentException("Investidor não encontrado.");
        }
        SessaoUsuario.getInvestidorLogado().setCarteira(investidor.getCarteira());

        Carteira carteira = SessaoUsuario.getInvestidorLogado().getCarteira();

        // Calcular o novo saldo
        double novoSaldo = carteira.getSaldoReais() + valor;

        // Atualizar no banco
        carteiraDAO.atualizarSaldoReais(valor, cpf);

        // Atualizar na sessão
        carteira.setSaldoReais(novoSaldo);

        // Registrar a transação no banco
        carteiraDAO.registrarTransacao("Depósito de R$ " + valor, cpf);

        return novoSaldo;
    }

    public boolean sacar(String cpf, double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor do saque deve ser maior que zero.");
        }

        // Sincronizar o saldo do investidor logado com o banco
        Investidor investidor = investidorDAO.buscarPorCpf(cpf);
        if (investidor == null) {
            throw new IllegalArgumentException("Investidor não encontrado.");
        }
        SessaoUsuario.getInvestidorLogado().setCarteira(investidor.getCarteira());

        Carteira carteira = SessaoUsuario.getInvestidorLogado().getCarteira();

        // Validar saldo suficiente
        if (carteira.getSaldoReais() < valor) {
            return false; // Saldo insuficiente
        }

        double novoSaldo = carteira.getSaldoReais() - valor;
        carteira.setSaldoReais(novoSaldo);

        // Atualizar saldo no banco de dados
        carteiraDAO.atualizarSaldoReais(-valor, cpf);

        // Registrar transação no extrato
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
    //Cripto
    public boolean comprarCriptomoeda(String cpf, Moedas moeda, double quantidade) {
        // Sincronizar carteira com o banco
        Investidor investidor = investidorDAO.buscarPorCpf(cpf);
        if (investidor == null) {
            throw new IllegalArgumentException("Investidor não encontrado.");
        }
        SessaoUsuario.getInvestidorLogado().setCarteira(investidor.getCarteira());

        Carteira carteira = SessaoUsuario.getInvestidorLogado().getCarteira();

        // Calcular o custo total da compra
        double cotacaoAtual = carteiraDAO.buscarCotacao(moeda.getClass().getSimpleName());
        moeda.setCotacaoAtual(cotacaoAtual);

        double valorCompra = quantidade * cotacaoAtual;
        double taxa = ((Tarifacao) moeda).calcularTaxaCompra(valorCompra);
        double totalCompra = valorCompra + taxa;

        // Validar saldo suficiente
        if (carteira.getSaldoReais() < totalCompra) {
            return false; // Saldo insuficiente
        }

        // Atualizar os saldos
        carteira.setSaldoReais(carteira.getSaldoReais() - totalCompra);
        carteira.getSaldosCripto().put(
            moeda.getClass(),
            carteira.getSaldosCripto().get(moeda.getClass()) + quantidade
        );

        // Atualizar dados no banco
        carteiraDAO.atualizarSaldoReais(-totalCompra, cpf);
        carteiraDAO.atualizarSaldoCripto(quantidade, moeda.getClass(), cpf);

        // Registrar a transação no extrato
        carteiraDAO.registrarTransacao(
            "Compra de " + quantidade + " " + moeda.getClass().getSimpleName() +
            " - Total: R$ " + totalCompra + " (Taxa: R$ " + taxa + ")", cpf
        );

        return true;
    }

    public boolean venderCriptomoeda(String cpf, Moedas moeda, double quantidade) {
        // Sincronizar carteira com o banco
        Investidor investidor = investidorDAO.buscarPorCpf(cpf);
        if (investidor == null) {
            throw new IllegalArgumentException("Investidor não encontrado.");
        }
        SessaoUsuario.getInvestidorLogado().setCarteira(investidor.getCarteira());

        Carteira carteira = SessaoUsuario.getInvestidorLogado().getCarteira();

        // Verificar quantidade disponível
        double quantidadeDisponivel = carteira.getSaldosCripto().get(moeda.getClass());
        if (quantidadeDisponivel < quantidade) {
            return false; // Quantidade insuficiente
        }

        // Calcular o valor da venda
        double cotacaoAtual = carteiraDAO.buscarCotacao(moeda.getClass().getSimpleName());
        moeda.setCotacaoAtual(cotacaoAtual);

        double valorVenda = quantidade * cotacaoAtual;
        double taxa = ((Tarifacao) moeda).calcularTaxaVenda(valorVenda);
        double totalVenda = valorVenda - taxa;

        // Atualizar os saldos
        carteira.setSaldoReais(carteira.getSaldoReais() + totalVenda);
        carteira.getSaldosCripto().put(moeda.getClass(), quantidadeDisponivel - quantidade);

        // Atualizar dados no banco
        carteiraDAO.atualizarSaldoReais(totalVenda, cpf);
        carteiraDAO.atualizarSaldoCripto(-quantidade, moeda.getClass(), cpf);

        // Registrar a transação no extrato
        carteiraDAO.registrarTransacao(
            "Venda de " + quantidade + " " + moeda.getClass().getSimpleName() +
            " - Total: R$ " + totalVenda + " (Taxa: R$ " + taxa + ")", cpf
        );

        return true;
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
    public void atualizarCotacoes() {
        // Buscar as cotações atuais do banco
        double cotacaoBitcoin = carteiraDAO.buscarCotacao("Bitcoin");
        double cotacaoEthereum = carteiraDAO.buscarCotacao("Ethereum");
        double cotacaoRipple = carteiraDAO.buscarCotacao("Ripple");

        // Atualizar cotações com variação aleatória de -5% a +5%
        cotacaoBitcoin = calcularVariacao(cotacaoBitcoin);
        cotacaoEthereum = calcularVariacao(cotacaoEthereum);
        cotacaoRipple = calcularVariacao(cotacaoRipple);

        // Salvar as novas cotações no banco
        carteiraDAO.atualizarCotacao("Bitcoin", cotacaoBitcoin);
        carteiraDAO.atualizarCotacao("Ethereum", cotacaoEthereum);
        carteiraDAO.atualizarCotacao("Ripple", cotacaoRipple);
    }
    public double buscarCotacao(String criptomoeda) {
        return carteiraDAO.buscarCotacao(criptomoeda);
    }
    

    private double calcularVariacao(double cotacaoAtual) {
        Random random = new Random();
        double variacao = (random.nextDouble() * 10) - 5; // Gera variação entre -5% e +5%
        return cotacaoAtual + (cotacaoAtual * variacao / 100);
    }
}