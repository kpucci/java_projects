public class Cable implements Comparable<Cable>
{
    public enum CableType
    {
        COPPER(230000000),      // m/s
        FIBER_OPTIC(200000000); // m/s

        private final int cableSpeed;

        CableType(int cableSpeed)
        {
            this.cableSpeed = cableSpeed;
        }

        public int getCableSpeed()
        {
            return this.cableSpeed;
        }
    }

    private final int start;
    private final int end;
    private final CableType type;
    private final int bandwidth;
    private final int length;
    private double flow;

    public Cable(int start, int end, String type, int bandwidth, int length)
    {
        this.start = start;
        this.end = end;
        if(type.equalsIgnoreCase("copper"))
            this.type = CableType.COPPER;
        else if(type.equalsIgnoreCase("optical"))
            this.type = CableType.FIBER_OPTIC;
        else
            this.type = null;
        this.bandwidth = bandwidth;
        this.length = length;
        this.flow = 0.0;
    }

    /**
     * Copy constructor
     * @param copy cable to copy
     */
    public Cable(Cable copy)
    {
        this.start = copy.start;
        this.end = copy.end;
        this.type = copy.type;
        this.bandwidth = copy.bandwidth;
        this.length = copy.length;
    }

    public double flow()
    {
        return flow;
    }

    public int other(int vertex)
    {
        if(vertex == start)
            return end;
        else if(vertex == end)
            return start;
        else
            throw new RuntimeException("Inconsistent edge");
    }

    public double residualCapacityTo(int vertex)
    {
        if(vertex == start)
            return flow;
        else if(vertex == end)
            return bandwidth - flow;
        else
            throw new RuntimeException("Inconsistent edge");
    }

    public void addResidualFlowTo(int vertex, double delta)
    {
        if(vertex == start)
            flow -= delta;
        else if(vertex == end)
            flow += delta;
        else
            throw new RuntimeException("Inconsistent edge");
    }

    public int getBandwidth()
    {
        return this.bandwidth;
    }

    public int getLength()
    {
        return this.length;
    }

    public CableType getType()
    {
        return this.type;
    }

    public int getSpeed()
    {
        return this.type.getCableSpeed();
    }

    public double getTime()
    {
        return (this.length)/((double)this.getSpeed());
    }

    public int getStart()
    {
        return this.start;
    }

    public int getEnd()
    {
        return this.end;
    }

    public int compareTo(Cable that)
    {
        if(this.getTime() < that.getTime())
            return -1;
        else if(this.getTime() > that.getTime())
            return 1;
        return 0;
    }

    public String toString()
    {
        return String.format("%d-%d", start, end);
    }

}
