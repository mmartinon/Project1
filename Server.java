import java.net.*;
import java.io.*;

public class Server 
{
    public static void main(String[] args) throws IOException 
    {    
        //incorrect command line usage 
        if (args.length != 1) 
        {
            System.err.println("Usage: java KnockKnockServer <port number>");
            System.exit(1);
        }
 
        int portNumber = Integer.parseInt(args[0]);
 
        try 
        ( 
            ServerSocket serverSocket = new ServerSocket(portNumber); //initiate server socket on port
            Socket clientSocket = serverSocket.accept(); //connect to client
            //create output/input streams
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); 
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) 
        {
            //send file path to client
            FindFile kkp = new FindFile();
            String filePath = kkp.processInput(null);
            out.println(filePath);
 
            //read client input until "Bye."
            String inputLine;
            while ((inputLine = in.readLine()) != null) 
            {
                //print word count of file on server terminal
                System.out.println(inputLine);

                // if (inputLine.equals("Bye."))
                // {
                //     break;
                // }
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