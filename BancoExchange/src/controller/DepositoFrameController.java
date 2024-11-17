
package controller;


import view.DepositoFrame;
import view.MenuFrame;
import service.CarteiraService;
import service.InvestidorService;
import model.SessaoUsuario;

public class DepositoFrameController {

    private final DepositoFrame view;
    private final InvestidorService investidorService;
    private final CarteiraService carteiraService;

    public DepositoFrameController(DepositoFrame view) {
        this.view = view;
        this.investidorService = new InvestidorService();
        this.carteiraService = new CarteiraService();
        SessaoUsuario.getInvestidorLogado().setCarteira(
            this.carteiraService.consultarCarteira(SessaoUsuario.getInvestidorLogado().getCpf())
        );
    }

    public void voltarMenu() {
        view.dispose();
        MenuFrame menuFrame = new MenuFrame();
        menuFrame.setVisible(true);
    }

    public void realizarDeposito() {
        String senha = new String(view.getPassword());
        String valorTexto = view.getValorDeposito();
        String cpfLogado = SessaoUsuario.getInvestidorLogado().getCpf();

        // Validar senha
        boolean autentica = investidorService.autenticar(cpfLogado, senha);
        if (!autentica) {
            view.exibirMensagemErro("Senha incorreta. Tente novamente.");
            return;
        }

        // Validar valor do depósito
        double valorDeposito;
        try {
            valorDeposito = Double.parseDouble(valorTexto);
            if (valorDeposito <= 0) {
                view.exibirMensagemErro("O valor do depósito deve ser maior que zero.");
                return;
            }
        } catch (NumberFormatException e) {
            view.exibirMensagemErro("Por favor, insira um valor numérico válido.");
            return;
        }

        try {
            // Realizar o depósito e sincronizar o saldo atualizado
            double saldoAtualizado = carteiraService.depositar(cpfLogado, valorDeposito);

            // Atualizar o saldo exibido na interface
            SessaoUsuario.getInvestidorLogado().getCarteira().setSaldoReais(saldoAtualizado);
            view.exibirMensagemSucesso("Depósito realizado com sucesso!");
            view.atualizarSaldo("Saldo atual: R$ " + saldoAtualizado);
        } catch (Exception e) {
            view.exibirMensagemErro("Erro ao realizar o depósito: " + e.getMessage());
        }
    }


    
}