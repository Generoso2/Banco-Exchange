
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
            String sql = "INSERT INTO investidores (nome, senha, cpf) VALUES (?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, senha);
            stmt.setString(3, cpf);

            stmt.executeUpdate();
            view.showMessage("Usuário criado com sucesso!");
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
