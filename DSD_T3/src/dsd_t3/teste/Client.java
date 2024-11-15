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
                MensagemTemporaria.exibir("Hora recebida pelo servidor: " + time.getUtc(), 3000);

                // Calcula a diferença de tempo
                Calendar c = Calendar.getInstance();                                  
                long t1 = System.currentTimeMillis();
                int p = (int)(t1 - t0 - time.getH()) / 2;

                // Ajusta o tempo dependendo da comparação entre o UTC do servidor e o horário do cliente
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

                // Atualiza o horário local
                c.add(Calendar.MILLISECOND, p);                
                date = c.getTime();
                MensagemTemporaria.exibir("Horário atualizado: " + date, 3000);


                output.close();
                input.close();
                socket.close();
                MensagemTemporaria.exibir("Conexão com o servidor encerrada.", 3000);

                sleep(sleepMillis);

            } catch (ConnectException ex) {
                MensagemTemporaria.exibir("A conexão falhou, tente novamente...", 3000);
                continue;
            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                e.printStackTrace();
            }
        }        
    }
}
