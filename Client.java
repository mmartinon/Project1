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
        int totalWords = 0;

        try 
        (
            //initiate client socket connected to server
            Socket socket = new Socket(hostName, portNumber);
            //create output/input streams
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) 
        {
            // StringBuilder fromServer = new StringBuilder();
            char[] buffer = new char[1024];
            char[] temp = new char[1024];
            int bytesRead;

            while (true) 
            {
            
                String receivedString = in.readLine();

                if (receivedString != null && receivedString.equals("****")) {
                    break;
                }

                // Handle null case
                if (receivedString != null) {
                    wordCount = WordCount.wordCount(receivedString.trim());
                    totalWords += wordCount;
                    System.out.println(receivedString);
                    System.out.println("Word count of received substring: " + totalWords);
                    out.println(wordCount);
                } else {
                    System.err.println("Received null string from server");
                }
;
            }


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