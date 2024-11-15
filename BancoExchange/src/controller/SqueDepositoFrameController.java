
package controller;

import view.SaqueDepositoFrame;
import view.MenuFrame;

public class SqueDepositoFrameController {
    SaqueDepositoFrame view;

    public SqueDepositoFrameController(SaqueDepositoFrame view) {
        this.view = view;
    }

    public void voltarMenu() {
        view.dispose();
        MenuFrame menuFrame = new MenuFrame();
        menuFrame.setVisible(true);
    }

    // Adicione aqui métodos para processar saques e depósitos
}