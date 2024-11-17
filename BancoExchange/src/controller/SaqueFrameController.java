
package controller;

import view.SaqueFrame;
import view.MenuFrame;

public class SaqueFrameController {
    SaqueFrame view;

    public SaqueFrameController(SaqueFrame view) {
        this.view = view;
    }

    public void voltarMenu() {
        view.dispose();
        MenuFrame menuFrame = new MenuFrame();
        menuFrame.setVisible(true);
    }

    // Adicione aqui métodos para processar saques e depósitos
}
    
