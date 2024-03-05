import java.net.*;
import java.io.*;

public class Server 
{
    public static void main(String[] args) throws IOException 
    {    
        int portNumber = 4444;

        String ipAddress =  IPPrinter.printIP();
 
        Socket[] clientSockets = new Socket[5];
        ServerSocket serverSocket = new ServerSocket(portNumber); 
        try {
            System.out.println("Waiting for client connection...");
            // Wait for 5 clients
            for (int i = 0; i < 5; i++)
            {
                clientSockets[i] = serverSocket.accept();
                System.out.println("Client connected at: " + clientSockets[i].getInetAddress() + ":" + clientSockets[i].getPort());
            }

            BufferedReader[] clientStreams = new BufferedReader[5];
            PrintWriter[] clientWriters = new PrintWriter[5];

            for (int j = 0; j < 5; j++) 
            {
                clientStreams[j] = new BufferedReader(new InputStreamReader(clientSockets[j].getInputStream()));
                clientWriters[j] = new PrintWriter(clientSockets[j].getOutputStream(), true);
            }
            
            long startTime = System.currentTimeMillis();

            try 
            ( 
            
                FileInputStream fis = new FileInputStream(new File("file2.txt"));
            ) 
            {
                
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

                int totalWordCount = 0;
                for (int i = 0; i < numClients; i++) {
                    clientWriters[i].println(substrings[i]);
                    clientWriters[i].flush();
                    String count;
                    while ((count = clientStreams[i].readLine()) != null) {
                        totalWordCount += Integer.parseInt(count);
                    System.out.println("Word count from client " + (i + 1) + ": " + count);
                    clientWriters[i].println("****");
                    clientWriters[i].flush();
                    }
                }

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