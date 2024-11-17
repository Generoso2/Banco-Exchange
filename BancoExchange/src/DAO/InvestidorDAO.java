
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Investidor;
import model.Carteira;
import model.Bitcoin;
import model.Ethereum;
import model.Ripple;

public class InvestidorDAO {
    public boolean autenticar(String cpf, String senha) {
        String sql = "SELECT * FROM investidores WHERE cpf = ? AND senha = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public Investidor buscarPorCpf(String cpf) {
        String sqlInvestidor = "SELECT * FROM investidores WHERE cpf = ?";
        String sqlCarteira = "SELECT saldo_reais, saldo_bitcoin, saldo_ethereum, saldo_ripple FROM carteiras WHERE cpf_investidor = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmtInvestidor = conn.prepareStatement(sqlInvestidor);
             PreparedStatement stmtCarteira = conn.prepareStatement(sqlCarteira)) {

            // Buscar dados do investidor
            stmtInvestidor.setString(1, cpf);
            ResultSet rsInvestidor = stmtInvestidor.executeQuery();

            if (rsInvestidor.next()) {
                String nome = rsInvestidor.getString("nome");
                String senha = rsInvestidor.getString("senha");

                Investidor investidor = new Investidor(nome, cpf, senha);

                // Buscar dados da carteira
                stmtCarteira.setString(1, cpf);
                ResultSet rsCarteira = stmtCarteira.executeQuery();

                if (rsCarteira.next()) {
                    Carteira carteira = new Carteira();
                    carteira.setSaldoReais(rsCarteira.getDouble("saldo_reais"));
                    carteira.getSaldosCripto().put(Bitcoin.class, rsCarteira.getDouble("saldo_bitcoin"));
                    carteira.getSaldosCripto().put(Ethereum.class, rsCarteira.getDouble("saldo_ethereum"));
                    carteira.getSaldosCripto().put(Ripple.class, rsCarteira.getDouble("saldo_ripple"));
                    investidor.setCarteira(carteira);
                }

                return investidor;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    return null;
}
}
