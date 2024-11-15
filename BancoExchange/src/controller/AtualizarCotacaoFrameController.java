package controller;

import view.AtualizarCotacaoFrame;
import view.MenuFrame;

public class AtualizarCotacaoFrameController {
    AtualizarCotacaoFrame view;

    public AtualizarCotacaoFrameController(AtualizarCotacaoFrame view) {
        this.view = view;
    }

    public void voltarMenu() {
        view.dispose();
        MenuFrame menuFrame = new MenuFrame();
        menuFrame.setVisible(true);
    }

    // Adicione aqui métodos para atualizar cotações
}