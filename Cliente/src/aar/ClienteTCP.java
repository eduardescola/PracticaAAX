package aar;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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

        String fromServer;
        String fromUser;
        
        out.println("0");
        out.println(fichero);
        
        try {
            while ((fromServer = in.readLine()) != null) {
                receiveFile(System.getProperty("user.dir")+"/reports", socket);
        	}
        } catch (IOException e) {
            System.err.println(e.getCause());
            System.exit(1);
        }  
            
        try {
            out.close();      
            in.close();
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
        
        System.out.println("Introdueix la contrasenya: ");
        String contrasenya = teclat.nextLine();
        
        out.println("1");
        out.println(contrasenya);
        String validacion;
        String fitxer;
        
        try {			
			while(!(validacion = in.readLine()).equals("0") && contrasenya != "0") {
				System.out.println("Contrasenya incorrecta! \\n Introdueix la contrasenya: ");
		        contrasenya = teclat.nextLine();
		        out.println(contrasenya);
			}
			if (contrasenya != "0") {
				System.out.println("\nIntrodueix el nom del fitxer que vols enviar: ");
				fitxer = teclat.nextLine();
				String ruta = getFile(fitxer);
				sendFile(ruta, socket);
			}
			
		} catch (IOException e) {
			System.err.println("Read failed.");
			System.exit(1);
		}
    }
    
    public static void sendFile(String inputFilePath, Socket socket) throws IOException {
		FileInputStream fis;
		BufferedInputStream bis;
		OutputStream os;
		BufferedOutputStream bos;

		File input = new File(inputFilePath);
		
		fis = new FileInputStream(input);
		bis = new BufferedInputStream(fis);
		os = socket.getOutputStream();
		bos = new BufferedOutputStream(os);
		
		byte[] buffer = new byte[1024];
		int data;
		
		while (true) {
			data = bis.read(buffer);
			if (data != -1) {
				bos.write(buffer, 0, 1024);
			} else {
				bis.close();
				bos.close();
				break;
			}
		}
	}
    
    private String getFile(String fileName) {
		String PATH = System.getProperty("user.dir")+"/reports";
		File raiz = new File(PATH);
		String[] fileList = raiz.list();
		for (String str : fileList) {
			if (fileName.equals(str)) {
				return PATH + "/" + str;
			}
		}
		return null;
	}

}
