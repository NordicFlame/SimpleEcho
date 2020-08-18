import java.util.*;
import java.net.*;
import java.io.*;

public class Client {

    public Client(String address, int port) {
        System.out.println("Client Booting...");
        try (
            final Socket socket = new Socket(address, port);
            final Scanner scan = new Scanner(System.in);
            final BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            final BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            final DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        ){
            
            new Thread(new Runnable(){
                @Override
                public void run() {
                    try {
                        String line;
                        while((line = in.readLine()) != null) {
                            System.out.printf("%s\n", line);
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }).start();
            
            String choice = "";
            while (!(choice.toUpperCase().equals("EXIT"))) {
                choice = scan.nextLine();
                out.writeBytes(choice + "\n");
            }
            

        } catch(UnknownHostException e) {
            System.out.println(e);
        } catch(IOException e) {
            System.out.println(e);
        }
  
    }

    public static void main(String[] args) {
        System.out.printf("Starting Client...");
        Client client = new Client("localhost", 5000);
    }
}