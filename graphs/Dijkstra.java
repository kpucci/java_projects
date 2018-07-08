import java.util.Deque;
import java.util.ArrayDeque;

public class Dijkstra
{
    private Cable[] cableTo;           // Index = the last cable in the shortest path from source to the vertex at the index
    private double[] timeTo;           // Index = the time of the shortest path from source to the vertex at the index
    private IndexPQ<Double> timePQ;    // Queue of switch times

    public Dijkstra(NetworkGraph network, int source)
    {
        this.cableTo = new Cable[network.getNumSwitches()];             // Initialize cableTo array w/ size = number of switches
        this.timeTo = new double[network.getNumSwitches()];             // Initialize timeTo array w/ size = number of switches
        this.timePQ = new IndexPQ<Double>(network.getNumSwitches());    // Initialize timePQ w/ size = number of switches

        for(int v = 0; v < network.getNumSwitches(); v++)   // Initialize all times to infinity
            timeTo[v] = Double.POSITIVE_INFINITY;

        timeTo[source] = 0.0;           // Time to source is 0s
        timePQ.insert(source, 0.0);     // Add to priority queue
        while(!timePQ.isEmpty())
            relax(network, timePQ.delMin());
    }

    private void relax(NetworkGraph network, int sw)
    {
        for(Cable cable : network.getAdjacencyList(sw))   // For each cable in switch's adjacency list
        {
            int end = cable.getEnd();       // Get the endpoint for that cable

            if(timeTo[end] > (timeTo[sw] + cable.getTime()))
            {   // if time from source to endpoint is greater than time from source to vertex plus the current cable's time
                timeTo[end] = timeTo[sw] + cable.getTime();     // Update time from source to endpoint
                cableTo[end] = cable;                           // Update cable to
                if(timePQ.contains(end))                        // If the endpoint is already in the queue
                    timePQ.change(end, timeTo[end]);            //      Update the time at that position
                else
                    timePQ.insert(end, timeTo[end]);            // Otherwise, add it to the queue
            }
        }
    }

    public double timeTo(int v)
    {
        return timeTo[v];
    }

    public boolean hasPathTo(int v)
    {
        return timeTo[v] < Double.POSITIVE_INFINITY;
    }

    public boolean isGraphConnected()
    {
        for(int i=0; i < timeTo.length; i++)
            if(!hasPathTo(i))
                return false;
        return true;
    }

    public Iterable<Cable> pathTo(int v)
    {
        if(!hasPathTo(v))
            return null;

        Deque<Cable> path = new ArrayDeque<>();     // Use a deque so that it iterates through in reverse order
        Cable cable = cableTo[v];

        while(cable != null)
        {
            path.push(cable);
            cable = cableTo[cable.getStart()];
        }

        return path;
    }
}
