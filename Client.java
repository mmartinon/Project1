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
            String fromServer;

            while ((fromServer = in.readLine()) != null) 
            {
                //print server's request
                System.out.println("Server: What is the word count of " + fromServer + "?");

                //count words in the line
                wordCount += WordCount.wordCount(fromServer);
                out.println(String.valueOf(wordCount));

                //print word count of file on client terminal
                System.out.println("Client: The word count of " + fromServer + " is " + wordCount + ".");                

                //end session between server/client
                // System.exit(1);
            }

            out.println(String.valueOf(wordCount));

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