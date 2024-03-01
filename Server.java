import java.net.*;
import java.util.ArrayList;
import java.util.List;
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
        for (int i = 0; i < 1; i++) 
        {
            System.out.println("Waiting for client connection...");
            clientSockets[i] = serverSocket.accept();
            System.out.println("Client connected at: " + clientSockets[i].getInetAddress() + ":" + clientSockets[i].getPort());
            
        }

        BufferedReader[] clientStreams = new BufferedReader[5];

        for (int j = 0; j < 1; j++) 
        {
            clientStreams[j] = new BufferedReader(new InputStreamReader(clientSockets[j].getInputStream()));
        }

        PrintWriter[] clientWriters = new PrintWriter[5];
        for (int j = 0; j < 1; j++) 
        {
            clientWriters[j] = new PrintWriter(clientSockets[j].getOutputStream(), true);
        }

        try ( 
            FileInputStream fis = new FileInputStream(new File("words.txt"));
            BufferedReader fileReader = new BufferedReader(new FileReader("words.txt"))
        ) 
        {
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = fileReader.readLine()) != null) {
                lines.add(line);
            }

            int numSublists = 5;
            int linesPerSublist = lines.size() / numSublists;

            List<List<String>> sublists = new ArrayList<>();
            for (int i = 0; i < numSublists; i++) {
                int start = i * linesPerSublist;
                int end = (i + 1) * linesPerSublist;
                if (i == numSublists - 1) {
                    // Last sublist may have fewer lines if the total number of lines is not divisible evenly
                    end = lines.size();
                }
                sublists.add(lines.subList(start, end));

                // Send each sublist to the client
                String sublistString = String.join(System.lineSeparator(), sublists.get(i));
                clientWriters[i].println(sublistString);
            }
        } 

        // try 
        // ( 
            //initiate server socket on port
            //connect to client
            // Socket clientSocket = serverSocket.accept(); 

            //create output/input streams
            // PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); 
        //     FileInputStream fis = new FileInputStream(new File("words.txt"));
        //     BufferedReader fileReader = new BufferedReader(new FileReader("words.txt"))
        // ) 
        // {
            //send file path to client
            //FindFile kkp = new FindFile();

            // byte[] fileBytes = fis.readAllBytes();
            // int numSubstrings = 1;
            // int approximateSubstringLength = fileBytes.length / numSubstrings;

            // String[] substrings = new String[numSubstrings];
            // for (int i = 0, start = 0; i < numSubstrings; i++) 
            // {
            //     int end = Math.min(start + approximateSubstringLength, fileBytes.length);
            //     substrings[i] = new String(fileBytes, start, end - start);
            //     clientWriters[i].println(substrings[i]);
            //     start = end;
            // }

            // Output the substrings
            // for (String substring : substrings) {
            //     System.out.println(substring);
            // }

            // String line;
            // int clientIndex = 0;

            // while ((line = fileReader.readLine()) != null) 
            // {
            //     clientWriters[clientIndex].println(line); // Send a line to the current client

            //     clientIndex = (clientIndex + 1) % 1; // Cycle to the next client
            // }

            // Indicate end of file to all clients
            // for (PrintWriter writer : clientWriters) 
            // {
            //     writer.println("EOF");
            // }

            // Read and print word counts from each client
            for (int i = 0; i < 1; i++) 
            {
                String count = clientStreams[i].readLine();
                System.out.println("Word count from client " + i + ": " + count);
            }


            // String filePath = "words.txt";
            // out.println(filePath);
 
            //end session between server/client
            // System.exit(1);
        } 
        catch (IOException e) 
        {
            System.out.println("Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}