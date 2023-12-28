import java.io.*;
import java.net.*;

public class EchoClient {
    public static void main(String[] args) {
        if (args.length > 1) {
            switch (args[0]) {
                case "tcp":
                    tcpClient(args[1]);
                    break;
                case "udp":
                    udpClient(args[1]);
                    break;
                default:
                    System.out.println("Invalid argument");
            }
        } else {
            System.out.println("No arguments");
        }
    }

    public static void tcpClient(String arg){
        System.out.println("TCP Client Test");
        String hostName = "localhost";
        int portNumber = Integer.parseInt(arg);

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
                System.out.println("echo : " + (input = in.readLine()));
                if (input.equals("exit")){
                    break;
                }
            }

            echoSocket.close();
            in.close();
            out.close();
            stdIn.close();

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

    public static void udpClient(String arg){
        System.out.println("UDP Client Test");
        int portNumber = Integer.parseInt(arg);
        System.out.println("port : " + portNumber);

        try {
            InetAddress address = InetAddress.getByName("localhost");
            DatagramSocket socket = new DatagramSocket();

            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String userInput;

            while((userInput = stdIn.readLine())!= null){
                byte[] buf = userInput.getBytes();
                DatagramPacket packet = new DatagramPacket(buf, buf.length, address, portNumber);

                socket.send(packet);

                packet = new DatagramPacket(buf,buf.length);
                socket.receive(packet);

                String echo = new String(packet.getData(),0, packet.getLength());
                System.out.println("echo : " + echo);
                if (userInput.equals("exit")){
                    break;
                }

            }


        } catch (IOException ie) {
            throw new RuntimeException(ie);
        }
    }
}
