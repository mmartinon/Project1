import java.net.*;
import java.io.*;

public class Server 
{
    public static void main(String[] args) throws IOException 
    {    
        int portNumber = 4444;

        String ipAddres =  IPPrinter.printIP();

        // Socket socket = new Socket();
        // socket.connect(new InetSocketAddress("outlook.com", 80));
        // String ipAddress = socket.getLocalAddress().getHostAddress();
        // socket.close();
        // System.out.println("Server IP address: " + ipAddress);
 
        Socket[] clientSockets = new Socket[5];
        ServerSocket serverSocket = new ServerSocket(portNumber); 

        // Wait for 5 clients
        for (int i = 0; i < 2; i++)
        {
            System.out.println("Waiting for client connection...");
            clientSockets[i] = serverSocket.accept();
            System.out.println("Client connected at: " + clientSockets[i].getInetAddress() + ":" + clientSockets[i].getPort());
            
        }

        BufferedReader[] clientStreams = new BufferedReader[5];

        for (int j = 0; j < 2; j++) 
        {
            clientStreams[j] = new BufferedReader(new InputStreamReader(clientSockets[j].getInputStream()));
        }

        PrintWriter[] clientWriters = new PrintWriter[5];
        for (int j = 0; j < 2; j++) 
        {
            clientWriters[j] = new PrintWriter(clientSockets[j].getOutputStream(), true);
        }

        try 
        ( 
            //initiate server socket on port
            //connect to client
            // Socket clientSocket = serverSocket.accept(); 

            //create output/input streams
            // PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); 
            FileInputStream fis = new FileInputStream(new File("words.txt"));
            BufferedReader fileReader = new BufferedReader(new FileReader("words.txt"))
        ) 
        {
            //send file path to client
            //FindFile kkp = new FindFile();

            byte[] fileBytes = fis.readAllBytes();
            int numSubstrings = 2;
            int approximateSubstringLength = fileBytes.length / numSubstrings;

            String[] substrings = new String[numSubstrings];

            for (int i = 0, start = 0; i < numSubstrings; i++) {
                int end = start + approximateSubstringLength;
            
                // Find the last space character within the substring
                while (end < fileBytes.length && fileBytes[end] != ' ') 
                {
                    end++;
                }
            
                // Ensure end doesn't go out of bounds
                end = Math.min(end, fileBytes.length);  // Clamp end to string length
            
                substrings[i] = new String(fileBytes, start, end - start);
                clientWriters[i].println(substrings[i]);
                System.out.println("hello");
                start = end;
            }
            
            // for (int i = 0, start = 0; i < numSubstrings; i++) {
            //     int end = start + approximateSubstringLength;

            //     // Find the last space character within the substring
            //     while (end < fileBytes.length && fileBytes[end] != ' ') 
            //     {
            //         end++;
            //     }

            //     substrings[i] = new String(fileBytes, start, end - start);
            //     clientWriters[i].println(substrings[i]);
            //     start = end;
            // }

            // Read and print word counts from each client
            for (int i = 0; i < 2; i++) 
            {
                String count = clientStreams[i].readLine();
                System.out.println("Word count from client " + i + ": " + count);
            }

        } 
        catch (IOException e) 
        {
            System.out.println("Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }

    private static int findSpaceCharacter(byte[] bytes, int endIndex) {
        for (int i = endIndex; i >= 0; i--) {
            if (bytes[i] == ' ') {
                return i;
            }
        }
        return endIndex; // If no space character is found, return the original endIndex
    }
}