package aar;

public class MainTemperatureSensor {

    public static void main(String[] args) throws InterruptedException {
        TemperatureSensor server = new TemperatureSensor();
        server.run();
    }

}
