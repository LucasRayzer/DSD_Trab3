package dsd_t3.teste;

import dsd_t3.teste.ServerTime;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Calendar;
import java.util.Date;

public class Client extends Thread {
    
    private String address;
    private int port;
    private long sleepMillis;
    private Date date;

    public Client(String address, int port, long sleepMillis) {
        this.address = address;
        this.port = port;
        this.sleepMillis = sleepMillis;
        this.date = new Date();
    }

    @Override
    public void run() {
        while (true) {
            try {
                long t0 = System.currentTimeMillis();
                
                Socket socket = new Socket(this.address, this.port);                            
                socket.setReuseAddress(true);            
                
                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

                output.writeUTF("Me envie o tempo");
                output.flush();

                ServerTime time = (ServerTime) input.readObject();
                System.out.println("Hora recebida pelo servidor: " + time.getUtc());

                // Calcula a diferença de tempo
                Calendar c = Calendar.getInstance();                                  
                long t1 = System.currentTimeMillis();
                int p = (int)(t1 - t0 - time.getH()) / 2;

                // Ajusta o tempo dependendo da comparação entre o UTC do servidor e o horário do cliente
                 if (time.getUtc().before(date)) {
                    long t2 = time.getH();  
                    long t3 = t2;           
                    long theta = ((t1 - t0) + (t3 - t2)) / 2;
                    
                    c.setTime(date);
                    c.add(Calendar.MILLISECOND, - (int) theta);
                    date = c.getTime();
                } else {                        
                    c.setTime(time.getUtc());                    
                }

                // Atualiza o horário local
                c.add(Calendar.MILLISECOND, p);                
                date = c.getTime();
                System.out.println("Horário atualizado: " + date);


                output.close();
                input.close();
                socket.close();
               System.out.println("Conexão com o servidor encerrada.");

                sleep(sleepMillis);

            } catch (ConnectException ex) {
                System.out.println("A conexão falhou, tente novamente...");
                continue;
            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                e.printStackTrace();
            }
        }        
    }
}
