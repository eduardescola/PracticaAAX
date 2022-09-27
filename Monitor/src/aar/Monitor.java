package aar;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Monitor {
	private int puertoMonitor = 0;
	private String IP = "";
	private int puertoDestino = 0;
	private int puertoDestino2 = 0;
	private int puertoDestino3 = 0;

	public Monitor(int puerto, String IP, int puertoDestino, int puertoDestino2, int puertoDestino3) {
		this.puertoMonitor = puerto;
		this.puertoDestino = puertoDestino;
		this.puertoDestino2 = puertoDestino2;
		this.puertoDestino3 = puertoDestino3;
	}

	public void run() {
		
		DatagramSocket socket = crearSocket(puertoMonitor);
		
		byte[] buf = crearBuffer();
		
		DatagramPacket packet = crearPacket(buf);
		
		//recivir datos
		try {
			socket.receive(packet);
		} catch (IOException e) {
			System.err.println("Error when receiving");
			System.exit(1);
		}
		
		
		
		
		
		//conectar a sensores
		
		String received = new String(packet.getData(), 0, packet.getLength());

		buf = received.getBytes();

		// envio datos a cliete
		InetAddress addressOrigen = packet.getAddress();
		
		///////////enviarDatos(puertoDestino, socket, " ", addressOrigen, puertoMonitor);
		
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
	
	private DatagramSocket crearSocket(int puerto) {
		DatagramSocket socket = null;

		try {
			socket = new DatagramSocket(puerto);
		} catch (IOException e) {
			System.err.println("Could not listen on port: " + puerto);
			System.exit(1);
		}
		return socket;
	}
	
	private DatagramPacket crearPacket(byte[] buf) {
		DatagramPacket packet = new DatagramPacket(buf, buf.length);
		return packet;
	}
	
	private byte[] crearBuffer() {
		byte[] buf = new byte[256];
		return buf;
	}
	
	private void enviarDatos(int puerto, DatagramSocket socket, String datos, InetAddress addressOrigen, int puertoOrigen) {
				
		//declarar datos
		DatagramPacket packet;
		InetAddress address = null;
		byte[] buf = new byte[256];
		
		buf = datos.getBytes();
		
		//pasar ip del monitor
		try {
			address = InetAddress.getByName(IP);
		} catch (UnknownHostException ex) {
			System.err.println("Unknown Host Exception");
			System.exit(1);
		}
		
		//enviar datos
		if (datos == " ") 
			packet = new DatagramPacket(buf, buf.length, address, puerto);
		else 
			packet = new DatagramPacket(buf, buf.length, addressOrigen, puertoOrigen);
		
		try {
			socket.send(packet);
		} catch (IOException e) {
			System.err.println("Error when sending");
			System.exit(1);
		}

	}

}
