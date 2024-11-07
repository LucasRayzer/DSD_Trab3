package dsd_t3;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.Thread.sleep;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Ivens
 */
public class Client extends Thread{

    private String adress;
    private int port;
    private long sleepMilis;
    private Date date;
    
    public Client(String adress, int port, long sleepMilis){
        this.adress = adress;
        this.port = port;
        this.sleepMilis = sleepMilis;
        this.date = new Date();
    }
    
    @Override
    public void run() {        
        while (true){
            try{
                long t0 = System.currentTimeMillis();                
                Socket socket = new Socket(this.adress, this.port);                            
                socket.setReuseAddress(true);            
                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

                output.writeUTF("Send me the time");
                output.flush();                      

                Time time = (Time) input.readObject();
                
                Calendar c = Calendar.getInstance();                                  
                
                long t1 = System.currentTimeMillis();
                int p = (int)(t1 - t0 - time.getH()) / 2;
                
                if (time.getUtc().before(date)){
                    double aux = p / 2;
                    p = (int) Math.ceil(aux);
                    if (p < 0){
                        p = 1;
                    }
                    c.setTime(date);
                }else{                        
                    c.setTime(time.getUtc());                    
                }
                
                c.add(Calendar.MILLISECOND, p);                
                date = c.getTime();
                
                //controller.attDate(date);

                output.close();
                input.close();
                socket.close();
                sleep(sleepMilis);
            }catch(ConnectException ex){
                continue;
            }catch(IOException | ClassNotFoundException | InterruptedException e){
                e.printStackTrace();
            }
        }        
    }
    
}
