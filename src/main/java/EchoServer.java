import java.io.*;
import java.net.*;

public class EchoServer {
    public static void main(String[] args){
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
            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println("서버준비완료");
            Socket clientSocket = serverSocket.accept();
            System.out.println("클라이언트연결완료");
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                System.out.println("Client Receive : " + inputLine);
                out.println(inputLine);
                if (inputLine.equals("exit")){
                    break;
                }
            }

            clientSocket.close();
            serverSocket.close();
            out.close();
            in.close();

        } catch (IOException ie){
            System.err.println("Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
            System.err.println(ie.getMessage());
        }
    }
}
