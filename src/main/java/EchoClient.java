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
            } catch (NumberFormatException ne) {
                System.err.println("Argument must be an integer");
                System.exit(1);
            }
        }
        try {
            Socket echoSocket = new Socket(hostName, portNumber);
            System.out.println("port : " + portNumber);
            PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String userInput;
            String input;
            while ((userInput = stdIn.readLine())!= null) {
                out.println(userInput);
                System.out.println("echo: " + (input = in.readLine()));
                if (input.equals("exit")){
                    break;
                }
            }

            echoSocket.close();
            in.close();
            out.close();

        } catch (UnknownHostException ue) {
            System.err.println("Don't know about host " + hostName);
            System.err.println(ue.getMessage());
            System.exit(1);
        } catch (IOException ie) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.err.println(ie.getMessage());
            System.exit(1);
        }
    }
}
