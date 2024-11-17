
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Carteira;
import model.Bitcoin;
import model.Ethereum;
import model.Ripple;

public class CarteiraDAO {
    public void atualizarSaldoReais(double saldoAdicional, String cpf) {
        String sql = "UPDATE carteiras SET saldo_reais = saldo_reais + ? WHERE cpf_investidor = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, saldoAdicional); // Soma o saldo adicional
            stmt.setString(2, cpf);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Carteira buscarCarteiraPorCpf(String cpf) {
        String sql = "SELECT saldo_reais, saldo_bitcoin, saldo_ethereum, saldo_ripple FROM carteiras WHERE cpf_investidor = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Carteira carteira = new Carteira();
                carteira.setSaldoReais(rs.getDouble("saldo_reais"));
                carteira.getSaldosCripto().put(Bitcoin.class, rs.getDouble("saldo_bitcoin"));
                carteira.getSaldosCripto().put(Ethereum.class, rs.getDouble("saldo_ethereum"));
                carteira.getSaldosCripto().put(Ripple.class, rs.getDouble("saldo_ripple"));
                return carteira;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
    public List<String> buscarExtrato(String cpf) {
        List<String> extrato = new ArrayList<>();
        String sql = "SELECT transacao, data_hora FROM extratos WHERE cpf_investidor = ? ORDER BY data_hora DESC";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String transacao = rs.getString("transacao");
                String dataHora = rs.getTimestamp("data_hora").toString();
                extrato.add(dataHora + " - " + transacao);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return extrato;
    }
    
}
