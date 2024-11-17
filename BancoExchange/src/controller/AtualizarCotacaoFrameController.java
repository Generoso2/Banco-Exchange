package controller;

import service.CarteiraService;
import view.AtualizarCotacaoFrame;
import view.MenuFrame;

public class AtualizarCotacaoFrameController {
    AtualizarCotacaoFrame view;
    private final CarteiraService carteiraService;


    public AtualizarCotacaoFrameController(AtualizarCotacaoFrame view) {
        this.view = view;
        this.carteiraService = new CarteiraService();
    }

    public void voltarMenu() {
        view.dispose();
        MenuFrame menuFrame = new MenuFrame();
        menuFrame.setVisible(true);
    }

    // Adicione aqui métodos para atualizar cotações
    public void atualizarCotacoes() {
        try {
            carteiraService.atualizarCotacoes();
            double cotacaoBitcoin = carteiraService.buscarCotacao("Bitcoin");
            double cotacaoEthereum = carteiraService.buscarCotacao("Ethereum");
            double cotacaoRipple = carteiraService.buscarCotacao("Ripple");

            // Exibir as cotações atualizadas
            view.exibirCotacoes(
                String.format("Bitcoin: R$ %.2f\nEthereum: R$ %.2f\nRipple: R$ %.2f",
                              cotacaoBitcoin, cotacaoEthereum, cotacaoRipple)
            );
        } catch (Exception e) {
            view.exibirMensagemErro("Erro ao atualizar cotações: " + e.getMessage());
        }
    }
    
}