
package dsd_t3.teste;

import dsd_t3.teste.Client;

public class MainClient {

    public static void main(String[] args) {
        Client client = new Client("localhost", 80, 5000); 
        client.start();
    }

}
