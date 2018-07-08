import java.util.List;
import java.util.LinkedList;

public class TestClass<Value>
{
    // Field variables
    private Node rootNode = new Node();
    private int numKeys;      // Number of keys

    // Node class
    private static class Node
    {
        private char k;  // Field variable for character key at node
        private Object val;     // Field variable for value at node
        private LinkedList<Node> next = new LinkedList<>();   // Field variable for reference to linked list of nodes containing the next character

        public Node()
        {
            this.val = null;
        }

        public Node(char k)
        {
            this.k = k;
            this.val = null;
        }

        public Node(char k, Object val)
        {
            this.k = k;
            this.val = val;
        }
    }

    // Constructors
    // TEMP
    public TestClass()
    {

    }

    private Node searchList(LinkedList<Node> nList, char c, int index)
    {
        if(index < nList.size())
        {
            Node n = nList.get(index);
            if(n.k != c)
                return searchList(nList, c, ++index);
            return n;
        }
        else
            return null;
    }

    private void addNewBranch(LinkedList<Node> list, String key, int index, Value val)
    {
        LinkedList<Node> n = list;
        Node newNode;
        for(int i=index; i<key.length(); i++)
        {
            newNode = new Node(key.charAt(i));
            n.add(newNode);
            n = newNode.next;
        }
        n.add(new Node('^', val));
    }

    public void put(String key, Value val)
    {
        if(key == null)
            throw new IllegalArgumentException("Key must not be null");
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

        // If while loop terminates because index == key.length(), then the
        // current key is a substring of another string --> need to add node to
        // n.next with k = '^' and val = val
        if(index == key.length())
            n.next.add(new Node('^', val));
        // Otherwise, the key wasn't found and needs added
        if(n == null)
            addNewBranch(nList,key,--index,val);
    }

    public Value get(String key)
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
            return null;

        return (Value) n.val;
    }

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
        if(n.val == null)
            return false;
        return true;
    }

    public static void main(String[] args)
    {
        TestClass testDLB = new TestClass();
        testDLB.put("KAT",25);
        Node testFind = testDLB.searchList(testDLB.rootNode.next, 'K', 0);
        if(testFind != null)
            System.out.println("The character was found: " + testFind.k);
        else
            System.out.println("The character was not found.");

        Node testFind2 = testDLB.searchList(testFind.next, 'A', 0);
        if(testFind2 != null)
            System.out.println("The character was found: " + testFind2.k);
        else
            System.out.println("The character was not found.");

        Node testFind3 = testDLB.searchList(testFind2.next, 'T', 0);
        if(testFind3 != null)
            System.out.println("The character was found: " + testFind3.k);
        else
            System.out.println("The character was not found.");

        Node testFind4 = testDLB.searchList(testFind3.next, '^', 0);
        if(testFind4 != null)
        {
            System.out.println("The character was found: " + testFind4.k);
            System.out.println("The value of KAT: " + testFind4.val);
        }
        else
            System.out.println("The character was not found.");

        testDLB.put("KATIE", 27);
        Node testFind5 = testDLB.searchList(testDLB.rootNode.next, 'K', 0);
        if(testFind != null)
            System.out.println("The character was found: " + testFind5.k);
        else
            System.out.println("The character was not found.");

        Node testFind6 = testDLB.searchList(testFind5.next, 'A', 0);
        if(testFind6 != null)
            System.out.println("The character was found: " + testFind6.k);
        else
            System.out.println("The character was not found.");

        Node testFind7 = testDLB.searchList(testFind6.next, 'T', 0);
        if(testFind7 != null)
            System.out.println("The character was found: " + testFind7.k);
        else
            System.out.println("The character was not found.");

        Node testFind8 = testDLB.searchList(testFind7.next, 'I', 0);
        if(testFind8 != null)
            System.out.println("The character was found: " + testFind8.k);
        else
            System.out.println("The character was not found.");

        Node testFind9 = testDLB.searchList(testFind8.next, 'E', 0);
        if(testFind9 != null)
            System.out.println("The character was found: " + testFind9.k);
        else
            System.out.println("The character was not found.");

        Node testFind10 = testDLB.searchList(testFind9.next, '^', 0);
        if(testFind10 != null)
        {
            System.out.println("The character was found: " + testFind10.k);
            System.out.println("The value of KATIE: " + testFind10.val);
        }
        else
            System.out.println("The character was not found.");

        System.out.println("The value at KAT: " + testDLB.get("KAT"));
        System.out.println("The collection contains KAT: " + testDLB.contains("KAT"));
        System.out.println("The collection contains KATIE: " + testDLB.contains("KATIE"));
        System.out.println("The collection contains KATI: " + testDLB.contains("KATI"));
        System.out.println("The collection contains VIN: " + testDLB.contains("VIN"));
    }
}
