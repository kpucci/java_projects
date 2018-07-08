import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Collections;

public class DLB
{
    // -----FIELD VARIABLES----- //
    protected Node rootNode = new Node(); // Created a root node instead of a root list for looping/recursion purposes
    protected int numKeys;      // Number of keys

    // -----NODE CLASS----- //
    protected static class Node implements Comparable<Node>
    {
        protected char k;  // Field variable for character key at node
        protected int val;     // Field variable for value at node
        protected LinkedList<Node> next = new LinkedList<>();   // Field variable for reference to linked list of nodes containing the next character

        /**
         * No-arg constructor
         */
        public Node()
        {
            this.val = 0;
        }

        /**
         * Constructor
         * @param k character of the key to be placed in node
         */
        public Node(char k)
        {
            this.k = k;
            this.val = 0;
        }

        /**
         * Constructor
         * @param k character of the key to be placed in node
         * @param val value to be placed in node
         */
        public Node(char k, int val)
        {
            this.k = '^';
            this.val = val;
        }


        public int compareTo(Node b)
        {
            if(this.val > b.val) return 1;
            if(this.val < b.val) return -1;
            else                 return 0;
        }
    }

    // -----CONSTRUCTORS-----

    /**
     * No-arg constructor
     */
    public DLB()
    {
        this.numKeys = 0;
    }

    // -----METHODS-----

    /**
     * Search current list for node with given character
     * @param nList list to search
     * @param c character to search for
     * @param index index in list to start search (used for recursion)
     * @return node with given character from given list, or null if not found
     */
    protected Node searchList(LinkedList<Node> nList, char c, int index)
    {
        // If not at end of list
        if(index < nList.size())
        {
            Node n = nList.get(index); // Get next node in list
            // If node's character isn't equal to character we're searching for
            if(n.k != c)
                return searchList(nList, c, ++index); // Search next node
            return n;
        }
        // Otherwise, return null
        else
            return null;
    }

    /**
     * Adds new branch to DLB starting at given index
     * @param nList branch to start from
     * @param key key to insert
     * @param index index of key to start adding at
     * @param val value to be placed in new key terminator node
     */
    protected void addNewBranch(LinkedList<Node> nList, String key, int index, int val)
    {
        LinkedList<Node> nextList = nList; // Set nextList to nList to start
        Node nextNode; // Declare a node object for next node in path

        // From given index to end of key
        for(int i=index; i<key.length(); i++)
        {
            nextNode = new Node(key.charAt(i)); // Create a new node with next character of key
            nextList.add(nextNode);             // Add node to list
            nextList = nextNode.next;           // Set nextList to the next level down
        }

        nextList.add(new Node('^', val));   // At end of key, add the terminator node with given value
        this.numKeys++;                     // Increment number of keys
    }

    /**
     * Puts a new key-value pair into the DLB
     * @param key key to insert
     * @param val value to insert
     */
    public void put(String key, int val)
    {
        if(key == null || key == "")
            throw new IllegalArgumentException("Key must not be null");
        // if(val == null)
        //     throw new IllegalArgumentException("Value must not be null");

        char c;
        Node n = this.rootNode;                         // Set first node to root node
        LinkedList<Node> nList = new LinkedList<>();    // Initialize a new linked list
        int index = 0;

        while((index < key.length()) && (n != null))
        {
            nList = n.next;                 // Need to maintain reference to list for later
            c = key.charAt(index);          // Get next character in key
            n = searchList(nList, c, 0);    // Get reference to node containing the character
            index++;
        }

        // If n = null, the key wasn't found and needs added
        if(n == null)
            addNewBranch(nList,key,--index,val);    // Add new branch starting at last index of key

        // If while loop terminates because index == key.length(), then the current key either already exists
        // or is a substring of another string
        else
        {
            Node x = searchList(n.next, '^', 0);    // Search for terminator node in the next level down

            // If the terminator node exists, then replace the value
            if(x != null)
                x.val = val;

            // Otherwise, add new terminator node with given value and increment number of keys
            else
            {
                n.next.add(new Node('^', val));
                this.numKeys++;
            }
        }
    }

