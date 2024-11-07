package dsd_t3;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Calendar;
import java.util.Date;

public class Client {

    public static void main(String args[]) {

        try (Socket socket = new Socket("1015120202", 80); ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream()); ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {

            long t0 = System.currentTimeMillis();

            output.writeUTF("Send me the time");
            output.flush();

            Time time = (Time) input.readObject();

            Calendar c = Calendar.getInstance();

            Date date = new Date();

            long t1 = System.currentTimeMillis();
            int p = (int) (t1 - t0 - time.getH()) / 2;

            if (time.getUtc().before(date)) {
                double aux = p / 2;
                p = (int) Math.ceil(aux);
                if (p < 0) {
                    p = 1;
                }
                c.setTime(date);
            } else {
                c.setTime(time.getUtc());
            }

            c.add(Calendar.MILLISECOND, p);
            date = c.getTime();

            System.out.println("Horário sincronizado: " + date.toString());

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro na sincronização: " + e.getMessage());
        }

    }
}
