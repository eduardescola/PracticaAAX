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

		try {
			socket = new Socket(IP, puerto);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host: " + IP);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to: " + IP);
			System.exit(1);
		}

		System.out.println("Introdueix la contrasenya: ");
		String contrasenya = teclat.nextLine();

		out.println("0");
		out.println(contrasenya);
		
		String validacion;

		try {
			while ((validacion = in.readLine()).equals("0") && !contrasenya.equals("0")) {
				System.out.println("Contrasenya incorrecta! Introdueix la contrasenya: ");
				contrasenya = teclat.nextLine();
				out.println(contrasenya);
			}
			if (!contrasenya.equals("0")) {
				System.out.println("Quin fitxer vols descarregar?");
				String fichero = teclat.nextLine();

				out.println(fichero);
				
				while((validacion=in.readLine()).equals("0")) {
					System.out.println("\nEl fitxer '"+fichero+"' no existeix!");
					System.out.println("\nQuin fitxer vols descarregar?");
					fichero = teclat.nextLine();
					out.println(fichero);
				}
				
				receiveFile(System.getProperty("user.dir") + "\\reports\\"+fichero, socket);
				System.out.println("\nFitxer rebut!");
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

	public static void receiveFile(String outputFilePath, Socket socket) throws IOException {
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

	public void runUpload(Scanner teclat) {
		Socket socket = null;
		PrintWriter out = null;

		try {
			socket = new Socket(IP, puerto);
			out = new PrintWriter(socket.getOutputStream(), true);

		} catch (UnknownHostException e) {
			System.err.println("Don't know about host: " + IP);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to: " + IP);
			System.exit(1);
		}

		out.println("1");
		String fichero;

		try {
			System.out.println("\nIntrodueix el nom del fitxer que vols enviar: ");
			fichero = teclat.nextLine();
			String ruta = getFile(fichero);
			
			while(ruta==null) {
				System.out.println("\nEl fitxer '"+fichero+"' no existeix!");
				System.out.println("\nQuin fitxer vols enviar?");
				fichero = teclat.nextLine();
				ruta = getFile(fichero);
			}
			
			out.println(fichero);
			sendFile(ruta, socket);
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
		String PATH = System.getProperty("user.dir") + "\\reports";
		File raiz = new File(PATH);
		String[] fileList = raiz.list();
		for (String str : fileList) {
			if (fileName.equals(str)) {
				return PATH + "\\" + str;
			}
		}
		return null;
	}

}
