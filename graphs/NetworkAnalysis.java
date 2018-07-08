import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.ArrayList;

public class NetworkAnalysis
{
    private static NetworkGraph graph;

    /**
     * Configure the graph with the input file
     * @param filename name of configuration file
     * @return         true if successfully configured, false otherwise
     */
    private static boolean config(String filename)
    {
        BufferedReader reader;
        try{
            reader = new BufferedReader(new FileReader(filename));
            int numV = Integer.parseInt(reader.readLine()); // Get number of vertices from file
            graph = new NetworkGraph(numV);                 // Initialize graph

            String line, type = "";
            int start, end, bandwidth, length;

            while((line = reader.readLine()) != null)
            {
                if(!line.trim().equals(""))
                {
                    String[] tokens = line.split(" ");
                    start = Integer.parseInt(tokens[0]);        // Start vertex
                    end = Integer.parseInt(tokens[1]);          // End verted
                    type = tokens[2];                           // Cable type
                    bandwidth = Integer.parseInt(tokens[3]);    // Bandwidth
                    length = Integer.parseInt(tokens[4]);       // Cable length

                    Cable cableTo = new Cable(start,end,type,bandwidth,length);     // Cable from start to end
                    Cable cableFrom = new Cable(end,start,type,bandwidth,length);   // Cable from end to start
                    graph.addCable(cableTo);
                    graph.addCable(cableFrom);
                }
            }
            reader.close();
            return true;
        }
        catch(FileNotFoundException e)
        {
            System.out.println("\nThe provided file could not be found.\n");
            return false;
        }
        catch(IOException e)
        {
            System.out.println("\n" + e.toString() + "\n");
            return false;
        }
    }

    /**
     * Display the user menu
     * @return the selected option
     */
    public static int showMenu()
    {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("\nPlease select an option:\n" +
                            "1. Find shortest travel time between two switches\n" +
                            "2. Determine if graph is copper-only connected\n" +
                            "3. Find maximum amount of transmitted data between two points\n" +
                            "4. Find lowest average latency spanning tree\n" +
                            "5. Determine if any pair of vertices would cause graph disconnection\n" +
                            "6. Exit\n");

        int option = keyboard.nextInt();
        int start, end;
        switch(option)
        {
            case 1: // Find the lowest latency path between any two points, and give the bandwidth available along that path
                System.out.println("\nStart point: ");
                start = keyboard.nextInt();
                System.out.println("\nEnd point: ");
                end = keyboard.nextInt();
                findShortestTravelTime(start, end);
                break;

            case 2: // Determine whether or not the graph is copper-only connected
                System.out.println("\nThe graph " + (isCopperOnlyConnected()? "is":"is not") + " copper-only connected\n");
                break;

            case 3: // Find the maximum amount of data that can be transmitted from one vertex to another
                System.out.println("\nStart point: ");
                start = keyboard.nextInt();
                System.out.println("\nEnd point: ");
                end = keyboard.nextInt();
                findMaxFlow(start, end);
                break;

            case 4: // Find the lowest average latency spanning tree for the graph
                findLowestAvgLatencyTree();
                break;

            case 5: // Determine whether or not the graph would remain connected if any two vertices in the graph were to fail
                findDisconnect();
                break;

            case 6: // Exit
                break;
            default:
                System.out.println("Invalid argument");
        }

        return option;
    }

    /**
     * Determine if any vertex pair would cause a disconnect in the graph
     */
    public static void findDisconnect()
    {
        boolean isAlwaysConnected = true;
        ArrayList<Cable> failures = new ArrayList<>();

        // Create a copy of the graph, so it doesn't get messed up for other operations if an error occurs
        NetworkGraph graphCopy = new NetworkGraph(graph);

        ConnectionFinder finder;  // Create a variable for the connection finder

        for(Cable c : graphCopy.cables())                           // For all cables in the graph
        {
            int start = c.getStart(), end = c.getEnd();             // Get start and end vertices
            Cable forward = graphCopy.removeCable(start,end);       // Remove the cable going in forward direction
            Cable reverse = graphCopy.removeCable(end,start);       // Remove the cable going in reverse direction

            finder = new ConnectionFinder(graphCopy);               // Find all connections in modified graph
            if(finder.count() <= 0)                                 // If no connected components were found, an error occurred
                System.out.println("\nAn error occurred during connection searching");
            else if(finder.count() > 1)     // If more than one connected component was found, the graph was disconnected
            {
                if(!failures.contains(reverse))     // Add cable to failure list if the reverse wasn't already added
                    failures.add(c);
                isAlwaysConnected = false;
            }

            graphCopy.addCable(forward);    // Add forward cable back in graph
            graphCopy.addCable(reverse);    // Add reverse cable back in graph
        }

        if(isAlwaysConnected)               // If flag wasn't triggered, then no pair disconnected the graph
            System.out.println("\nThe graph remains connected after removal of any vertex pair");
        else
        {
            System.out.println("\nThe following vertex pairs caused the graph to disconnect:");
            for(Cable fail:failures)
                System.out.println(fail.toString());
        }
    }