    /**
     * Get value associated with given key
     * @param key key to search for
     * @return value associated with given key
     */
    public int get(String key)
    {
        if(key == null)
            throw new IllegalArgumentException("Key must not be null");

        // Node n = get(key+"^", 0, this.rootNode);
        key = key+"^";

        char c;
        Node n = this.rootNode;
        LinkedList<Node> nList = new LinkedList<>();
        int index = 0;

        while((index < key.length()) && (n != null))
        {
            nList = n.next;     // Need to maintain reference to list for later
            c = key.charAt(index);
            n = searchList(nList, c, 0);
            index++;
        }

        if(n == null)
            return 0;

        return n.val;
    }

    // BUG: This is erasing a value in another key for some reason
    /**
     * Helpter method that recursively searches for node containing last index of key
     * @param key key to search for
     * @param index current index in key
     * @param n current node
     */
    protected Node get(String key, int index, Node n)
    {
        if((index < key.length()) && (n != null))
            return get(key, ++index, searchList(n.next, key.charAt(index), 0));
        else
            return n;
    }

    /**
     * Checks whether the given key is contained in DLB
     * @param key key to search for
     * @return true if key exists in DLB; false otherwise
     */
    public boolean contains(String key)
    {
        if(key == null)
            throw new IllegalArgumentException("Key must not be null");

        key = key + "^";
        char c;
        Node n = this.rootNode;
        LinkedList<Node> nList = new LinkedList<>();
        int index = 0;
        while((index < key.length()) && (n != null))
        {
            nList = n.next;     // Need to maintain reference to list for later
            c = key.charAt(index);
            n = searchList(nList, c, 0);
            index++;
        }

        if(n == null)
            return false;
        if(n.val == 0)
            return false;
        return true;
    }

    /**
     * Get the number of keys contained in the DLB
     * @return number of keys
     */
    public int size()
    {
        return numKeys;
    }

    /**
     * Checks whether the DLB is empty
     * @return true if empty; false otherwise
     */
    public boolean isEmpty()
    {
        return size() == 0;
    }

    /**
     * Get a list of all keys in DLB that can be iterated
     * @return list of all keys
     */
    public LinkedList<String> keys()
    {
        return keysWithPrefix("");
    }

    /**
     * Get a list of all keys containing the given prefix
     * @param prefix the prefix to search for
     * @return list of keys with prefix
     */
    public LinkedList<String> keysWithPrefix(String prefix)
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

        LinkedList<String> results = new LinkedList<>();
        // If n is null, no keys were found that contain prefix --> Return null
        if(n == null)
            return results;

