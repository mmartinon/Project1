import java.net.*;
import java.io.*;

public class Server 
{
    public static void main(String[] args) throws IOException 
    {    
        int portNumber = 4444;

        String ipAddress =  IPPrinter.printIP();

        // Socket socket = new Socket();
        // socket.connect(new InetSocketAddress("outlook.com", 80));
        // String ipAddress = socket.getLocalAddress().getHostAddress();
        // socket.close();
        // System.out.println("Server IP address: " + ipAddress);
 
        Socket[] clientSockets = new Socket[2];
        ServerSocket serverSocket = new ServerSocket(portNumber); 
        try {
            System.out.println("Waiting for client connection...");
            // Wait for 5 clients
            for (int i = 0; i < 2; i++)
            {
                clientSockets[i] = serverSocket.accept();
                System.out.println("Client connected at: " + clientSockets[i].getInetAddress() + ":" + clientSockets[i].getPort());
            }

            BufferedReader[] clientStreams = new BufferedReader[2];
            PrintWriter[] clientWriters = new PrintWriter[2];

            for (int j = 0; j < 2; j++) 
            {
                clientStreams[j] = new BufferedReader(new InputStreamReader(clientSockets[j].getInputStream()));
                clientWriters[j] = new PrintWriter(clientSockets[j].getOutputStream(), true);
            }
            
            long startTime = System.currentTimeMillis();

            try 
            ( 
                //initiate server socket on port
                //connect to client
                // Socket clientSocket = serverSocket.accept(); 

                //create output/input streams
                // PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); 

                FileInputStream fis = new FileInputStream(new File("words.txt"));
                // BufferedReader fileReader = new BufferedReader(new FileReader("words.txt"))
            ) 
            {
                // //send file path to client
                // //FindFile kkp = new FindFile();

                // byte[] fileBytes = fis.readAllBytes();
                // int numClients = clientSockets.length;
                // int bytesPerClient = fileBytes.length / numClients;

                // // Handle unequal file sizes
                // int remainingBytes = fileBytes.length % numClients;


                // // for (int i = 0, start = 0; i < numClients; i++) {
                // //     int end = start + bytesPerClient;
                
                // //     // Find the last space character within the substring
                // //     while (end < fileBytes.length && fileBytes[end] != ' ') 
                // //     {
                // //         end++;
                // //     }
                
                // //     // Ensure end doesn't go out of bounds
                // //     end = Math.min(end, fileBytes.length);  // Clamp end to string length
                
                // //     substrings[i] = new String(fileBytes, start, end - start);
                // //     clientWriters[i].println(substrings[i]);
                // //     System.out.println("hello");
                // //     start = end;
                // // }
                
                // // for (int i = 0; i < numClients; i++) {
                // //     int clientBytes = bytesPerClient;
                // //     if (i == numClients - 1) {
                // //         clientBytes += remainingBytes; // Send remaining bytes to the last client
                // //     }

                // //     byte[] clientData = new byte[clientBytes];
                // //     System.arraycopy(fileBytes, i * bytesPerClient, clientData, 0, clientBytes);

                // //     clientWriters[i].println(new String(clientData));
                // //     clientWriters[i].flush();
                // // }

                // for (int i = 0, start = 0; i < numClients; i++) {
                //     int end = start + bytesPerClient;

                //     // Find the last space character within the substring
                //     while (end < fileBytes.length && fileBytes[end] != ' ') 
                //     {
                //         end++;
                //     }

                //     end = Math.min(end, fileBytes.length);

                //     substrings[i] = new String(fileBytes, start, end - start);
                //     clientWriters[i].println(substrings[i]);
                //     clientWriters[i].flush();
                //     clientWriters[i].println("****");
                //     clientWriters[i].flush();

                //     start = end;
                byte[] fileBytes = fis.readAllBytes();
                int numClients = clientSockets.length;
                int bytesPerClient = fileBytes.length / numClients;
                String[] substrings = new String[numClients];


                // Distribute content equally with better handling of remaining bytes
                int start = 0;
                for (int i = 0; i < numClients; i++) {
                    int end = start + bytesPerClient;

                    // If it's the last client, include all remaining bytes
                    if (i == numClients - 1) {
                        end = fileBytes.length;
                    } else {
                        // Find the last space character within the substring (if possible)
                        while (end < fileBytes.length && fileBytes[end] != ' ') {
                            end++;
                        }

                        // Ensure end doesn't exceed the entire file length
                        end = Math.min(end, fileBytes.length);    
                    }

                    substrings[i] = new String(fileBytes, start, end - start);
                    System.out.println(substrings[i]);

                    start = end;
                }

                int[] clientWordCounts = new int[numClients];


                for (int i = 0; i < numClients; i++)
                {
                    clientWriters[i].println(substrings[i]);
                        // clientWriters[i].flush();
                        String count = clientStreams[i].readLine();
                     clientWordCounts[i] = Integer.parseInt(count);
                     System.out.println("Word count from client " + (i + 1) + ": " + count);
                        clientWriters[i].println("****");
                        clientWriters[i].flush();
                }

                // Read and print word counts from each client
                // System.out.println("before for");

                 // Read word counts from clients
                //  int[] clientWordCounts = new int[numClients];
                //  for (int i = 0; i < numClients; i++) {
                     
                //  }
 
                 // Calculate total word count
                 int totalWordCount = 0;
                 for (int count : clientWordCounts) {
                     totalWordCount += count;
                 }

                // System.out.println(clientStreams[0].readLine());

                // for (int i = 0; i < 2; i++) 
                // {
                //     String count = clientStreams[i].readLine();
                //     System.out.println("Word count from client " + i + ": " + count);
                // }

                System.out.println("Total word count: " + totalWordCount);

                long endTime = System.currentTimeMillis();
                long durationInMs = endTime - startTime;
                System.out.println("Execution time in milliseconds: " + durationInMs);
            } 
            catch (IOException e) 
            {
                System.out.println("Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
                System.out.println(e.getMessage());
            }
        }
        catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        } 
        finally 
        {
            if (serverSocket != null) {
                serverSocket.close();
            }
            for (Socket socket : clientSockets) {
                if (socket != null) {
                    socket.close();
                }
            }

        }
    }
}