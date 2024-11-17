
package projeto_main;

import javax.swing.SwingUtilities;
import view.AbrindoFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AbrindoFrame frame = new AbrindoFrame();
            frame.setVisible(true);
        });
    }
}
