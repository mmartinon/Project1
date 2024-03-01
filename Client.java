import java.io.*;
import java.net.*;

public class Client 
{
    public static void main(String[] args) throws IOException 
    {
        //incorrect command line usage 
        if (args.length != 1) 
        {
            System.err.println("Usage: java Client.java <host name>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = 4444;
        int wordCount = 0;

        try 
        (
            //initiate client socket connected to server
            Socket socket = new Socket(hostName, portNumber);
            //create output/input streams
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) 
        {
            StringBuilder fromServer = new StringBuilder();
            char[] buffer = new char[1024];
            char[] temp = new char[1024];
            int bytesRead;

            while (true) 
            {
                String receivedString = in.readLine();

                if (receivedString != null && receivedString.equals("****\n")) {
                    break;
                }

                // Handle null case
                if (receivedString != null) {
                    wordCount = WordCount.wordCount(receivedString.trim());
                    System.out.println("Client: Word count of received substring: " + wordCount);
                    out.println(wordCount);
                } else {
                    System.err.println("Received null string from server");
                }

                // Calculate word count for the received substring
                wordCount = WordCount.wordCount(receivedString);

                // Print word count on client terminal
                System.out.println("Client: Word count of received substring: " + wordCount);

                // Send word count to server
                out.println(wordCount);

                //DR J EXPLANATION THAT YOU DID 
                // bytesRead = in.read(buffer);
                // if (bytesRead == -1) {
                //     break;
                // }

                // String receivedString = new String(buffer, 0, bytesRead);
                // if (!receivedString.equals("****")) {
                //     fromServer.append(receivedString);
                // } else {
                //     break;
                // }
                // append the received data to the StringBuilder
                //in.read(temp);

                // if (String.valueOf(temp) != "****")
                // {
                //     //append data to buffer
                //     fromServer.append(temp, 0, )
                // }
                // fromServer.append(buffer, 0, bytesRead);
            }

            //int totalBytes = 5;
            
            // while(bytesRead <= totalBytes)
            // {

            // }


            // print the server's request
            System.out.println("Server: What is the word count of " + fromServer + "?");

            // count words in the entire string
            wordCount = WordCount.wordCount(fromServer.toString());
            //out.println(wordCount + "\n");
            
            System.out.println(socket.getPort());
            out.println("client message");
            out.flush();

            // print word count of the string on the client terminal
            System.out.println("Client: The word count of " + fromServer + " is " + wordCount + ".");
            
            // String fromServer;

            // while ((fromServer = in.readLine()) != null) 
            // {
            //     //print server's request
            //     System.out.println("Server: What is the word count of " + fromServer + "?");

            //     //count words in the line
            //     wordCount += WordCount.wordCount(fromServer);
            //     out.println(String.valueOf(wordCount));

            //     //print word count of file on client terminal
            //     System.out.println("Client: The word count of " + fromServer + " is " + wordCount + ".");                

            //     //end session between server/client
            //     // System.exit(1);
            // }

            // out.println(String.valueOf(wordCount));

            System.exit(1);
        } 
        catch (UnknownHostException e) 
        {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } 
        catch (IOException e) 
        {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }
    }
}