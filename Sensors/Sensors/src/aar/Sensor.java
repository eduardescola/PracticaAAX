package aar;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

public abstract class Sensor {
    private DatagramSocket socket;
    private InetAddress clientAddress;
    private int clientPort;

    private final Random generator = new Random();
    private final int max;
    private final String unit;
    private final int port;
    private final int readingDelayMs;

    protected Sensor(int port, int max, String unit, int readingDelayMs) {
        this.port = port;
        this.max = max;
        this.unit = unit;
        this.readingDelayMs = readingDelayMs;
    }

    public void run() throws InterruptedException {
        startUp();

        while (true) {
            Thread.sleep(readingDelayMs);
            sendReading();
        }
    }

    private void startUp() {
        try {
            System.out.println("Waiting for client on port " + port + "...");
            socket = new DatagramSocket(port);
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + port);
            System.exit(1);
        }

        byte[] requestBuffer = new byte[256];
        DatagramPacket requestPacket = new DatagramPacket(requestBuffer, requestBuffer.length);

        try {
            socket.receive(requestPacket);
            clientAddress = requestPacket.getAddress();
            clientPort = requestPacket.getPort();
        } catch (IOException e) {
            System.err.println("Error when receiving");
            System.exit(1);
        }
    }

    private void sendReading() {
        String message = generator.nextInt(max) + " " + unit;
        byte[] buffer = message.getBytes();

        DatagramPacket responsePacket = new DatagramPacket(buffer, buffer.length, clientAddress, clientPort);

        try {
            System.out.println("Sending reading " + message + "...");
            socket.send(responsePacket);
        } catch (IOException e) {
            System.err.println("Error when sending");
            System.exit(1);
        }
    }
}
