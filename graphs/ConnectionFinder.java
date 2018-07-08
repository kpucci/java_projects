public class ConnectionFinder
{
    private boolean[] marked;
    private int[] id;
    private int count;

    public ConnectionFinder(NetworkGraph G)
    {
        marked = new boolean[G.getNumSwitches()];
        id = new int[G.getNumSwitches()];
        for(int s = 0; s < G.getNumSwitches(); s++)
        {
            if(!marked[s])
            {
                dfs(G, s);
                count++;
            }
        }
    }

    private void dfs(NetworkGraph G, int v)
    {
        marked[v] = true;
        id[v] = count;
        for(Cable c: G.getAdjacencyList(v))
            if(!marked[c.getEnd()])
                dfs(G, c.getEnd());
    }

    public boolean connected(int v, int w)
    {
        return id[v] == id[w];
    }

    public int id(int v)
    {
        return id[v];
    }

    public int count()
    {
        return count;
    }
}
