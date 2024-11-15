
package controller;

import view.ConsultaSaldoFrame;
import view.MenuFrame;

public class ConsultaSaldoFramecontroller {
    ConsultaSaldoFrame view;

    public ConsultaSaldoFramecontroller(ConsultaSaldoFrame view) {
        this.view = view;
    }

    public void voltarMenu() {
        view.dispose();
        MenuFrame menuFrame = new MenuFrame();
        menuFrame.setVisible(true);
    }

    // Adicione aqui métodos para buscar e exibir o saldo
}
