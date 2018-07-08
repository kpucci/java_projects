public class KruskalMST
{
    private Queue<Cable> mst;

    public KruskalMST(NetworkGraph G)
    {
        mst = new Queue<Cable>();
        MinPQ<Cable> pq = new MinPQ<Cable>(G.getNumCables());
        for(Cable c:G.cables())
            pq.insert(c);

        UF uf = new UF(G.getNumSwitches());
        while(!pq.isEmpty() && mst.size() < G.getNumSwitches()-1)
        {
            Cable c = pq.delMin();
            int v = c.getStart(), w = c.getEnd();
            if(uf.connected(v, w))
                continue;
            uf.union(v, w);
            mst.enqueue(c);
        }
    }

    public Iterable<Cable> cables()
    {
        return mst;
    }

    public double weight()
    {
        double weight = 0;

        for(Cable cable : cables())
        {
            weight += cable.getTime();
        }

        return weight;
    }

}
