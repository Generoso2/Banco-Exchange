package controller;

import view.ConsultaExtratoFrame;
import view.MenuFrame;

public class ConsultaExtratoFrameController {
    ConsultaExtratoFrame view;

    public ConsultaExtratoFrameController(ConsultaExtratoFrame view) {
        this.view = view;
    }

    public void voltarMenu() {
        view.dispose();
        MenuFrame menuFrame = new MenuFrame();
        menuFrame.setVisible(true);
    }

    // Adicione aqui métodos para buscar e exibir o extrato
}