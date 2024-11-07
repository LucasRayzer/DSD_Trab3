package dsd_t3;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Calendar;
import java.util.Date;

public class Client {

    public static void main(String args[]) {

        try (Socket socket = new Socket("", 80);
             ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {

            // Tempo de envio do pedido (t0)
            long t0 = System.currentTimeMillis();

            // Solicita a hora ao servidor
            output.writeUTF("Send me the time");
            output.flush();

            // Recebe a hora do servidor
            Time time = (Time) input.readObject();
            
            // Tempo de recebimento (t1)
            long t1 = System.currentTimeMillis();
            
            // Calcula o tempo de ida e volta (RTT) e a correção p
            int p = (int) ((t1 - t0 - time.getH()) / 2);

            // Ajusta a hora local com base na resposta do servidor
            Calendar c = Calendar.getInstance();
            c.setTime(time.getUtc());
            c.add(Calendar.MILLISECOND, p);

            Date horarioSincronizado = c.getTime();
            System.out.println("Horário sincronizado: " + horarioSincronizado);

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro na sincronização: " + e.getMessage());
        }
    
    }
}
