package controller;

import view.CriptoFrame;
import view.MenuFrame;

public class CriptoFrameController {
    CriptoFrame view;

    public CriptoFrameController(CriptoFrame view) {
        this.view = view;
    }

    public void voltarMenu() {
        view.dispose();
        MenuFrame menuFrame = new MenuFrame();
        menuFrame.setVisible(true);
    }

    // Adicione aqui métodos para comprar e vender criptomoedas
}