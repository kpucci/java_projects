import java.util.LinkedList;
import java.util.ListIterator;

public class NetworkGraph
{
    private final int numSwitch;    // # of switches
    private int numCable;           // # of cables
    private LinkedList<Cable>[] adjLists;   // Adjacency lists

    /**
     * Network graph constructor
     * @param numSwitch number of switches in netword
     */
    public NetworkGraph(int numSwitch)
    {
        this.numSwitch = numSwitch;                     // Set number of switches
        this.numCable = 0;                              // Initialize number of cables to 0
        adjLists = (LinkedList<Cable>[]) new LinkedList[numSwitch]; // Initialize adjacency list array w/ size = # of switches
        for(int i=0; i < numSwitch; i++)             // For each index of adjacency list array
            adjLists[i] = new LinkedList<Cable>();              //      Create new reference to a list of cables
    }

    /**
     * Copy constructor
     * @param copy graph to copy
     */
    public NetworkGraph(NetworkGraph copy)
    {
        this.numSwitch = copy.numSwitch;
        this.numCable = copy.numCable;
        this.adjLists = (LinkedList<Cable>[]) new LinkedList[numSwitch];
        for(int i=0; i < this.numSwitch; i++)     // For each index in array of adjacency lists
        {
            adjLists[i] = new LinkedList<Cable>();
            for(Cable cable:copy.adjLists[i])       // For each cable in copy's adjacency list
                adjLists[i].add(new Cable(cable));   // Add a copy of the cable to list
        }
    }

    /**
     * Get the number of switches in the network
     * @return number of switches
     */
    public int getNumSwitches()
    {
        return this.numSwitch;
    }

    /**
     * Get the number of cables in the network
     * @return number of cables
     */
    public int getNumCables()
    {
        return this.numCable;
    }

    /**
     * Add a cable to the network
     * @param cable cable to add
     */
    public void addCable(Cable cable)
    {
        adjLists[cable.getStart()].add(cable);   // Add cable to list at the index of the cable's "from" vertex
        numCable++;                              // Increment number of cables
    }

    /**
     * Remove a cable from the network
     * @param cable cable to remove
     */
    // public void removeCable(Cable cable)
    // {
    //     adjLists[cable.getStart()].remove(cable);
    // }

    /**
     * Remove a cable from the network
     * @param start start vertex
     * @param end   end vertex
     */
    public Cable removeCable(int start, int end)
    {
        ListIterator<Cable> cableIterator = adjLists[start].listIterator(0);
        while(cableIterator.hasNext())
        {
            Cable next = cableIterator.next();
            if(next.getEnd() == end)
            {
                cableIterator.remove();
                return next;
            }
        }

        return null;
    }

    /**
     * Get iterable adjacency list at the specified switch
     * @param  sw switch
     * @return    iterable list
     */
    public Iterable<Cable> getAdjacencyList(int sw)
    {
        return adjLists[sw];
    }

    public Iterable<Cable> cables()
    {
        LinkedList<Cable> cables = new LinkedList<Cable>();
        for(int v=0; v < this.numSwitch; v++)
            for(Cable cable: this.adjLists[v])
                cables.add(cable);
        return cables;
    }

    public Cable getCable(int start, int end)
    {
        for(Cable c : this.adjLists[start])
            if(c.getEnd() == end)
                return c;
        return null;
    }
}
