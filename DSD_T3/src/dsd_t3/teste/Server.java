package dsd_t3.teste;

import dsd_t3.teste.ServerTime;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    
    public Server() {}

    public void execute() {
        try {
            ServerSocket serverSocket = new ServerSocket(80);  
            serverSocket.setReuseAddress(true);

            while (true) {
                System.out.println("Aguardando conexões...");
                Socket socket = serverSocket.accept();
                System.out.println("Cliente conectado: " + socket.getInetAddress());

                SocketThread thread = new SocketThread(socket);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
