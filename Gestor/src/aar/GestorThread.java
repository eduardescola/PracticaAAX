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

public class GestorThread extends Thread {
	private Socket socket = null;

	public GestorThread(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		PrintWriter out = null;
		BufferedReader in = null;
		String clientDescarga = "0";

		// crear in/outs
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			System.err.println("Create streams failed.");
			System.exit(1);
		}
	
		String contrasenya = "elpepe";

		// validar contraseña en el else
		try {
			String inputLine = in.readLine();
			if (inputLine.equals(clientDescarga)) {
				inputLine = in.readLine();
				while (!inputLine.equals(contrasenya)) {
					out.println("0");
					inputLine = in.readLine();
				}
				out.println("1");
				inputLine = in.readLine();
				
				String ruta = getFile(inputLine);
				while (ruta==null) {
					out.println("0");
					inputLine = in.readLine();
					ruta = getFile(inputLine);
				}
				out.println(ruta);
				sendFile(ruta, socket);
				System.out.println("Fitxer enviat");
			} else {
		        try {
		        	String nomFitxer=in.readLine();
	                receiveFile(System.getProperty("user.dir")+"\\reports\\"+nomFitxer, socket);
	                System.out.println("Fitxer rebut");
		        } catch (IOException e) {
		            System.err.println(e.getCause());
		            System.exit(1);
		        }  

			}

		} catch (IOException e) {
			System.err.println("Read failed.");
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

	private String getFile(String fileName) {
		String PATH = System.getProperty("user.dir") + "\\reports";
		File raiz = new File(PATH);
		String[] fileList = raiz.list();
		for (String str : fileList) {
			if (fileName.equals(str)) {
				return PATH + "//" + str;
			}
		}
		return null;
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
}
