import server.NIOClient;
import server.NIOServer;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        Process server;
        NIOClient client;

        try {
            server = NIOServer.start();
            client = NIOClient.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String resp1 = client.sendMessage("hello");
        String resp2 = client.sendMessage("world");

        try {
            NIOClient.stop();
            server.destroy();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
