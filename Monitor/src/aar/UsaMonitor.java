package aar;

public class UsaMonitor {

	public static void main(String[] args) {
		Monitor objetoServer= new Monitor(3000, 4000, 5000, 6000);
        objetoServer.run();
        
    }

}
