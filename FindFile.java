
// private static final int WAITING = 0;
// private static final int SENTKNOCKKNOCK = 1;
// private static final int SENTCLUE = 2;
// private static final int ANOTHER = 3;

// private static final int NUMJOKES = 5;

// private int state = WAITING;
// private int currentJoke = 0;

// private String[] clues = { "Turnip", "Little Old Lady", "Atch", "Who", "Who" };
// private String[] answers = { "Turnip the heat, it's cold in here!",
//                                 "I didn't know you could yodel!",
//                                 "Bless you!",
//                                 "Is there an owl in here?",
//                                 "Is there an echo in here?" };

// public String processInput(String input) {
//     String output = null;

//     if (state == WAITING) {
//         output = "Knock! Knock!";
//         state = SENTKNOCKKNOCK;
//     } else if (state == SENTKNOCKKNOCK) {
//         if (input.equalsIgnoreCase("Who's there?")) {
//             output = clues[currentJoke];
//             state = SENTCLUE;
//         } else {
//             output = "You're supposed to say \"Who's there?\"! " +
//             "Try again. Knock! Knock!";
//         }
//     } else if (state == SENTCLUE) {
//         if (input.equalsIgnoreCase(clues[currentJoke] + " who?")) {
//             output = answers[currentJoke] + " Want another? (y/n)";
//             state = ANOTHER;
//         } else {
//             output = "You're supposed to say \"" + 
//             clues[currentJoke] + 
//             " who?\"" + 
//             "! Try again. Knock! Knock!";
//             state = SENTKNOCKKNOCK;
//         }
//     } else if (state == ANOTHER) {
//         if (input.equalsIgnoreCase("y")) {
//             output = "Knock! Knock!";
//             if (currentJoke == (NUMJOKES - 1))
//                 currentJoke = 0;
//             else
//                 currentJoke++;
//             state = SENTKNOCKKNOCK;
//         } else {
//             output = "Bye.";
//             state = WAITING;
//         }
//     }
//     return output;
// }
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