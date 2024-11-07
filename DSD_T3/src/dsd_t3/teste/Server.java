package dsd_t3.teste;

import dsd_t3.teste.ServerTime;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    
    public Server() {}

    public void execute() {
        try {
            // Criação do ServerSocket que escuta na porta 80
            ServerSocket serverSocket = new ServerSocket(80);  
            System.out.println("Server is running on port 80...");
            serverSocket.setReuseAddress(true);

            while (true) {
                // Aguardando a conexão do cliente
                System.out.println("Waiting for a connection...");
                Socket socket = serverSocket.accept();
                System.out.println("Client connected: " + socket.getInetAddress());

                // Criando e iniciando uma nova thread para tratar a conexão
                SocketThread thread = new SocketThread(socket);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
