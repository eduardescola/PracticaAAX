package aar;

import java.io.IOException;
import java.net.ServerSocket;

public class Gestor {
private int puerto = 0;
    
    public Gestor(int puerto) {
        this.puerto = puerto; 
    }
    
    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(puerto);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 5555.");
            System.exit(1);
        }
        
        while (true) {       
            try { 
                new GestorThread(serverSocket.accept()).start();
            } catch (IOException e) {
                System.err.println("Accept failed");
            }
        }     
    } 
}
