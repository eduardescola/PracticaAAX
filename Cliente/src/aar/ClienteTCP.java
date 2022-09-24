package aar;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClienteTCP {
	private String IP = "";
    private int puerto = 0;

    public ClienteTCP(String IP, int puerto) {
        this.IP = IP;
        this.puerto = puerto;
    }
    
    
    public void run(Scanner inputTeclado) {
        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        System.out.println(puerto);
        System.out.println("Que fichero quieres descargar?");
        String fichero = inputTeclado.nextLine();
    }
}
