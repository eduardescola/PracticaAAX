package aar;

public class MainHumiditySensor {

    public static void main(String[] args) throws InterruptedException {
        HumiditySensor server = new HumiditySensor();
        server.run();
    }

}
