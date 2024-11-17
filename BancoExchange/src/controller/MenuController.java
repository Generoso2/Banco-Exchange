package controller;

import view.MenuFrame;
import view.ConsultaSaldoFrame;
import view.ConsultaExtratoFrame;
import view.DepositoFrame;
import view.CriptoFrame;
import view.AtualizarCotacaoFrame;
import view.AbrindoFrame;
import view.SaqueFrame;

public class MenuController {
    MenuFrame view;

    public MenuController(MenuFrame view) {
        this.view = view;
    }

    public void consultarSaldo() {
        view.dispose();
        ConsultaSaldoFrame consultaSaldo = new ConsultaSaldoFrame();
        consultaSaldo.setVisible(true);
    }

    public void consultarExtrato() {
        view.dispose();
        ConsultaExtratoFrame consultaExtrato = new ConsultaExtratoFrame();
        consultaExtrato.setVisible(true);
    }

    public void Depositar() {
        view.dispose();
        DepositoFrame Deposito = new DepositoFrame();
        Deposito.setVisible(true);
    }
    
    public void Saque() {
        view.dispose();
        SaqueFrame Saque = new SaqueFrame();
        Saque.setVisible(true);
    }

    public void gerenciarCripto() {
        view.dispose();
        CriptoFrame criptoFrame = new CriptoFrame();
        criptoFrame.setVisible(true);
    }

    public void atualizarCotacao() {
        view.dispose();
        AtualizarCotacaoFrame atualizarCotacao = new AtualizarCotacaoFrame();
        atualizarCotacao.setVisible(true);
    }

    public void sair() {
        view.dispose();
        AbrindoFrame abrindoFrame = new AbrindoFrame();
        abrindoFrame.setVisible(true);
    }
}