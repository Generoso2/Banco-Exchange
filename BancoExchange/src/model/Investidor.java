
package model;

import model.Pessoa;

public class Investidor extends Pessoa{
    
    public Investidor(String nome, String cpf, String senha) {
        super(nome, cpf);
        this.senha = senha;
        this.carteira = new Carteira();
    }
    private String senha;
    private Carteira carteira;

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Carteira getCarteira() {
        return carteira;
    }

    public void setCarteira(Carteira carteira) {
        this.carteira = carteira;
    }

    
}

