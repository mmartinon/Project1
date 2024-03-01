import java.net.*;
import java.io.*;

public class Server 
{
    public static void main(String[] args) throws IOException 
    {    
        //incorrect command line usage 
        // if (args.length != 1) 
        // {
        //     System.err.println("Usage: java KnockKnockServer");
        //     System.exit(1);
        // }
 
        int portNumber = 4444;
 
        try 
        ( 
            //initiate server socket on port
            ServerSocket serverSocket = new ServerSocket(portNumber); 
            //connect to client
            Socket clientSocket = serverSocket.accept(); 
            //create output/input streams
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); 
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) 
        {
            //send file path to client
            //FindFile kkp = new FindFile();
            String filePath = "words.txt";
            out.println(filePath);
 
            //read client input
            String inputLine;
            while ((inputLine = in.readLine()) != null) 
            {
                //print word count of file on server terminal
                System.out.println("The word count of " + filePath + " is " + inputLine + ".");
            }
         
            //end session between server/client
            System.exit(1);
        } 
        catch (IOException e) 
        {
            System.out.println("Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}