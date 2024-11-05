
package utils;

import java.util.Random;

public class CotacaoUtils {
    public static double gerarVariação(double cotacaoAtual) {
        Random random = new Random();
        double variacao = (random.nextDouble() * 0.1) - 0.05; // Variação entre -5% e +5%
        return cotacaoAtual + (cotacaoAtual * variacao);
    }
}
