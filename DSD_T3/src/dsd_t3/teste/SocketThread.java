package dsd_t3.teste;

import dsd_t3.teste.ServerTime;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;

public class SocketThread extends Thread {
    
    private Socket connection;
    private String clientPort;

    public SocketThread(Socket connection) {
        this.connection = connection;
        this.clientPort = connection.getInetAddress().toString();
    }

    @Override
    public void run() {
        try {
            // Criação dos streams de entrada e saída
            ObjectOutputStream output = new ObjectOutputStream(connection.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(connection.getInputStream());
            
            // Captura do tempo inicial do servidor
            long h1 = System.currentTimeMillis();

            // Lê a mensagem do cliente
            String msg = input.readUTF();
            System.out.println("Received message from client: " + msg);

            if ("Send me the time".equals(msg)) {
                // Criação de um objeto ServerTime com o tempo atual
                ServerTime time = new ServerTime(h1, new Date());
                
                // Envio do tempo para o cliente
                output.writeObject(time);
                output.flush();
                System.out.println("Sent time to client: " + time.getUtc());
            }

            // Fechamento das conexões após a resposta
            output.close();
            input.close();
            connection.close();
            System.out.println("Connection with client " + clientPort + " closed.");
        } catch (IOException e) {
            e.printStackTrace();
            try {
                connection.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }  
}
