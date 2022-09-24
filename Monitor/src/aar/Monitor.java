package aar;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Monitor {
	private int puerto = 0;
	public Monitor(int puerto) {
        this.puerto = puerto; 
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
