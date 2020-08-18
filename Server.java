import java.net.*;
import java.io.*;
import java.util.*;

public class Server {

    public void client(Socket socket) {
        System.out.printf("Connecting to Client...");
        String serverMenu = "--------------------\nWelcome to CharFreqServer \nType any of the following options:\nNewRequest <INPUTSTRING>\nStatusRequest <passcode>\nExit\n--------------------\n Enter: \n";
        String line = "";
        ArrayList<String> queries = new ArrayList<String>();
        try (
            final DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            final BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            final BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            //Introduce Server
            System.out.printf("sending menu\n");
            out.writeBytes(serverMenu);
            
            while ((line = in.readLine()) != null) {
                String[] temp = line.split(" ");
                
                System.out.print(line);
                out.writeBytes("Server says " + line + "\n");

            }
            

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            System.out.printf("Lost Connection to Client..");
        }
    }

    public Server(int port) {
        
        try (
            final ServerSocket server = new ServerSocket(port);    
        ){

            while(true) {
                Socket socket = server.accept();
                new Thread(new Runnable() { public void run() {client(socket);} }).start();
            }

        } catch(IOException i) {
            System.out.println(i);
        } finally {
            System.out.println("Closing connection");
        }
    }

    public static void main(String[] args) {
        Server server = new Server(5000);
    }
    
}