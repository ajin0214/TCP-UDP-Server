import java.io.*;
import java.net.*;
import java.util.Objects;

public class EchoClient {
    public static void main(String[] args){
        String hostName = "localhost";
        int portNumber = 8080;
        if (args.length > 0) {
            try {
                portNumber = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Argument must be an integer");
                System.exit(1);
            }
        }
        try (Socket echoSocket = new Socket(hostName, portNumber);
             PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {

            String userInput;

            while (!Objects.equals(userInput = stdIn.readLine(), "exit")) {
                out.println(userInput);
                System.out.println("echo: " + in.readLine());
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }
    }
}
