import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Collections;
import java.io.*;
import java.util.Scanner;
import java.io.FileReader;

public class UserHistory extends DLB
{
    // -----NODE CLASS-----
    protected static class WordHist implements Comparable<WordHist>
    {
        protected String key;  // Field variable for character key at node
        protected int freq;     // Field variable for value at node containing the next character

        /**
         * No-arg constructor
         */
        public WordHist()
        {
            this.freq = 0;
        }

        /**
         * Constructor
         * @param k character of the key to be placed in node
         */
        public WordHist(String key)
        {
            this.key = key;
            this.freq = 0;
        }

        /**
         * Constructor
         * @param k character of the key to be placed in node. k should always
         *          be '^', but need parameter in case val is also a char, in
         *          which case we wouldn't know if the passed value is the
         *          character key or the value to be placed at '^'.
         * @param val value to be placed in node
         */
        public WordHist(String key, int freq)
        {
            this.key = key;
            this.freq = freq;
        }

        public int compareTo(WordHist b)
        {
            if(this.freq > b.freq) return 1;
            if(this.freq < b.freq) return -1;
            else                 return 0;
        }
    }

    public UserHistory()
    {
        super();
    }

    @Override
    public String toString()
    {
        LinkedList<WordHist> history = wordHistWithPrefix("");
        sortByFreq(history);
        String output = "";
        for(WordHist wh:history)
            output += wh.key + " " + wh.freq + "\n";

        return output;
    }

    public void incrementFrequency(String key)
    {
        int freq = 0;

        // Check if key exists
        if(contains(key))
            freq = get(key);

        freq++;
        put(key, freq);
    }

    public boolean saveHistory(String filename) throws IOException
    {
        String userHistory = this.toString();
        File newFile = new File(filename);
        if(!newFile.exists())
            newFile.createNewFile();

        FileWriter fwriter = null;
        try
        {
            fwriter = new FileWriter(filename, false);
            fwriter.write(userHistory);
        }
        catch(IOException e)
        {
            System.out.println("Error occurred while writing to file");
            System.out.println(e.toString());
            return false;
        }
        finally
        {
            fwriter.close();
        }

        return true;
    }

    public LinkedList<String> getUserPredictions(StringBuilder prefix, int cutoff)
    {
        LinkedList<WordHist> userWH = wordHistWithPrefix(prefix);
        sortByFreq(userWH);

        LinkedList<String> results = new LinkedList<>();
        for(int i=0; i<userWH.size() && i<cutoff; i++)
            results.add(0,userWH.get(i).key);

        return results;

    }

    public LinkedList<WordHist> wordHistWithPrefix(StringBuilder prefix)
    {
        return wordHistWithPrefix(prefix.toString());
    }

    public LinkedList<WordHist> wordHistWithPrefix(String prefix)
    {
        if(prefix == null)
            throw new IllegalArgumentException("Prefix must not be null");

        char c;
        Node n = this.rootNode;
        LinkedList<Node> nList = new LinkedList<>();
        int preIndex = 0;

        while((preIndex < prefix.length()) && (n != null))
        {
            nList = n.next;                 // Need to maintain reference to next level list for later
            c = prefix.charAt(preIndex);    // Get next character in prefix
            n = searchList(nList, c, 0);    // Search current level for character and return node
            preIndex++;
        }

        LinkedList<WordHist> results = new LinkedList<>();
        // If n is null, no keys were found that contain prefix --> Return null
        if(n == null)
            return results;

        nList = n.next;     // Get next level
        StringBuilder pre = new StringBuilder(prefix);      // Create new stringbuilder from prefix
        return collectWH(nList, pre, results, new ArrayList<>()); // Collect keys
    }

    protected LinkedList<WordHist> collectWH(LinkedList<Node> nList, StringBuilder partial, LinkedList<WordHist> results, ArrayList<StringBuilder> pList)
    {
        Node n;
        for(int i=0; i<nList.size(); i++)
        {
            pList.add(partial);     // Add partial key to list
            n = nList.get(i);       // Get next node in current level

            // If node isn't terminator
            if(n.k != '^')
            {
                pList.set(i, new StringBuilder(partial.toString() + n.k));  // Add node's character to partial
                collectWH(n.next, pList.get(i), results, pList);              // Continue adding to partial until '^' is found
            }
            // Otherwise, '^' is found --> partial is a full key and can be added to results list
            else
            {
                results.add(new WordHist(partial.toString(), n.val));
            }
        }
        return results;
    }

    private LinkedList<WordHist> sortByFreq(LinkedList<WordHist> whList)
    {
        Collections.sort(whList);
        return whList;
    }

}