        nList = n.next;     // Get next level
        StringBuilder pre = new StringBuilder(prefix);      // Create new stringbuilder from prefix
        return collect(nList, pre, results, new ArrayList<>()); // Collect keys
    }

    /**
     * Get a list of keys containing the given prefix with length equal to cutoff
     * @param prefix the prefix to search for
     * @param cutoff cutoff value for length of prediction list
     * @return list of keys with prefix
     */
    public LinkedList<String> getPredictions(String prefix, int cutoff)
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

        LinkedList<String> results = new LinkedList<>();
        // If n is null, no keys were found that contain prefix --> Return null
        if(n == null)
            return results;

        nList = n.next;     // Get next level
        StringBuilder pre = new StringBuilder(prefix);      // Create new stringbuilder from prefix
        return collect(nList, pre, results, new ArrayList<>(), cutoff); // Collect keys
    }

    /**
     * Get a list of keys containing the given prefix with length equal to cutoff
     * @param prefix the prefix to search for
     * @param cutoff cutoff value for length of prediction list
     * @return list of keys with prefix
     */
    public LinkedList<String> getPredictions(StringBuilder prefix, int cutoff)
    {
        return getPredictions(prefix.toString(), cutoff);
    }

    /**
     * Get a list of keys containing the given prefix with length equal to cutoff
     * @param prefix the prefix to search for
     * @param cutoff cutoff value for length of prediction list
     * @return list of keys with prefix
     */
    public LinkedList<String> getPredictions(String prefix, LinkedList<String> repeats, int cutoff)
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

        LinkedList<String> results = new LinkedList<>();
        // If n is null, no keys were found that contain prefix --> Return null
        if(n == null)
            return results;

        nList = n.next;     // Get next level
        StringBuilder pre = new StringBuilder(prefix);      // Create new stringbuilder from prefix
        return collect(nList, pre, results, new ArrayList<>(), repeats, cutoff); // Collect keys
    }

    /**
     * Get a list of keys containing the given prefix with length equal to cutoff
     * @param prefix the prefix to search for
     * @param cutoff cutoff value for length of prediction list
     * @return list of keys with prefix
     */
    public LinkedList<String> getPredictions(StringBuilder prefix, LinkedList<String> repeats, int cutoff)
    {
        return getPredictions(prefix.toString(), repeats, cutoff);
    }

    /**
     * Helper method for keysWithPrefix that recursively collects keys with
     * given prefix
     * @param nList the list starting after the last character of prefix
     * @param partial a partial key
     * @param results list containing the found keys
     * @param pList list containing all strings that have been traversed so far
     */
    protected LinkedList<String> collect(LinkedList<Node> nList, StringBuilder partial, LinkedList<String> results, ArrayList<StringBuilder> pList)
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
                collect(n.next, pList.get(i), results, pList);              // Continue adding to partial until '^' is found
            }
            // Otherwise, '^' is found --> partial is a full key and can be added to results list
            else
            {
                results.add(partial.toString());
            }
        }
        return results;
    }

    protected LinkedList<String> collect(LinkedList<Node> nList, StringBuilder partial, LinkedList<String> results, ArrayList<StringBuilder> pList, int cutoff)
    {
        Node n;
        for(int i=0; i<nList.size() && results.size()<cutoff; i++)
        {
            pList.add(partial);     // Add partial key to list
            n = nList.get(i);       // Get next node in current level

            // If node isn't terminator
            if(n.k != '^')
            {
                pList.set(i, new StringBuilder(partial.toString() + n.k));  // Add node's character to partial
                collect(n.next, pList.get(i), results, pList, cutoff);              // Continue adding to partial until '^' is found
            }
            // Otherwise, '^' is found --> partial is a full key and can be added to results list
            else
            {
                results.add(partial.toString());
            }

        }
        return results;
    }

    protected LinkedList<String> collect(LinkedList<Node> nList, StringBuilder partial, LinkedList<String> results, ArrayList<StringBuilder> pList, LinkedList<String> repeats, int cutoff)
    {
        Node n;
        for(int i=0; results.size()<cutoff && i<nList.size(); i++)
        {
            pList.add(partial);     // Add partial key to list
            n = nList.get(i);       // Get next node in current level

            // If node isn't terminator
            if(n.k != '^')
            {
                pList.set(i, new StringBuilder(partial.toString() + n.k));  // Add node's character to partial
                collect(n.next, pList.get(i), results, pList, repeats, cutoff); // Continue adding to partial until '^' is found
            }
            // Otherwise, '^' is found --> partial is a full key and can be added to results list
            else
            {
                if(!repeats.contains(partial.toString()))
                {
                    results.add(partial.toString());
                }

            }

        }
        return results;
    }

    /**
     * Checks if node has a next level
     * @param n node to check
     * @return true if node has a valid next list; false otherwise
     */
    protected boolean hasNext(Node n)
    {
        return n.next.size() > 0;
    }

    /**
     * Delete the key-value pair at given key
     * @param key key to delete
     */
    public void delete(String key)
    {
        if(key == null)
            throw new IllegalArgumentException("Argument to delete() is null");

        String delkey = key + "^"; // Add terminator character to key

        char c;
        Node n = this.rootNode;
        LinkedList<Node> nList = new LinkedList<>();
        int keyIndex = 0;

        while((keyIndex < delkey.length()) && (n != null))
        {
            nList = n.next;                 // Get next level
            c = delkey.charAt(keyIndex);       // Get next character of key
            n = searchList(nList, c, 0);    // Search current level for character and return node
            keyIndex++;
        }

        if(n == null)
            System.out.println("The key was not found.");
        else
            nList.remove(n);

    }

}
