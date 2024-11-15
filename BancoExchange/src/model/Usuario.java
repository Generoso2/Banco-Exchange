
package model;

public class Usuario {
    private int id;
    private String cpf;
    private String senha; 
    private Carteira carteira;

    // Construtor
    public Usuario(int id, String cpf, String senha) {
        this.id = id;
        this.cpf = cpf;
        this.senha = senha;
        this.carteira = new Carteira();
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public String getCpf() {
        return cpf;
    }

    public String getSenha() {
        return senha;
    }

    public Carteira getCarteira() {
        return carteira;
    }
}
