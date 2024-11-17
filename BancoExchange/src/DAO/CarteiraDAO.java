
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
import model.Moedas;
import model.Ripple;

public class CarteiraDAO {
    public void atualizarSaldoReais(double saldoAdicional, String cpf) {
        String sql = "UPDATE carteiras SET saldo_reais = saldo_reais + ? WHERE cpf_investidor = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, saldoAdicional); // Soma apenas o valor adicional
            stmt.setString(2, cpf);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void atualizarSaldoCripto(double quantidade, Class<? extends Moedas> moeda, String cpf) {
        String coluna = ""; // Determina a coluna no banco com base na classe da moeda
        if (moeda.equals(Bitcoin.class)) {
            coluna = "saldo_bitcoin";
        } else if (moeda.equals(Ethereum.class)) {
            coluna = "saldo_ethereum";
        } else if (moeda.equals(Ripple.class)) {
            coluna = "saldo_ripple";
        }

        String sql = "UPDATE carteiras SET " + coluna + " = " + coluna + " + ? WHERE cpf_investidor = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, quantidade);
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
    
    
    //Cotação
    public double buscarCotacao(String criptomoeda) {
        String coluna = determinarColunaCotacao(criptomoeda);
        String sql = "SELECT " + coluna + " FROM cotacoes";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble(coluna);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0.0; // Retorna 0.0 se não encontrar a cotação
    }

    public void atualizarCotacao(String criptomoeda, double novaCotacao) {
        String coluna = determinarColunaCotacao(criptomoeda);
        String sql = "UPDATE cotacoes SET " + coluna + " = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, novaCotacao);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String determinarColunaCotacao(String criptomoeda) {
        switch (criptomoeda) {
            case "Bitcoin": return "cotacao_bitcoin";
            case "Ethereum": return "cotacao_ethereum";
            case "Ripple": return "cotacao_ripple";
            default: throw new IllegalArgumentException("Criptomoeda desconhecida: " + criptomoeda);
        }
    }
    
}
