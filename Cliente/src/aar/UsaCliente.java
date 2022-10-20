package aar;

import java.util.Scanner;

public class UsaCliente {

	public static void main(String[] args) {
		int opcio = 0;
		Scanner teclat = new Scanner(System.in);

		while (opcio != 4) {
			System.out.println(
					"\nQue t'agradaria fer?\n\n-1 monitoritzar els sensors\n"
					+ "-2 Descarregar un informe\n-3 Pujar un informe\n-4 Sortir");

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
				if (opcio != 4) 
					System.out.println("\n\n\n\n\n\nTria l'opcio 1, 2, 3 o 4\n");
				
			}
		}
		teclat.close();
	}

}
