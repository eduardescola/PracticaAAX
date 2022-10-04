package aar;

import java.util.Scanner;

public class UsaCliente {

	public static void main(String[] args) {
		int opcio;
		Scanner teclat = new Scanner(System.in);
		
		System.out.println(
				"Que t'agradaria fer?\n 1 monitoritzar els sensors\n 2 Descarregar un informe\n 3 Pujar un informe");

		opcio = teclat.nextInt();
		switch (opcio) {
		case 1:
			ClienteUDP objetoClienteUDP = new ClienteUDP("localhost", 3000);
			objetoClienteUDP.run();
			teclat.close();
			break;
		case 2:
			ClienteTCP objetoClienteTCP = new ClienteTCP("localhost", 7000);
			objetoClienteTCP.runDownload(teclat);
			teclat.close();
			break;
		case 3:
			ClienteTCP objetoClienteTCP2 = new ClienteTCP("localhost", 7000);
			objetoClienteTCP2.runUpload(teclat);
			teclat.close();
			break;
		default:
			System.out.println("Tria l'opcio 1, 2 o 3");
		}

	}

}
