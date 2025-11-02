package org.lattuse.algo.dagsp;

import org.lattuse.algo.graph.Graph;
import org.lattuse.algo.topo.TopologicalSort;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

//Single-source shortest and longest path algorithms for DAG.

public class DAGShortestPaths {
    private final Graph graph;
    private final int source;

    public int relaxations = 0;

    public DAGShortestPaths(Graph graph, int source) {
        this.graph = graph;
        this.source = source;
    }

    public int[] shortestPaths() {
        List<Integer> order = new TopologicalSort(graph).kahnSort();
        if (order == null) throw new IllegalArgumentException("Graph is not a DAG");

        int n = graph.n;
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;

        relaxations = 0;

        for (int u : order) {
            if (dist[u] == Integer.MAX_VALUE) continue;
            for (Graph.Edge e : graph.adj[u]) {
                if (dist[u] + e.weight < dist[e.to]) {
                    dist[e.to] = dist[u] + e.weight;
                    relaxations++;
                }
            }
        }
        return dist;
    }

    public int[] longestPaths() {
        List<Integer> order = new TopologicalSort(graph).kahnSort();
        if (order == null) throw new IllegalArgumentException("Graph is not a DAG");

        int n = graph.n;
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MIN_VALUE);
        dist[source] = 0;

        relaxations = 0;

        for (int u : order) {
            if (dist[u] == Integer.MIN_VALUE) continue;
            for (Graph.Edge e : graph.adj[u]) {
                if (dist[u] + e.weight > dist[e.to]) {
                    dist[e.to] = dist[u] + e.weight;
                    relaxations++;
                }
            }
        }
        return dist;
    }
    // Reconstruct longest path from source to the farthest node
    public List<Integer> reconstructLongestPath(int[] dist) {
        int n = graph.n;
        int maxNode = 0;
        int maxDist = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            if (dist[i] > maxDist) {
                maxDist = dist[i];
                maxNode = i;
            }
        }
        List<Integer> path = new ArrayList<>();
        int cur = maxNode;
        path.add(cur);
        outer:
        while (cur != source) {
            for (int u = 0; u < n; u++) {
                for (Graph.Edge e : graph.adj[u]) {
                    if (e.to == cur && dist[u] + e.weight == dist[cur]) {
                        cur = u;
                        path.add(cur);
                        continue outer;
                    }
                }
            }
            break;
        }
        Collections.reverse(path);
        return path;
    }
}

