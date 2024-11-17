
package controller;

import view.SaqueFrame;
import view.MenuFrame;
import service.CarteiraService;
import service.InvestidorService;
import model.SessaoUsuario;

public class SaqueFrameController {
    private final SaqueFrame view;
    private final InvestidorService investidorService;
    private final CarteiraService carteiraService;
    
public SaqueFrameController(SaqueFrame view) {
        this.view = view;
        this.investidorService = new InvestidorService();
        this.carteiraService = new CarteiraService();
    }

    public void voltarMenu() {
        view.dispose();
        MenuFrame menuFrame = new MenuFrame();
        menuFrame.setVisible(true);
    }
    
    public void realizarSaque() {
        String senha = new String(view.getPassword());
        String valorTexto = view.getValorSaque();
        String cpfLogado = SessaoUsuario.getInvestidorLogado().getCpf();

        // Validar senha
        boolean autentica = investidorService.autenticar(cpfLogado, senha);
        if (!autentica) {
            view.exibirMensagemErro("Senha incorreta. Tente novamente.");
            return;
        }

        // Validar valor do saque
        double valorSaque;
        try {
            valorSaque = Double.parseDouble(valorTexto);
            if (valorSaque <= 0) {
                view.exibirMensagemErro("O valor do saque deve ser maior que zero.");
                return;
            }
        } catch (NumberFormatException e) {
            view.exibirMensagemErro("Por favor, insira um valor numérico válido.");
            return;
        }

        try {
            // Realizar o saque via CarteiraService
            boolean sucesso = carteiraService.sacar(cpfLogado, valorSaque);

            if (!sucesso) {
                view.exibirMensagemErro("Saldo insuficiente para realizar o saque.");
            } else {
                // Atualizar saldo exibido na interface
                double saldoAtualizado = SessaoUsuario.getInvestidorLogado().getCarteira().getSaldoReais();
                view.exibirMensagemSucesso("Saque realizado com sucesso! Saldo atualizado: R$ " + saldoAtualizado);
                view.atualizarSaldo("Saldo atual: R$ " + saldoAtualizado);
            }
        } catch (Exception e) {
            view.exibirMensagemErro("Erro ao realizar o saque: " + e.getMessage());
        }
    }


}
    
