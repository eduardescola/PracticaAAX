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
		PrintWriter out = crearOut(socket);
		BufferedReader in = crearIn(socket);

		String contrasenya = "elpepe";

		try {
			String inputLine = in.readLine();

			// Comparamos lo que recibe el Gestor del cliente con un "0" para que el Gestor
			// sepa que es una petición de Descarga.
			if (inputLine.equals("0")) {
				comprobarContrasenya(inputLine, in, out, contrasenya);

				// Enviamos un "1" par que el cliente sepa que la contrasenya es correcta
				out.println("1");
				inputLine = in.readLine();

				String ruta = recibirRuta(inputLine, in, out);

				out.println(ruta);
				sendFile(ruta, socket);
				System.out.println("Fitxer enviat");
			} else {
				// Como en este caso recibe un "1", el Gestor lo reconocería como una peticion
				// de Subida.
				recibirSubida(in);
			}

		} catch (IOException e) {
			System.err.println("Read failed.");
			System.exit(1);
		}

		cerrarSocketOutIn(socket, out, in);
	}

	private PrintWriter crearOut(Socket socket) {
		PrintWriter out = null;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			System.err.println(e.getCause());
			System.exit(1);
		}
		return out;
	}

	private BufferedReader crearIn(Socket socket) {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			System.err.println(e.getCause());
			System.exit(1);
		}
		return in;
	}

	private void comprobarContrasenya(String inputLine, BufferedReader in, PrintWriter out, String contrasenya) {
		try {
			inputLine = in.readLine();

			while (!inputLine.equals(contrasenya)) {
				
				//Si la contrasenya es incorrecta, el Gestor envia un "0" para que el Cliente
				//sepa que es incorrecta y vuelva a introducir otra.
				out.println("0");
				inputLine = in.readLine();
			}
		} catch (IOException e) {
			System.err.println(e.getCause());
			System.exit(1);
		}
	}

	private String recibirRuta(String inputLine, BufferedReader in, PrintWriter out) {
		String ruta = getFile(inputLine);
		try {
			while (ruta == null) {
				//Si el fichero no existe, el Gestor envia un "0" para que el Cliente
				//sepa que no existe y vuelva a pedir otro. 
				out.println("0");
				inputLine = in.readLine();
				ruta = getFile(inputLine);
			}
			return ruta;
		} catch (IOException e) {
			System.err.println(e.getCause());
			System.exit(1);
		}
		return ruta;
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

	private void recibirSubida(BufferedReader in) {
		try {
			String nomFitxer = in.readLine();
			receiveFile(System.getProperty("user.dir") + "\\reports\\" + nomFitxer, socket);
			System.out.println("Fitxer rebut");
		} catch (IOException e) {
			System.err.println(e.getCause());
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

	private void cerrarSocketOutIn(Socket socket, PrintWriter out, BufferedReader in) {
		try {
			out.close();
			in.close();
			socket.close();
		} catch (IOException e) {
			System.err.println("Close failed.");
			System.exit(1);
		}
	}
}
