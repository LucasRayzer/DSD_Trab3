package dsd_t3.teste;

import dsd_t3.teste.ServerTime;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;

public class Server {
    
    public Server() {}

    public void execute() {
        try {
            ServerSocket serverSocket = new ServerSocket(80);  
            serverSocket.setReuseAddress(true);

            while (true) {
                MensagemTemporaria.exibir("Aguardando conexões...", 3000);
                Socket socket = serverSocket.accept();
                MensagemTemporaria.exibir("Cliente conectado: " + socket.getInetAddress(), 3000);
                SocketThread thread = new SocketThread(socket);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
