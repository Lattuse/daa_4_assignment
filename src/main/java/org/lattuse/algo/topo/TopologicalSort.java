package org.lattuse.algo.topo;

import org.lattuse.algo.graph.Graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;


public class TopologicalSort {
    private final Graph graph;

    public int pushCount = 0;
    public int popCount = 0;

    public TopologicalSort(Graph graph) {
        this.graph = graph;
    }

    public List<Integer> kahnSort() {
        int n = graph.n;
        int[] indegree = new int[n];
        for (int u = 0; u < n; u++) {
            for (Graph.Edge e : graph.adj[u]) {
                indegree[e.to]++;
            }
        }
        Deque<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                queue.add(i);
                pushCount++;
            }
        }
        List<Integer> order = new ArrayList<>();
        while (!queue.isEmpty()) {
            int u = queue.poll();
            popCount++;
            order.add(u);
            for (Graph.Edge e : graph.adj[u]) {
                indegree[e.to]--;
                if (indegree[e.to] == 0) {
                    queue.add(e.to);
                    pushCount++;
                }
            }
        }
        if (order.size() != n) {
            return null;
        }
        return order;
    }
}

