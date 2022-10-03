package aar;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClienteTCP {
	private String IP = "";
    private int puerto = 0;

    public ClienteTCP(String IP, int puerto) {
        this.IP = IP;
        this.puerto = puerto;
    }
    
    public void runDownload(Scanner teclat) {
        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        System.out.println("Que fichero quieres descargar?");
        String fichero = teclat.nextLine();
        
        try {
            socket = new Socket(IP, puerto);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: "+IP);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: "+IP);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        String fromUser;
        
        out.println("0");
        out.println(fichero);
        
        try {
            while ((fromServer = in.readLine()) != null) {
                receiveFile("C:/Usuarios/AORUS/eclipse-workspace/Javasockets/src/Clientes/", socket);
                }
        } catch (IOException e) {
            System.err.println(e.getCause());
            System.exit(1);
        }  
            
        try {
            out.close();      
            in.close();
            stdIn.close();
            socket.close();
        } catch (IOException e) {
            System.err.println("Close failed.");
            System.exit(1);
        } 
    }
    
    
    
    public static void receiveFile(String outputFilePath, Socket socket) throws IOException{
        InputStream is;
        BufferedInputStream bis;
        FileOutputStream fos;
        BufferedOutputStream bos;
        
            File output = new File(outputFilePath);
            is = socket.getInputStream();
            bis = new BufferedInputStream(is);
            fos = new FileOutputStream(output);
            bos = new BufferedOutputStream(fos);
            byte[] buffer = new byte[1024];
            int data;
            while(true)
            {
                data = bis.read(buffer);
                if(data != -1)
                {
                    bos.write(buffer, 0, 1024);
                }
                else
                {
                    bis.close();
                    bos.close();
                    break;
                }
            }
        }
    public void runUpload(Scanner teclat) {
    	Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        System.out.println("Introdueix la contrasenya: ");
        String contrasenya = teclat.nextLine();
        
        try {
            socket = new Socket(IP, puerto);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: "+IP);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: "+IP);
            System.exit(1);
        }
        
        out.println("1");
        out.println(contrasenya);
        
        
    }
}
