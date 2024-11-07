package dsd_t3;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;

public class SocketThread extends Thread{
    
    private Socket connection;
    private String clientPort;

    public SocketThread(Socket connection) {
        this.connection = connection;
        clientPort = connection.getInetAddress().toString(); 
        //controller.log("Client " + clientPort + " connected, initializing thread " + getId() + "!");
    }

    @Override
    public void run() {
        try{
            ObjectOutputStream output = new ObjectOutputStream(connection.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(connection.getInputStream());
            long h1 = System.currentTimeMillis();
            
            String msg = input.readUTF();
            
            if (msg.equals("Send me the time")){                       
                Time time = new Time(h1, new Date());                
                output.writeObject(time);
                output.flush();
            }
            
            output.close();            
            input.close();
            connection.close();
        }catch(IOException e){
            e.printStackTrace();
            try{
                connection.close();
            }catch(IOException e2){
                e2.printStackTrace();
            }
        }finally{
            //controller.log("Thread " + this.getId() + " finished " + clientPort);
        }
    }  
    
    
}
