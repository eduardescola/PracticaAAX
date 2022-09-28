package aar;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Monitor {
	private int puertoMonitor = 0;
	private String IP = "";
	private int puertoDestino1 = 0;
	private int puertoDestino2 = 0;
	private int puertoDestino3 = 0;

	public Monitor(int puerto, String IP, int puertoDestino, int puertoDestino2, int puertoDestino3) {
		this.puertoMonitor = puerto;
		this.puertoDestino1 = puertoDestino;
		this.puertoDestino2 = puertoDestino2;
		this.puertoDestino3 = puertoDestino3;
	}

	public void run() {
		//recive peticion cliente
		DatagramSocket socket = crearSocket(puertoMonitor);
		
		byte[] buf = crearBuffer();
		
		DatagramPacket packet = crearPacket(buf);
		
		recivirDatos(socket, packet);
		InetAddress addressOrigen = packet.getAddress();
		int puertoOrigen = packet.getPort();
		
		//envia peticion a sensores
		enviarDatos(puertoDestino1, socket, " ", null, 0);
		enviarDatos(puertoDestino2, socket, " ", null, 0);
		enviarDatos(puertoDestino3, socket, " ", null, 0);
		
		//tratar packet sensores
		byte[] bufSensor = crearBuffer();
		
		DatagramPacket packetSensor = crearPacket(bufSensor);
		
		while (!socket.isClosed()) {
			recivirDatos(socket, packetSensor);
			
			String packetTratado=tratarDatos(packetSensor, bufSensor);
			
			enviarDatos(0, socket, packetTratado, addressOrigen, puertoOrigen);
		}
		
		System.out.println("Envio finalizado!");
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
	
	private void recivirDatos(DatagramSocket socket, DatagramPacket packet) {
		try {
			socket.receive(packet);
		} catch (IOException e) {
			System.err.println("Error when receiving");
			System.exit(1);
		}
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
	
	private String tratarDatos(DatagramPacket packet, byte[] buf) {
		String received = new String(packet.getData(), 0, packet.getLength());
		String[] partes = received.split(" ");
		String valor = partes[0];
		String unidad = partes[1];
		
		int valorInt = Integer.parseInt(valor);
		if (unidad.equals("F"))
			received=farenheitToCelsius(valorInt, unidad);
		return received;
	}

	private String farenheitToCelsius(int valor, String unidad) {
		valor=(valor-32)*5/9; 
		unidad="C";
		String convertido=valor+" "+unidad;
		return convertido;
	}
	
}
