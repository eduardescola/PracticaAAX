package aar;

public class MainPressureSensor {

    public static void main(String[] args) throws InterruptedException {
        PressureSensor server = new PressureSensor();
        server.run();
    }

}
