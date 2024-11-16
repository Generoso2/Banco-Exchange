package controller;

import service.CarteiraService;
import service.InvestidorService;
import view.ConsultaExtratoFrame;
import view.MenuFrame;
import java.util.List;
import model.SessaoUsuario;

public class ConsultaExtratoFrameController {
    private final ConsultaExtratoFrame view;
    private final InvestidorService investidorService;
    private final CarteiraService carteiraService;

    public ConsultaExtratoFrameController(ConsultaExtratoFrame view) {
        this.view = view;
        this.investidorService = new InvestidorService();
        this.carteiraService = new CarteiraService();
    }

    public void voltarMenu() {
        view.dispose();
        MenuFrame menuFrame = new MenuFrame();
        menuFrame.setVisible(true);
    }
    public void consultarExtrato() {
        String senha = new String(view.getPassword());
        String cpfLogado = SessaoUsuario.getInvestidorLogado().getCpf();

        // Validar senha
        boolean autentica = investidorService.autenticar(cpfLogado, senha);
        if (!autentica) {
            view.exibirMensagemErro("Senha incorreta. Tente novamente.");
            return;
        }

        // Buscar extrato
        List<String> extrato = carteiraService.consultarExtrato(cpfLogado);
        if (extrato.isEmpty()) {
            view.exibirMensagemErro("Nenhuma transação encontrada.");
        } else {
            view.exibirExtrato(String.join("\n", extrato));
        }
    }
}