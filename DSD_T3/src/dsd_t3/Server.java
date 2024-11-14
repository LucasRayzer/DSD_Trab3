package dsd_t3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    
    public Server() {
    }   
       
    public void execute(){        
        try{            
            ServerSocket serverSocket = new ServerSocket(80);          
            serverSocket.setReuseAddress(true);
            while (true){
                //controller.log("Waiting connection...");
                Socket socket = serverSocket.accept();                
                SocketThread thread = new SocketThread(socket);
                thread.start();                
            }
        }catch (IOException e){
            e.printStackTrace();
        }          
    }
    
}
