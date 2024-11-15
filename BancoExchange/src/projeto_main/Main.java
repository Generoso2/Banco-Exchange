/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projeto_main;

import javax.swing.SwingUtilities;
import model.Carteira;
import view.ConsultaExtratoFrame;
import view.AbrindoFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AbrindoFrame frame = new AbrindoFrame();
            frame.setVisible(true);
        });
    }
}
