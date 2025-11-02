package org.lattuse.algo.scc;

import org.lattuse.algo.graph.Graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;


public class TarjanSCC {
    private final Graph graph;
    private final int[] ids, low;
    private final boolean[] onStack;
    private final Deque<Integer> stack;
    private int id = 0;

    private final List<List<Integer>> sccs = new ArrayList<>();


    public int dfsCount = 0;
    public int edgeCount = 0;

    public TarjanSCC(Graph graph) {
        this.graph = graph;
        int n = graph.n;
        ids = new int[n];
        low = new int[n];
        onStack = new boolean[n];
        stack = new ArrayDeque<>();
        for (int i = 0; i < n; i++) ids[i] = -1;
    }


    public List<List<Integer>> findSCCs() {
        for (int i = 0; i < graph.n; i++) {
            if (ids[i] == -1) dfs(i);
        }
        return sccs;
    }

    private void dfs(int at) {
        ids[at] = id;
        low[at] = id;
        id++;
        stack.push(at);
        onStack[at] = true;
        dfsCount++;

        for (Graph.Edge edge : graph.adj[at]) {
            edgeCount++;
            int to = edge.to;
            if (ids[to] == -1) {
                dfs(to);
                low[at] = Math.min(low[at], low[to]);
            } else if (onStack[to]) {
                low[at] = Math.min(low[at], ids[to]);
            }
        }
        if (low[at] == ids[at]) {
            List<Integer> component = new ArrayList<>();
            while (true) {
                int node = stack.pop();
                onStack[node] = false;
                component.add(node);
                if (node == at) break;
            }
            sccs.add(component);
        }
    }
}

