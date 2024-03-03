
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class WordCount
{
	/*
	 * @param path path to the file
	 * @return total number of words in the file references by path
	 * @throws FileNotFoundException if file doesn't exist
	 * @precondition file must be a valid text file
	 * @postcondition wordCount represents total number of words separated by space
	 */
	public static int wordCount(String path) throws FileNotFoundException
	{
		int wordCount = 0;
		wordCount += path.trim().split("\\s+").length;

	    return wordCount;
	}

	public static void main(String[] args)
	{
		try
		{
			System.out.println(wordCount("applications, preparations of mercury are usually selected, notably the\n" + //
								"ointments of the red oxide of mercury, ammoniated mercury,"));
		} 
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}