import java.io.*;
import java.net.*;

public class EchoServer {
    public static void main(String[] args) {
        if (args.length > 1) {
            switch (args[0]) {
                case "tcp":
                    tcpServer(args[1]);
                    break;
                case "udp":
                    udpServer(args[1]);
                    break;
                default:
                    System.out.println("Invalid argument");
            }
        } else {
            System.out.println("No arguments");
        }
    }

    public static void tcpServer(String arg){
        System.out.println("TCP Server Test");
        int portNumber = Integer.parseInt(arg);
        System.out.println("port : " + portNumber);

        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println("Server is ready");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Connect completed");
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                System.out.println("Client>" + inputLine);
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

    public static void udpServer(String arg) {
        System.out.println("UDP Server Test");
        int portNumber = Integer.parseInt(arg);
        System.out.println("port : " + portNumber);

        try{
            DatagramSocket socket = new DatagramSocket(portNumber);
            while(true){
                byte[] buf = new byte[100];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);

                socket.receive(packet);
                String dataGot = new String(packet.getData(),0, packet.getLength());
                System.out.println("Client>" + dataGot);
                InetAddress address =packet.getAddress();
                int clientPort = packet.getPort();

                packet = new DatagramPacket(buf, buf.length, address,clientPort);

                socket.send(packet);

                if (dataGot.equals("exit")){
                    break;
                }
            }
        } catch (IOException ie) {
            System.err.println("Couldn't get I/O");
            System.err.println(ie.getMessage());
            System.exit(1);
        }

    }


}
