package org.lattuse.algo;

import org.lattuse.algo.graph.Graph;
import org.lattuse.algo.output.GraphOutput;
import org.lattuse.algo.output.GraphProcessor;

import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.Map;


public class Main {
    public static void main(String[] args) throws Exception {
        String filepath = "data/input.json";

        // Create Jackson ObjectMapper instance for JSON parsing
        ObjectMapper mapper = new ObjectMapper();
        Map<?, ?> root = mapper.readValue(new File(filepath), Map.class);

        // Get the list of individual graph objects
        List<Map<String, Object>> graphs = (List<Map<String, Object>>) root.get("graphs");

        if (graphs == null || graphs.isEmpty()) {
            System.out.println("No graphs available.");
            return;
        }

        List<GraphOutput> allResults = new java.util.ArrayList<>();

        // Create a processor instance for running analysis on graphs
        GraphProcessor processor = new GraphProcessor();

        // Iterate over each graph, parse and process it
        for (int index = 0; index < graphs.size(); index++) {
            System.out.println("\n=== Processing graph " + index + " ===\n");

            Graph graph = createGraphFromMap(graphs.get(index));

            GraphOutput output = processor.process(index, graph);

            allResults.add(output);

            System.out.println("Processed graph " + index + ": SCCs=" + output.sccComponents.size());
        }

        mapper.writerWithDefaultPrettyPrinter()
                .writeValue(new File("output/graphs_output.json"), allResults);

        System.out.println("All results saved to 'output/graphs_output.json'");
    }


    // converts a raw map representing a graph JSON object into a Graph instance.

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

