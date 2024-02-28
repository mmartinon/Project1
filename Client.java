


    // public static void main(String[] args) throws IOException {
        
    //     if (args.length != 2) {
    //         System.err.println(
    //             "Usage: java EchoClient <host name> <port number>");
    //         System.exit(1);
    //     }

    //     String hostName = args[0];
    //     int portNumber = Integer.parseInt(args[1]);

    //     try (
    //         Socket kkSocket = new Socket(hostName, portNumber);
    //         PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
    //         BufferedReader in = new BufferedReader(
    //             new InputStreamReader(kkSocket.getInputStream()));
    //     ) {
    //         BufferedReader stdIn =
    //             new BufferedReader(new InputStreamReader(System.in));
    //         String fromServer;
    //         String fromUser;

    //         while ((fromServer = in.readLine()) != null) {
    //             System.out.println("Server: " + fromServer);
    //             if (fromServer.equals("Bye."))
    //                 break;
                
    //             fromUser = stdIn.readLine();
    //             if (fromUser != null) {
    //                 System.out.println("Client: " + fromUser);
    //                 out.println(fromUser);
    //             }
    //         }
    //     } catch (UnknownHostException e) {
    //         System.err.println("Don't know about host " + hostName);
    //         System.exit(1);
    //     } catch (IOException e) {
    //         System.err.println("Couldn't get I/O for the connection to " +
    //             hostName);
    //         System.exit(1);
    //     }
    // }

import java.io.*;
import java.net.*;

public class Client 
{
    public static void main(String[] args) throws IOException 
    {
        if (args.length != 2) 
        {
            System.err.println("Usage: java KnockKnockClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try 
        (
            Socket kkSocket = new Socket(hostName, portNumber); //initiate client socket connected to server
            //create output/input streams
            PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()))
        ) 
        {
            String fromServer;

            while ((fromServer = in.readLine()) != null) 
            {
                System.out.println("Server: " + fromServer);
                if (fromServer.equals("Bye.")) {
                    break;
                }

                // Count words in the received file content
                int wordCount = WordCount.wordCount(fromServer);

                System.out.println("Client: Word count: " + wordCount);
                System.out.println("Bye.");
                out.println(String.valueOf(wordCount)); // Send word count to server
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
