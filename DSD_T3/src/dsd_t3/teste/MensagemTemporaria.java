
package dsd_t3.teste;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MensagemTemporaria {

    public static void exibir(String mensagem, int duracao) {
        JOptionPane pane = new JOptionPane(mensagem, JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = pane.createDialog(null, "Aviso");

        Timer timer = new Timer(duracao, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose(); 
            }
        });
        timer.setRepeats(false);
        timer.start(); 

        dialog.setVisible(true);
    }
}
