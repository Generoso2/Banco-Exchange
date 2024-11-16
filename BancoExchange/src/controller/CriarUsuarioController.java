package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import DAO.DatabaseConnection;
import view.CriarUsuario;
import view.MenuFrame;

public class CriarUsuarioController {
    CriarUsuario view;

    public CriarUsuarioController(CriarUsuario view) {
        this.view = view;
    }

    public void criarUsuario() {
        String nome = view.getNome();
        String senha = view.getSenha();
        String cpf = view.getCpf();

        if (nome.isEmpty() || senha.isEmpty() || cpf.isEmpty()) {
            view.showMessage("Todos os campos são obrigatórios.");
            return;
        }

        if (!cpf.matches("\\d{11}")) {
            view.showMessage("O CPF deve conter 11 dígitos numéricos.");
            return;
        }

        if (!senha.matches("\\d{6}")) {
            view.showMessage("A senha deve conter 6 dígitos.");
            return;
        }

        try (Connection connection = DatabaseConnection.connect()) {
            // Insere o investidor na tabela
            String sqlInvestidor = "INSERT INTO investidores (nome, senha, cpf) VALUES (?, ?, ?)";
            PreparedStatement stmtInvestidor = connection.prepareStatement(sqlInvestidor);
            stmtInvestidor.setString(1, nome);
            stmtInvestidor.setString(2, senha);
            stmtInvestidor.setString(3, cpf);
            stmtInvestidor.executeUpdate();

            // Insere a carteira associada ao investidor
            String sqlCarteira = "INSERT INTO carteiras (cpf_investidor) VALUES (?)";
            PreparedStatement stmtCarteira = connection.prepareStatement(sqlCarteira);
            stmtCarteira.setString(1, cpf);
            stmtCarteira.executeUpdate();

            view.showMessage("Usuário e carteira criados com sucesso!");
            navegarParaMenu();

        } catch (SQLException e) {
            view.showMessage("Erro ao criar usuário: " + e.getMessage());
        }
    }

    public void navegarParaMenu() {
        view.dispose();
        MenuFrame menuFrame = new MenuFrame();
        menuFrame.setVisible(true);
    }
}