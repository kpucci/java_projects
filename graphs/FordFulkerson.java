public class FordFulkerson
{
    private boolean[] marked;
    private Cable[] edgeTo;
    private double value;

    public FordFulkerson(NetworkGraph G, int s, int t)
    {
        while (hasAugmentingPath(G, s, t))
        {
            double bottle = Double.POSITIVE_INFINITY;
            for (int v = t; v != s; v = edgeTo[v].other(v))
                bottle = Math.min(bottle, edgeTo[v].residualCapacityTo(v));

            for (int v = t; v != s; v = edgeTo[v].other(v))
                edgeTo[v].addResidualFlowTo(v, bottle);
            value += bottle;
        }
    }

    public double value()
    {
        return value;
    }

    public boolean inCut(int v)
    {
        return marked[v];
    }

    private boolean hasAugmentingPath(NetworkGraph G, int s, int t)
    {
        marked = new boolean[G.getNumSwitches()];
        edgeTo = new Cable[G.getNumSwitches()];
        Queue<Integer> q = new Queue<Integer>();
        marked[s] = true;
        q.enqueue(s);
        while (!q.isEmpty())
        {
            int v = q.dequeue();
            for(Cable c : G.getAdjacencyList(v))
            {
                int w = c.other(v);
                if (c.residualCapacityTo(w) > 0 && !marked[w])
                {
                    edgeTo[w] = c;
                    marked[w] = true;
                    q.enqueue(w);
                }
            }
        }

        return marked[t];
    }
}
