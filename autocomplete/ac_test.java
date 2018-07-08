import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.io.*;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Collections;

public class ac_test
{
    public static void main(String[] args)
    {
        // Create dictionary and user history DLBs
        DLB dictionary = createDictionary("dictionary.txt");
        UserHistory userHist = loadUserHistory("user_history.txt");

        // Initialize lists for dictionary and user history keys
        LinkedList<String> dictKeys = new LinkedList<>();
        LinkedList<String> userKeys = new LinkedList<>();

        // Set variables for times
        long start, elapsed;
        double timeInSeconds;
        ArrayList<Double> times = new ArrayList<>();

        StringBuilder input;    // Builder for user input
        String next;            // String to hold nextLine
        int selection;          // Variable for word selection value
        boolean exit = false;   // Exit program when this is true

        Scanner keyboard = new Scanner(System.in);

        do{
            keyboard.reset(); // Reset scanner for each new word

            System.out.println("Enter your first character: ");
            next = keyboard.nextLine();
            if(next.equals("$"))
            {
                System.out.println("You cannot enter a null word");
                break;
            }
            if(next.equals("!"))
            {
                exit = true;
                break;
            }

            input = new StringBuilder(next);

            start = System.nanoTime();      // Start timer
            userKeys = userHist.getUserPredictions(input,5);    // Get up to 5 matching user history keys
            dictKeys = dictionary.getPredictions(input,userKeys,5-userKeys.size()); // Fill remainder with dictionary words
            elapsed = System.nanoTime() - start;      // End timer
            timeInSeconds = elapsed*(1.0e-9);
            times.add(timeInSeconds);                 // Add to list for average later

            userKeys.addAll(dictKeys);                // Add all keys to same list, with user keys in front
            showPredictions(timeInSeconds,userKeys);

            while(true)
            {
                //Get user input
                System.out.println("Enter your next character: ");

                // See if user entered a number
                try{
                    selection = keyboard.nextInt();

                    // If user selected a valid prediction
                    if(selectedPredict(selection,userKeys.size()))
                    {
                        String compWord = userKeys.get(selection-1);    // Get word from list of keys
                        userHist.incrementFrequency(compWord);          // Add word to user history
                        System.out.println("\nWORD COMPLETED:\t"+compWord+"\n");
                        keyboard.nextLine();  // Absorb \n
                        break;
                    }
                    else
                        next = String.valueOf(selection);   // Otherwise, set number as next character so it could be added to user history
                }
                catch(InputMismatchException e){
                    next = keyboard.nextLine();     // Get next character

                    // If $, add current word to user history
                    if(next.equals("$"))
                    {
                        userHist.incrementFrequency(input.toString());
                        System.out.println();
                        break;
                    }

                    // If !, exit program
                    if(next.equals("!"))
                    {
                        exit = true;
                        break;
                    }
                }

                input.append(next);     // Add character to word

                start = System.nanoTime();      // Start timer
                userKeys = userHist.getUserPredictions(input,5);    // Get up to 5 matching user history keys
                dictKeys = dictionary.getPredictions(input,userKeys,5-userKeys.size());     // Fill remainder with dict keys
                elapsed = System.nanoTime() - start;    // End timer
                timeInSeconds = elapsed*(1.0e-9);       // Time in seconds
                times.add(timeInSeconds);

                userKeys.addAll(dictKeys);      // Combine all keys, with user predictions in front

                if(userKeys.isEmpty())
                    System.out.println("\nThere are no predictions for the current word.\n");
                else
                    showPredictions(timeInSeconds,userKeys);

            }
        }while(!exit);

        // Save user history
        try
        {
            userHist.saveHistory("user_history.txt");
        }
        catch(IOException e)
        {
            System.out.println("Error saving user history");
            System.out.println(e.toString());
        }

        // Get average time
        double sum = 0.0;
        for(double time:times)
            sum += time;
        double avg_time = sum/times.size();

        System.out.printf("\nAverage time: %.6f\n", avg_time);
        System.out.println("Bye!");

    }

    /* Check if user selected a valid prediction */
    public static boolean selectedPredict(int input, int numKeys)
	{
        return (input >= 1 && input <= numKeys);
	}

    /* Print predictions to console */
    private static void showPredictions(double time, List<String> keys)
    {
        // Display time
        System.out.printf("\n(%.6f s)\n", time);

        // Display predictions
        String output = "Predictions:\n";
        for(int i=0; i<keys.size(); i++)
            output += "(" + (i+1) + ") " + keys.get(i) + "    ";
        output += "\n";
        System.out.println(output);
    }

    /* Create dictionary DLB from input file */
    private static DLB createDictionary(String file)
    {
        DLB dict = new DLB();
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while((line = reader.readLine()) != null)
            {
                dict.put(line,0);
            }
            reader.close();
            return dict;
        }
        catch(Exception e)
        {
            System.out.println("A problem occurred trying to read the file");
            return null;
        }
    }

    /* Create user history DLB from history file */
    private static UserHistory loadUserHistory(String file)
    {
        String nextDataLine = null;
        String[] wordData;
        int freq;
        String key;

        UserHistory userHist = new UserHistory();

        try
        {
            File historyFile = new File(file);
            Scanner inputFile = new Scanner(historyFile);

            //While the file still has data available to read
            while(inputFile.hasNext())
            {
                nextDataLine = inputFile.nextLine(); //Get the next line
                wordData = nextDataLine.split(" "); //Split the line at the spaces
                //Example of what's contained in each String array: {"abc", 5}

                // Get key-value pair
                key = wordData[0];
                freq = Integer.parseInt(wordData[1]);

                // Add to user history DLB
                userHist.put(key,freq);
            }
            inputFile.close();
        }
        catch(FileNotFoundException e)
        {
            System.out.println(file + " not found. Created an empty user history structure");
        }

        return userHist;
    }
}
