package org.lattuse.algo.graph;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Graph {
    public final int n; // number of nodes
    public final List<Edge>[] adj; // adjacency list
    public final boolean directed;
    public final int source;
    public final String weightModel;

    public static class Edge {
        public final int to;
        public final int weight;
        public Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    @SuppressWarnings("unchecked")
    public Graph(int n, boolean directed, int source, String weightModel) {
        this.n = n;
        this.directed = directed;
        this.adj = new ArrayList[n];
        for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();
        this.source = source;
        this.weightModel = weightModel;
    }

    public void addEdge(int u, int v, int w) {
        adj[u].add(new Edge(v, w));
        if (!directed) {
            adj[v].add(new Edge(u, w));
        }
    }

    public static Graph fromJsonFile(String filename) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<?, ?> map = mapper.readValue(new File(filename), Map.class);
        int n = (int) map.get("n");

        Boolean directedVal = (Boolean) map.get("directed");
        boolean directed = directedVal != null ? directedVal : true;

        Integer sourceVal = (Integer) map.get("source");
        int source = sourceVal != null ? sourceVal : -1;

        String weightModelVal = (String) map.get("weight_model");
        String weightModel = weightModelVal != null ? weightModelVal : "edge";

        Graph g = new Graph(n, directed, source, weightModel);
        List<Map<String,Object>> edges = (List<Map<String,Object>>) map.get("edges");
        for (Map<String,Object> e : edges) {
            int u = (int) e.get("u");
            int v = (int) e.get("v");
            int w = (int) e.get("w");
            g.addEdge(u, v, w);
        }
        return g;
    }

}

