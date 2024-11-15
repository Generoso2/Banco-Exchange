/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UsuarioDAO {
    private Connection connection;

    public UsuarioDAO(Connection connection) {
        this.connection = connection;
    }

    // Método para buscar um usuário pelo CPF
    public Usuario buscarPorCpf(String cpf) {
        Usuario usuario = null;
        String sql = "SELECT * FROM usuarios WHERE cpf = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String senha = rs.getString("senha");
                usuario = new Usuario(id, cpf, senha);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Tratar exceções de forma adequada em produção
        }
        return usuario;
    }

    // Método para adicionar um novo usuário
    public boolean adicionarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (cpf, senha) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario.getCpf());
            stmt.setString(2, usuario.getSenha()); // Lembre-se de armazenar a senha de forma segura (hash)
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace(); // Tratar exceções de forma adequada em produção
            return false;
        }
    }

    // Método para verificar a senha de um usuário
    public boolean verificarSenha(Usuario usuario, String senha) {
        return usuario.getSenha().equals(senha); // Lembre-se de usar hash para comparação em produção
    }
}
