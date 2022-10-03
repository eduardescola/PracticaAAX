package aar;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
		
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			System.err.println("Create streams failed.");
			System.exit(1);
		}

		String inputLine;

		try {			
			if ((inputLine = in.readLine()).equals(clientDescarga)) {
				String ruta = null;
				ruta = getFile(inputLine);
				sendFile(ruta, socket);
			}else {
				
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
		String PATH = "C:\\Users\\AORUS\\eclipse-workspace\\Javasockets\\src\\Edge\\";
		File raiz = new File(PATH);
		String[] fileList = raiz.list();
		for (String str : fileList) {
			if (fileName.equals(str)) {
				return PATH + "/" + str;
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
}
