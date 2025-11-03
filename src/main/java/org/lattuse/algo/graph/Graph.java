package org.lattuse.algo.graph;

import java.util.ArrayList;
import java.util.List;

// Graph class to represent directed weighted graph.
// Supported JSON parsing from input file, but now it's in GraphProcessor, GraphOutput files


public class Graph {
    public final int n;
    public final List<Edge>[] adj;
    public final boolean directed;
    public final int source;
    public final String weightModel;

    // Edge class representing an edge to a node with a weight
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

    // Adds an edge from u to v with weight w
    public void addEdge(int u, int v, int w) {
        adj[u].add(new Edge(v, w));
        if (!directed) {
            adj[v].add(new Edge(u, w));
        }
    }


}

