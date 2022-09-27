package aar;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClienteUDP {
	 private String IP = "";
	 private int puerto = 0;
	    
	 public ClienteUDP(String IP, int puerto) {
	      this.IP = IP;
	      this.puerto = puerto;
	  }
	 public String run() { 
	        DatagramSocket socket = null;
	        try {
	            socket = new DatagramSocket();
	        } catch (IOException e) {
	            System.err.println("Could not listen on port: "+puerto);
	            System.exit(1);
	        }
	        // Enviar datos
	        
	        
	        String peticion = " ";
	        
	        byte[] buf = new byte[256];
	        buf = peticion.getBytes();
	        InetAddress address = null;
	        try {
	            address = InetAddress.getByName(IP);
	        } catch (UnknownHostException ex) {
	            System.err.println("Unknown Host Exception");
	            System.exit(1);
	        }
	        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, puerto);  
	        try {
	            socket.send(packet);
	        } catch (IOException e) {
	            System.err.println("Error when sending");
	            System.exit(1);
	        }
	        
	        //Recibir datos
	        //poner un bucle para recivir todo el rato
	        byte[] bufResponse=new byte [256];
	        DatagramPacket responsePacket = new DatagramPacket(bufResponse, bufResponse.length);
	        try {
	            socket.receive(responsePacket);
	        } catch (IOException e) {
	            System.err.println("Error when receiving");
	            System.exit(1);
	        }
	        
	        //Printar datos recibidos
	        String received = new String(responsePacket.getData(), 0, responsePacket.getLength());
	        System.out.println("Datos Edge server: " + received);

	        socket.close();
	        return (received);
	 }
}
