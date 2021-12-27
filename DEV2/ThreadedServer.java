package Dev_2_v2;

import java.net.ServerSocket;
import java.net.Socket;

public class ThreadedServer {
    public static void main(String[] args) {
        int port = 9000;
        ServerSocket serverSocket = null;
        System.out.println("server listening on the port :" + port);
        System.out.println("waiting for client...");
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = null;
                socket = serverSocket.accept();
                ThreadTask clientThread = new ThreadTask(socket);
                clientThread.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}