package org.lattuse.algo.output;

import org.lattuse.algo.metrics.Metrics;
import java.util.List;

public class GraphOutput {
    public int id;  // ID assigned by input order
    public List<List<Integer>> sccComponents;
    public List<Integer> topoOrder;
    public int[] shortestDistances;
    public int[] longestDistances;
    public List<Integer> longestPath;
    public Metrics metrics;

    public GraphOutput(int id, List<List<Integer>> sccComponents, List<Integer> topoOrder,
                       int[] shortestDistances, int[] longestDistances, List<Integer> longestPath,
                       Metrics metrics) {
        this.id = id;
        this.sccComponents = sccComponents;
        this.topoOrder = topoOrder;
        this.shortestDistances = shortestDistances;
        this.longestDistances = longestDistances;
        this.longestPath = longestPath;
        this.metrics = metrics;
    }
}



