package Dev_2_v1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String command = "";
        try{
            InetAddress serverAddress = InetAddress.getByName("127.0.0.1");
            System.out.println("server Ip address: " + serverAddress.getHostAddress());
            while (true) {
                Socket socket = new Socket(serverAddress, 9090);
                PrintWriter send = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader receive = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                //send the command
                    command = sc.nextLine();
                    send.println(command);
                //receive the results
                String line;
                while ((line=receive.readLine())!=null) {
                    System.out.println(line);
                }

                if (command.equalsIgnoreCase("exit")){
                    break;
                }
                receive.close();
                send.close();
                socket.close();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
