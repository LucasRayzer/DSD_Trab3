
package dsd_t3;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;


public class Server {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(80)) {
            System.out.println("Servidor iniciado. Aguardando conexões...");

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream());
                     ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream())) {

                    System.out.println("Cliente conectado: " + clientSocket.getInetAddress());

                    long h1 = System.currentTimeMillis();

                    String msg = input.readUTF();

                    if (msg.equals("Send me the time")) {
                        Time time = new Time(h1, new Date());
                        output.writeObject(time);
                        output.flush();
                        System.out.println("Hora enviada para o cliente.");
                    }
                } catch (IOException e) {
                    System.out.println("Erro ao lidar com o cliente: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao iniciar o servidor: " + e.getMessage());
        }
    }
}

