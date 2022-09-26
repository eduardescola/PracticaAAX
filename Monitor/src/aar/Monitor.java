package aar;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Monitor {
	private int puerto = 0;
	private int puertoDestino = 0;
	private int puertoDestino2 = 0;
	private int puertoDestino3 = 0;
	public Monitor(int puerto, int puertoDestino, int puertoDestino2, int puertoDestino3) {
        this.puerto = puerto; 
        this.puertoDestino = puertoDestino;
        this.puertoDestino2 = puertoDestino2;
        this.puertoDestino3 = puertoDestino3;
    }
	public void run() {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(puerto);
        } catch (IOException e) {
            System.err.println("Could not listen on port: "+puerto);
            System.exit(1);
        }
        
            byte[] buf = new byte[256];  
            
            DatagramPacket packet = new DatagramPacket(buf, buf.length);       
            try {
                socket.receive(packet);
            } catch (IOException e) {
                System.err.println("Error when receiving");
                System.exit(1);
            }
            String received = new String(packet.getData(), 0, packet.getLength());
            String ipedge = new String("Viva Peru");
    		buf = ipedge.getBytes();
            InetAddress addressOrigen = packet.getAddress();
            int puertoOrigen = packet.getPort();
            packet = new DatagramPacket(buf, buf.length, addressOrigen, puertoOrigen);
            try {
                socket.send(packet);
            } catch (IOException e) {
                System.err.println("Error when sending");
                System.exit(1);
            }
            System.out.println("Peticion servida");
        socket.close();
	}
}