    /**
     * Find the lowest average latency spanning tree of the graph
     */
    public static void findLowestAvgLatencyTree()
    {
        System.out.println("\nLowest Average Latency Spanning Tree\n");
        System.out.printf("%10s%-15s\n", "", "Connections:");

        KruskalMST kruskal = new KruskalMST(graph);     // Create new kruskal algorithm object
        for(Cable c:kruskal.cables())
            System.out.printf("%15s%-15s\n", "", c.toString());
        System.out.printf("\n%10s%-8s%-5.5e s\n", "", "Weight:", kruskal.weight());
    }

    // Find the lowest latency path between any two points, and give the bandwidth available along that path.
    /**
     * Find the lowest latency path between any two points, and give the minimum bandwidth available along that path.
     * @param start starting vertex
     * @param end   ending vertex
     */
    public static void findShortestTravelTime(int start, int end)
    {
        Dijkstra dijkstra = new Dijkstra(graph, start);  // Create new Dijkstra object with source = start

        if(dijkstra.hasPathTo(end))     // If a path exists from start to end
        {
            Iterable<Cable> path = dijkstra.pathTo(end);    // Get shortest path
            double totalTime = 0.0;                         // Time accumulator
            int minBW = -1;                                 // Initialize minimum bandwidth to flag value

            System.out.printf("\nShortest Path from %d to %d:\n", start, end);
            for(Cable cable:path)
            {
                System.out.printf("%s\n", cable.toString());
                totalTime += cable.getTime();

                if(minBW < 0)                           // For first cable in path, set minimum to cable's bandwidth
                    minBW = cable.getBandwidth();
                else if(cable.getBandwidth() < minBW)
                    minBW = cable.getBandwidth();
            }

            System.out.printf("\nTotal Time: %.5e s\n",totalTime);
            if(minBW >= 0)
                System.out.printf("Minimum Bandwidth: %d Mbs\n",minBW);
            else
                System.out.println("Minimum Bandwidth: 0 Mbs");
        }
        else
            System.out.printf("\nA path does not exist from %d to %d\n", start, end);
    }

    /**
     * Determine if removing all fiber-optic wires leaves the graph still connected
     * @return true if copper-connected, false otherwise
     */
    public static boolean isCopperOnlyConnected()
    {
        NetworkGraph copperGraph = new NetworkGraph(graph);     // Create copy of graph
        int vertex1, vertex2;
        for(Cable cable:copperGraph.cables())                   // For each cable in graph
        {
            if(cable.getType() == Cable.CableType.FIBER_OPTIC)  // If the cable's type is FIBER_OPTIC
            {
                vertex1 = cable.getStart();                     // Get start vertex
                vertex2 = cable.getEnd();                       // Get end vertex
                copperGraph.removeCable(vertex1,vertex2);       // Remove cable going in forward direction
                copperGraph.removeCable(vertex2,vertex1);       // Remove cable going in reverse direction
            }
        }

        Dijkstra dijkstra = new Dijkstra(copperGraph, 0);       // Create a new Dijkstra object from copper-only graph
        return dijkstra.isGraphConnected();                     // Determine if graph is still connected
    }

    /**
     * Find maximum flow from start to end
     * @param start start vertex
     * @param end   end vertex
     */
    public static void findMaxFlow(int start, int end)
    {
        FordFulkerson maxflow = new FordFulkerson(graph, start, end);
        System.out.printf("\nMax flow value = %.2f Mbs\n", maxflow.value());
    }

    public static void main(String[] args)
    {
        if(args.length > 0)
        {
            if(config(args[0]))
            {
                // NOTE: Uncomment the following block to show cables and connections in the the console.
                //       Was commented out just in cause you're testing with large graph files and don't
                //       want to print a million things.

                // System.out.println("\nCables:\n");
                // for(Cable c:graph.cables())
                //     System.out.println(c.toString() + ", Time: " + c.getTime());
                //
                // System.out.println("\nAdjacency Lists:");
                // for(int i=0; i<graph.getNumSwitches(); i++)
                // {
                //     System.out.println("\nSwitch " + i + " Adjacency List:");
                //     for(Cable c:graph.getAdjacencyList(i))
                //         System.out.println(c.toString());
                // }

                int option = 0;
                do
                {
                    option = showMenu();
                }while(option != 6);
            }
        }
        else
            System.out.println("Please provide an input file");
    }
}
