package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DAO.DatabaseConnection;
import view.LoginFrame;
import view.MenuFrame;

public class LoginControl {
    LoginFrame view;

    public LoginControl(LoginFrame view) {
        this.view = view;
    }

    public void fazerLogin() {
        String cpf = view.getCpf();
        String senha = view.getSenha();

        if (cpf.isEmpty() || senha.isEmpty()) {
            view.showMessage("CPF e senha são obrigatórios.");
            return;
        }

        try (Connection connection = DatabaseConnection.connect()) {
            String sql = "SELECT * FROM investidores WHERE cpf = ? AND senha = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cpf);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                view.showMessage("Login bem-sucedido!");
                navegarParaMenu();
            } else {
                view.showMessage("CPF ou senha incorretos.");
            }

        } catch (SQLException e) {
            view.showMessage("Erro ao realizar login: " + e.getMessage());
        }
    }

    public void navegarParaMenu() {
        view.dispose();
        MenuFrame menuFrame = new MenuFrame();
        menuFrame.setVisible(true);
    }
}