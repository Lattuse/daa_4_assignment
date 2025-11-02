package org.lattuse.algo;


import org.lattuse.algo.dagsp.DAGShortestPaths;
import org.lattuse.algo.graph.Graph;
import org.lattuse.algo.output.GraphOutput;
import org.lattuse.algo.scc.CondensationGraph;
import org.lattuse.algo.scc.TarjanSCC;
import org.lattuse.algo.topo.TopologicalSort;

import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        String filepath = "data/input.json";

        ObjectMapper mapper = new ObjectMapper();
        Map<?, ?> root = mapper.readValue(new File(filepath), Map.class);
        List<Map<String, Object>> graphs = (List<Map<String, Object>>) root.get("graphs");

        if (graphs == null || graphs.isEmpty()) {
            System.out.println("No graphs available.");
            return;
        }

        for (int index = 0; index < graphs.size(); index++) {
            System.out.println("\n=== Graph " + index + " ===\n");

            Graph graph = createGraphFromMap(graphs.get(index));

            System.out.println("Nodes: " + graph.n);

            TarjanSCC scc = new TarjanSCC(graph);
            List<List<Integer>> sccs = scc.findSCCs();
            System.out.println("SCC count: " + sccs.size());

            CondensationGraph condensation = new CondensationGraph(graph, sccs);
            Graph condGraph = condensation.condensation;

            TopologicalSort topo = new TopologicalSort(condGraph);
            List<Integer> topoOrder = topo.kahnSort();

            System.out.println("Topological order of SCCs: " + topoOrder);

            int sourceScc = condensation.getSccId()[graph.source];
            DAGShortestPaths dagSp = new DAGShortestPaths(condGraph, sourceScc);

            int[] shortest = dagSp.shortestPaths();
            System.out.println("Shortest distances:");
            for (int i = 0; i < shortest.length; i++) {
                System.out.printf("SCC %d: %d%n", i, shortest[i]);
            }

            int[] longest = dagSp.longestPaths();
            System.out.println("Longest distances:");
            for (int i = 0; i < longest.length; i++) {
                System.out.printf("SCC %d: %d%n", i, longest[i]);
            }

            List<Integer> longestPath = dagSp.reconstructLongestPath(longest);
            System.out.println("Longest path in condensation graph: " + longestPath);

            GraphOutput output = new GraphOutput(
                    sccs,
                    topoOrder,
                    shortest,
                    longest,
                    longestPath
            );

            String outputFilename = "output/output_graph_" + index + ".json";
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(outputFilename), output);

            System.out.println("Output saved to " + outputFilename);
        }
    }

    private static Graph createGraphFromMap(Map<String, Object> map) {
        int n = (int) map.get("n");
        boolean directed = map.containsKey("directed") ? (Boolean) map.get("directed") : true;
        Object sourceObj = map.get("source");
        int source = sourceObj == null ? -1 : ((Number) sourceObj).intValue();
        String weightModel = map.containsKey("weight_model") ? (String) map.get("weight_model") : "edge";

        Graph graph = new Graph(n, directed, source, weightModel);

        List<Map<String, Object>> edges = (List<Map<String, Object>>) map.get("edges");
        for (Map<String, Object> e : edges) {
            int u = ((Number) e.get("u")).intValue();
            int v = ((Number) e.get("v")).intValue();
            int w = ((Number) e.get("w")).intValue();
            graph.addEdge(u, v, w);
        }
        return graph;
    }
}

