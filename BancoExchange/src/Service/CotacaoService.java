/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.CotacaoDAO;
import model.Cotacao;

public class CotacaoService {
    private CotacaoDAO cotacaoDAO;

    public CotacaoService(CotacaoDAO cotacaoDAO) {
        this.cotacaoDAO = cotacaoDAO;
    }

    // Método para obter a cotação atual
    public Cotacao obterCotacaoAtual() {
        return cotacaoDAO.buscarCotacaoAtual();
    }

    // Método para registrar uma nova cotação
    public boolean registrarCotacao(Cotacao cotacao) {
        return cotacaoDAO.adicionarCotacao(cotacao);
    }
}
