
package dsd_t3.teste;

import dsd_t3.teste.Client;
import javax.swing.JOptionPane;

public class MainClient {

    public static void main(String[] args) {
        String input = JOptionPane.showInputDialog(null, "Informe o endereço do servidor", "10.15.120.000");
        Client client = new Client(input, 80, 5000); 
        client.start();
    }

}
