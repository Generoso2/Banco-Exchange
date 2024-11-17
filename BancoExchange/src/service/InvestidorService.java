package service;

import DAO.InvestidorDAO;
import model.Investidor;

public class InvestidorService {
    private final InvestidorDAO investidorDAO;

    public InvestidorService() {
        this.investidorDAO = new InvestidorDAO();
    }

    public boolean autenticar(String cpf, String senha) {
        return investidorDAO.autenticar(cpf, senha);
    }

    public Investidor buscarInvestidorPorCpf(String cpf) {
        return investidorDAO.buscarPorCpf(cpf);
    }
}