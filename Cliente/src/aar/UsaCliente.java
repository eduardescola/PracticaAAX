package aar;

import java.util.Scanner;

public class UsaCliente {

	public static void main(String[] args) {
		Scanner inputTeclado = new Scanner(System.in);
		String puertoMonitor;
		int opcio;
		
		Scanner teclat = new Scanner(System.in);
	    System.out.println("Que t'agradaria fer?\n 1 monitoritzar els sensors\n 2 Descarregar un informe\n 3 Pujar un informe");
	    
	    while (!teclat.hasNextInt()) {
	        teclat.next();
	    }
	        opcio = teclat.nextInt();
	        teclat.close();
		switch (opcio) {
		case 1:
			ClienteUDP objetoClienteUDP= new ClienteUDP("localhost",3000);
	        puertoMonitor = objetoClienteUDP.run(inputTeclado);
		    break;
		  case 2:
			//ClienteTCP objetoClienteTCP= new ClienteTCP("localhost",Integer.parseInt(puertoDestino));
			//objetoClienteTCP.run(inputTeclado);
			//inputTeclado.close();
		    break;
		  case 3:
			//Falta Contrasenya
			//ClienteTCP objetoClienteTCP= new ClienteTCP("localhost",Integer.parseInt(puertoDestino));
			//objetoClienteTCP.run(inputTeclado);
			//inputTeclado.close();
		    break;
		  default:
	            System.out.println("Tria entre 1 i 3");
		}
		
        
	}

}
