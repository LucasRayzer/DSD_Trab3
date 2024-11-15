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
            ObjectOutputStream output = new ObjectOutputStream(connection.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(connection.getInputStream());
            
            long h1 = System.currentTimeMillis();

            String msg = input.readUTF();

            if ("Me envie o tempo".equals(msg)) {
                ServerTime time = new ServerTime(h1, new Date());
                
                output.writeObject(time);
                output.flush();
                MensagemTemporaria.exibir("Hora enviada para o cliente: " + time.getUtc(), 3000);
            }

            output.close();
            input.close();
            connection.close();
            MensagemTemporaria.exibir("Conexão com o cliente " + clientPort + " encerrada.", 3000);
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
