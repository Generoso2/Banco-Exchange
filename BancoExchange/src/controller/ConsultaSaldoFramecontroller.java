package controller;

import model.SessaoUsuario;
import service.CarteiraService;
import service.InvestidorService;
import view.ConsultaSaldoFrame;
import view.MenuFrame;
import model.Carteira;
import model.Bitcoin;
import model.Ethereum;
import model.Ripple;

public class ConsultaSaldoFramecontroller {
    private final ConsultaSaldoFrame view;
    private final InvestidorService investidorService;
    private final CarteiraService carteiraService;

    public ConsultaSaldoFramecontroller(ConsultaSaldoFrame view) {
        this.view = view;
        this.investidorService = new InvestidorService();
        this.carteiraService = new CarteiraService();
    }

    public void voltarMenu() {
        view.dispose();
        MenuFrame menuFrame = new MenuFrame();
        menuFrame.setVisible(true);
    }

    public void consultarSaldo() {
        String senha = new String(view.getPassword());
        String cpfLogado = SessaoUsuario.getInvestidorLogado().getCpf();

        // Validar senha
        boolean autentica = investidorService.autenticar(cpfLogado, senha);
        if (!autentica) {
            view.exibirMensagemErro("Senha incorreta. Tente novamente.");
            return;
        }

        // Sincronizar carteira com o banco
        Carteira carteiraAtualizada = carteiraService.sincronizarCarteira(cpfLogado);

        // Exibir todos os saldos
        String saldoFormatado = String.format(
            "Saldo atual:\nReais: R$ %.2f\nBitcoin: %.8f\nEthereum: %.8f\nRipple: %.8f",
            carteiraAtualizada.getSaldoReais(),
            carteiraAtualizada.getSaldosCripto().get(Bitcoin.class),
            carteiraAtualizada.getSaldosCripto().get(Ethereum.class),
            carteiraAtualizada.getSaldosCripto().get(Ripple.class)
        );

        view.exibirSaldo(saldoFormatado);
    }
}