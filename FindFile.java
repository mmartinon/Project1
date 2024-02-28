import java.io.*;

public class FindFile 
{
    private static final String FILE_PATH = "words.txt";

    public String processInput(String input) 
    {
        String output = null;

        if (input == null) 
        {
            try 
            {
                //check if file exists - return path if yes, error message if no
                if (new File(FILE_PATH).exists()) 
                {
                    output = FILE_PATH; 
                } 
                else 
                {
                    output = "Error: File not found";
                }
            }
            
            //accounts for other possible errors
            catch (Exception e) 
            {
                output = "Error: Unexpected error";
            }
        }

        return output;
    }
}