package controller;

import model.Moedas;
import model.Bitcoin;
import model.Ethereum;
import model.Ripple;
import service.CarteiraService;
import service.InvestidorService;
import view.CriptoFrame;
import view.MenuFrame;
import model.SessaoUsuario;

public class CriptoFrameController {
    
    private final CriptoFrame view;
    private final InvestidorService investidorService;
    private final CarteiraService carteiraService;
    
    public CriptoFrameController(CriptoFrame view) {
        this.view = view;
        this.investidorService = new InvestidorService();
        this.carteiraService = new CarteiraService();
    }

    public void voltarMenu() {
        view.dispose();
        MenuFrame menuFrame = new MenuFrame();
        menuFrame.setVisible(true);
    }

    // Adicione aqui métodos para comprar e vender criptomoedas
    public void realizarCompra() {
        String senha = new String(view.getPassword());
        String quantidadeTexto = view.getQuantidade();
        String cpfLogado = SessaoUsuario.getInvestidorLogado().getCpf();

        // Validar senha
        boolean autentica = investidorService.autenticar(cpfLogado, senha);
        if (!autentica) {
            view.exibirMensagemErro("Senha incorreta. Tente novamente.");
            return;
        }

        // Validar a criptomoeda selecionada
        Moedas moeda;
        if (view.isBitcoinSelecionado()) {
            moeda = new Bitcoin(0); // Cotação será usada do banco
        } else if (view.isEthereumSelecionado()) {
            moeda = new Ethereum(0);
        } else if (view.isRippleSelecionado()) {
            moeda = new Ripple(0);
        } else {
            view.exibirMensagemErro("Selecione uma criptomoeda.");
            return;
        }

        // Validar a quantidade
        double quantidade;
        try {
            quantidade = Double.parseDouble(quantidadeTexto);
            if (quantidade <= 0) {
                view.exibirMensagemErro("A quantidade deve ser maior que zero.");
                return;
            }
        } catch (NumberFormatException e) {
            view.exibirMensagemErro("Por favor, insira um valor numérico válido.");
            return;
        }

        try {
            // Realizar a compra
            boolean sucesso = carteiraService.comprarCriptomoeda(cpfLogado, moeda, quantidade);

            if (!sucesso) {
                view.exibirMensagemErro("Saldo insuficiente para realizar a compra.");
            } else {
                view.exibirMensagemSucesso("Compra realizada com sucesso! Consulte seu saldo no menu de consulta.");
            }
        } catch (Exception e) {
            view.exibirMensagemErro("Erro ao realizar a compra: " + e.getMessage());
        }
    }
}