package aar;

import java.util.Scanner;

public class UsaCliente {

	public static void main(String[] args) {
		Scanner inputTeclado = new Scanner(System.in);
		String puertoDestino;
		String puertoMonitor;
		
		
		ClienteUDP objetoClienteUDP= new ClienteUDP("localhost",3000);
        puertoMonitor = objetoClienteUDP.run(inputTeclado);

        
        
        //ClienteTCP objetoClienteTCP= new ClienteTCP("localhost",Integer.parseInt(puertoDestino));
		//objetoClienteTCP.run(inputTeclado);
		//inputTeclado.close();
	}

}
