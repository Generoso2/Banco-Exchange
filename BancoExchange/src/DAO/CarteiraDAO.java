
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CarteiraDAO {
    public void atualizarSaldoReais(double saldo, String cpf) {
        String sql = "UPDATE carteiras SET saldo_reais = ? WHERE cpf_investidor = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, saldo);
            stmt.setString(2, cpf);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void registrarTransacao(String transacao, String cpf) {
        String sql = "INSERT INTO extratos (cpf_investidor, transacao) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            stmt.setString(2, transacao);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
