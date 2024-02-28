import java.io.*;
import java.net.*;

public class Client 
{
    public static void main(String[] args) throws IOException 
    {
        //incorrect command line usage 
        if (args.length != 2) 
        {
            System.err.println("Usage: java KnockKnockClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try 
        (
            //initiate client socket connected to server
            Socket kkSocket = new Socket(hostName, portNumber); 
            //create output/input streams
            PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()))
        ) 
        {
            String fromServer;

            while ((fromServer = in.readLine()) != null) 
            {
                //print server's request
                System.out.println("Server: What is the word count of " + fromServer + "?");

                //count words in the file
                int wordCount = WordCount.wordCount(fromServer);

                //print word count of file on client terminal
                System.out.println("Client: The word count of " + fromServer + " is " + wordCount + ".");
                //send word count to server
                out.println(String.valueOf(wordCount));

                //end session between server/client
                System.exit(1);
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