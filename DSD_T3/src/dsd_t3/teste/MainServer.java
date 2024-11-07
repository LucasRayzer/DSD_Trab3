package dsd_t3.teste;

import dsd_t3.teste.Client;
import dsd_t3.teste.Server;

public class MainServer {
    public static void main(String[] args) {
        Server server = new Server();
        Thread serverThread = new Thread(() -> server.execute());
        serverThread.start();  
        
        try {
            Thread.sleep(1000);  
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
