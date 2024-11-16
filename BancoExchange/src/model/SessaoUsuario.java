package model;

public class SessaoUsuario {
    private static Investidor investidorLogado;

    public static Investidor getInvestidorLogado() {
        return investidorLogado;
    }

    public static void setInvestidorLogado(Investidor investidor) {
        investidorLogado = investidor;
    }
}