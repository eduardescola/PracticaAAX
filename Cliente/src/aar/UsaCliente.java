package aar;

import java.util.Scanner;

public class UsaCliente {

	public static void main(String[] args) {
		int opcio = 0;
		Scanner teclat = new Scanner(System.in);

		while (opcio != 1 || opcio != 2 || opcio != 3) {
			System.out.println(
					"Que t'agradaria fer?\n\n-1 monitoritzar els sensors\n"
					+ "-2 Descarregar un informe\n-3 Pujar un informe");

			opcio = Integer.parseInt(teclat.nextLine());

			switch (opcio) {
			case 1:
				ClienteUDP objetoClienteUDP = new ClienteUDP("localhost", 3000);
				objetoClienteUDP.run();
				break;
			case 2:
				ClienteTCP objetoClienteTCP = new ClienteTCP("localhost", 7000);
				objetoClienteTCP.runDownload(teclat);
				break;
			case 3:
				ClienteTCP objetoClienteTCP2 = new ClienteTCP("localhost", 7000);
				objetoClienteTCP2.runUpload(teclat);
				break;
			default:
				System.out.println("\nTria l'opcio 1, 2 o 3");
			}
		}
		teclat.close();
	}

}
