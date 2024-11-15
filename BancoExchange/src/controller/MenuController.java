package controller;

import view.MenuFrame;
import view.ConsultaSaldoFrame;
import view.ConsultaExtratoFrame;
import view.SaqueDepositoFrame;
import view.CriptoFrame;
import view.AtualizarCotacaoFrame;
import view.AbrindoFrame;

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

    public void sacarDepositar() {
        view.dispose();
        SaqueDepositoFrame saqueDeposito = new SaqueDepositoFrame();
        saqueDeposito.setVisible(true);
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