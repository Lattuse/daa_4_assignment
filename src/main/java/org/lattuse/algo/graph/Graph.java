package org.lattuse.algo.graph;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Graph {
    public final int n;
    public final List<Edge>[] adj;
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

        Boolean directedObj = (Boolean) map.get("directed");
        boolean directed = directedObj != null ? directedObj : true;

        Object sourceVal = map.get("source");
        int source = -1;
        if (sourceVal != null) {
            if (sourceVal instanceof Number) {
                source = ((Number) sourceVal).intValue();
            } else if (sourceVal instanceof String) {
                source = Integer.parseInt((String) sourceVal);
            }
        }

        String weightModel = "edge";
        Object wmObj = map.get("weight_model");
        if (wmObj != null && wmObj instanceof String) {
            weightModel = (String) wmObj;
        }

        Graph g = new Graph(n, directed, source, weightModel);

        List<Map<String, Object>> edges = (List<Map<String, Object>>) map.get("edges");
        for (Map<String, Object> e : edges) {
            int u = 0, v = 0, w = 0;
            Object uObj = e.get("u");
            Object vObj = e.get("v");
            Object wObj = e.get("w");
            if (uObj instanceof Number) u = ((Number) uObj).intValue();
            if (vObj instanceof Number) v = ((Number) vObj).intValue();
            if (wObj instanceof Number) w = ((Number) wObj).intValue();

            g.addEdge(u, v, w);
        }
        return g;
    }


}

