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

	public void run() {
		
		DatagramSocket socket = crearSocket(puerto);

		enviarPeticion(puerto, socket);
		
		// Recivir datos
		int i=1;
		while(true) {
			byte[] buf=crearBuffer();
			DatagramPacket packetMonitor=crearPacket(buf);
			recivirDatos(socket, packetMonitor);
			
			String received = new String(packetMonitor.getData(), 0, packetMonitor.getLength());
			String response=i+". Dato Sensor: " + received;
			System.out.println(response);
			i++;
			if(i==11)
				break;
		}
		socket.close();
	}
	
	private DatagramSocket crearSocket(int puerto) {
		DatagramSocket socket = null;

		try {
			socket = new DatagramSocket();
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
	
	private void recivirDatos(DatagramSocket socket, DatagramPacket packet) {
		try {
			socket.receive(packet);
		} catch (IOException e) {
			System.err.println("Error when receiving");
			System.exit(1);
		}
	}
	
	private void enviarPeticion(int puerto, DatagramSocket socket) {

		// declarar peticion
		DatagramPacket packet;
		InetAddress address = null;
		byte[] buf = new byte[256];
		String peticion = " ";

		buf = peticion.getBytes();

		// pasar ip del Cliente
		try {
			address = InetAddress.getByName(IP);
		} catch (UnknownHostException ex) {
			System.err.println("Unknown Host Exception");
			System.exit(1);
		}

		// enviar peticion
		packet = new DatagramPacket(buf, buf.length, address, puerto);
	
		try {
			socket.send(packet);
		} catch (IOException e) {
			System.err.println("Error when sending");
			System.exit(1);
		}
	}
}
