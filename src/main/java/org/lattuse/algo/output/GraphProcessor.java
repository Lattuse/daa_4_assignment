package org.lattuse.algo.output;

import org.lattuse.algo.dagsp.DAGShortestPaths;
import org.lattuse.algo.graph.Graph;
import org.lattuse.algo.metrics.Metrics;
import org.lattuse.algo.scc.CondensationGraph;
import org.lattuse.algo.scc.TarjanSCC;
import org.lattuse.algo.topo.TopologicalSort;

import java.util.List;

// This class is responsible for the full analysis pipeline on a single graph.

public class GraphProcessor {

    public GraphOutput process(int graphId, Graph graph) {
        // SCC + timing
        long startScc = System.nanoTime();
        TarjanSCC scc = new TarjanSCC(graph);
        List<List<Integer>> sccs = scc.findSCCs();
        long endScc = System.nanoTime();

        // Condensation & topological sort + timing
        long startTopo = System.nanoTime();
        CondensationGraph condensation = new CondensationGraph(graph, sccs);
        Graph condGraph = condensation.condensation;
        TopologicalSort topo = new TopologicalSort(condGraph);
        List<Integer> topoOrder = topo.kahnSort();
        long endTopo = System.nanoTime();

        // DAG shortest + longest paths + timing
        int sourceScc = condensation.getSccId()[graph.source];

        long startSP = System.nanoTime();
        DAGShortestPaths dagSp = new DAGShortestPaths(condGraph, sourceScc);
        int[] shortest = dagSp.shortestPaths();
        int[] longest = dagSp.longestPaths();
        long endSP = System.nanoTime();

        long startLP = System.nanoTime();
        List<Integer> longestPath = dagSp.reconstructLongestPath(longest);
        long endLP = System.nanoTime();

        // Collect metrics
        Metrics metrics = new Metrics();
        metrics.sccDfsVisits = scc.dfsCount;
        metrics.sccEdgeVisits = scc.edgeCount;
        metrics.topoPushCount = topo.pushCount;
        metrics.topoPopCount = topo.popCount;
        metrics.dagRelaxations = dagSp.relaxations;
        metrics.timeSccNs = endScc - startScc;
        metrics.timeTopoNs = endTopo - startTopo;
        metrics.timeShortestPathNs = endSP - startSP;
        metrics.timeLongestPathNs = endLP - startLP;

        // Prepare and return output container
        return new GraphOutput(
                graphId, sccs, topoOrder, shortest, longest, longestPath, metrics
        );
    }
}

