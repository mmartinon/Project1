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
            FindFile kkp = new FindFile();
            String filePath = kkp.processInput(null);
            int numClients = 2;

            File original = new File(filePath);
            long fileSize = original.length();

            long portionSize = fileSize / numClients;

            for (int i = 1; i <= numClients; i++)
            {
                String fileName = "client " + i + ".txt";
                File outputFile = new File(fileName);

                // Open buffered output stream
                try (FileOutputStream fos = new FileOutputStream(outputFile)) 
                {
                // Open buffered input stream
                    try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(original))) 
                    {
                        long bytesRead = 0;
                        byte[] buffer = new byte[1024]; // adjust buffer size as needed

                        // Read and write data in chunks
                        while (bytesRead < portionSize && bis.available() > 0) 
                        {
                            int bytes = bis.read(buffer);
                            fos.write(buffer, 0, bytes);
                            bytesRead += bytes;
                        }
                    }
                }

                out.println(fileName);
            }

            // out.println(filePath);
 
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