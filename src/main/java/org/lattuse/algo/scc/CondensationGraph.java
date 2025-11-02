package org.lattuse.algo.scc;

import org.lattuse.algo.graph.Graph;

import java.util.Arrays;
import java.util.List;


public class CondensationGraph {
    private final Graph originalGraph;
    private final List<List<Integer>> sccs;
    private final int[] sccId;
    public final Graph condensation; // DAG resultant graph

    public CondensationGraph(Graph originalGraph, List<List<Integer>> sccs) {
        this.originalGraph = originalGraph;
        this.sccs = sccs;
        sccId = new int[originalGraph.n];
        Arrays.fill(sccId, -1);
        for (int i = 0; i < sccs.size(); i++) {
            for (Integer node : sccs.get(i)) {
                sccId[node] = i;
            }
        }
        condensation = buildCondensation();
    }

    private Graph buildCondensation() {
        int sccCount = sccs.size();
        Graph cond = new Graph(sccCount, true, -1, "edge");

        for (int u = 0; u < originalGraph.n; u++) {
            int fromScc = sccId[u];
            for (Graph.Edge e : originalGraph.adj[u]) {
                int toScc = sccId[e.to];
                if (fromScc != toScc) {
                    cond.addEdge(fromScc, toScc, e.weight);
                }
            }
        }
        return cond;
    }

    public int[] getSccId() {
        return sccId;
    }
}

