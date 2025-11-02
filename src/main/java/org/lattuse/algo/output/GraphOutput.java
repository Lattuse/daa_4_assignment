package org.lattuse.algo.output;

import java.util.List;

public class GraphOutput {
    public List<List<Integer>> sccComponents;
    public List<Integer> topoOrder;
    public int[] shortestDistances;
    public int[] longestDistances;
    public List<Integer> longestPath;

    public GraphOutput() { }

    public GraphOutput(List<List<Integer>> sccComponents, List<Integer> topoOrder,
                       int[] shortestDistances, int[] longestDistances, List<Integer> longestPath) {
        this.sccComponents = sccComponents;
        this.topoOrder = topoOrder;
        this.shortestDistances = shortestDistances;
        this.longestDistances = longestDistances;
        this.longestPath = longestPath;
    }
}

