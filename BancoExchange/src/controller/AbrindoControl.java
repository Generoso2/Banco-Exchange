
package controller;

import view.AbrindoFrame;
import view.LoginFrame;
import view.CriarUsuario;

public class AbrindoControl {
    AbrindoFrame view;
    
    public AbrindoControl(AbrindoFrame view){
        this.view = view;
    }
    public void Login(){
        view.dispose();
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setVisible(true);
    }
    public void Cadastro(){
        view.dispose();
        CriarUsuario criarUsuario = new CriarUsuario();
        criarUsuario.setVisible(true);
    }
}
