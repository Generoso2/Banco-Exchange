
package controller;

import view.LoginFrame;
import view.MenuFrame;
import model.Investidor;
import model.SessaoUsuario;
import service.InvestidorService;

public class LoginControl {
    LoginFrame view;
    private final InvestidorService investidorService;


    public LoginControl(LoginFrame view) {
        this.view = view;
        this.investidorService = new InvestidorService();
    }

    public void fazerLogin() {
        String cpf = view.getCpf();
        String senha = view.getSenha();

        if (cpf.isEmpty() || senha.isEmpty()) {
            view.showMessage("CPF e senha são obrigatórios.");
            return;
        }

        // Autenticar usuário
        boolean autenticado = investidorService.autenticar(cpf, senha);
        if (!autenticado) {
            view.showMessage("CPF ou senha incorretos.");
            return;
        }

        // Buscar o investidor e salvar na sessão
        Investidor investidor = investidorService.buscarInvestidorPorCpf(cpf);
        SessaoUsuario.setInvestidorLogado(investidor);

        // Navegar para o menu
        view.showMessage("Login bem-sucedido!");
        navegarParaMenu();
    }

    public void navegarParaMenu() {
        view.dispose();
        MenuFrame menuFrame = new MenuFrame();
        menuFrame.setVisible(true);
    }
}