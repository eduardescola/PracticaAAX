package aar;

import java.net.Socket;

public class ClienteUDPThread extends Thread{
	private Socket socket = null;
	
	public ClienteUDPThread(Socket socket) {
		this.socket = socket;
	    }

	@Override
    public void run() {
		
	}
}
