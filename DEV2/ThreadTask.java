package Dev_2_v2;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class ThreadTask extends Thread{
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    private Socket socket = null;

    public ThreadTask(Socket socket){
        this.socket=socket;
    }
    public void run(){
        String fileToOpen="";
        String path = "C:\\Users\\Imran Costa\\Desktop\\caw";
        File file = new File(path);
        try {
            System.out.println("waiting for client...");
            PrintWriter send = new PrintWriter(socket.getOutputStream(),true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // get the command
            String receive = in.readLine();
            // get the value of the command
            String arrReceived[] = receive.split(" ");
            //treat the command received
            if (arrReceived[0].equalsIgnoreCase("list")){
                //List
                send.println(BLUE+"the file contains " + file.list().length + " elements" + RESET);
                try {
                    for (File myFile : file.listFiles()){
                        if (myFile.isDirectory()){
                            send.println(PURPLE+myFile.getName()+RESET);
                        }
                        else{
                            send.println(myFile.getName());
                        }
                    }
                }
                catch (Exception e){
                    send.println(RED+"this directory can't be readen"+RESET);
                }
                //end of list
            }
            else {
                if (arrReceived[0].equalsIgnoreCase("get")){
                    // get the file wanted
                    for (int i = 1; i < arrReceived.length ; i++) {
                        fileToOpen=String.join(" ",arrReceived[i]);
                    }
                    boolean fileExist=false;
                    StringBuilder newPath =new StringBuilder();
                    //search for the file
                    for (int i = 0; i < file.list().length; i++) {
                        if (file.list()[i].equals(fileToOpen)){
                            fileExist=true;
                            newPath.append(path).append("\\").append(fileToOpen);
                        }
                    }
                    if (fileExist){
                        try {
                            if (fileToOpen.contains(".")){
                                send.println(newPath);
                                // open the file
                                send.println(GREEN+"Opening the file ..."+RESET);
                                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                                BufferedReader br = new BufferedReader(new FileReader(String.valueOf(newPath)));
                                String line ;
                                while ((line=br.readLine())!=null){
                                    bw.write(line);
                                    bw.newLine();
                                    bw.flush();
                                }
                                bw.close();
                                br.close();
                            }
                            else{
                                send.println(RED+"change the path to get access to this file"+RESET);
                            }
                        }
                        catch (FileNotFoundException e){
                            send.println(RED+"You can't open this file"+RESET);
                        }
                    }
                    else {
                        //write not found
                        send.println(RED+"the file doesn't exist"+RESET);
                    }
                }
                else{
                    if (arrReceived[0].equalsIgnoreCase("exit")){
                        send.println("exit...");
                    }
                    else {
                        send.println(RED+"Unknown Command!"+RESET+"\n"+"USE \n"+GREEN+
                                "list : to show the list of files including in the directory \n" +
                                "get : to open an file not a folder \n" +
                                "exit : to quit the operation"
                                +RESET);
                    }
                }
            }
            //close and flush
            in.close();
            send.close();
            socket.close();
        }
        catch (SocketException e){
            e.printStackTrace();
        }
        catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
