package org.lattuse.algo.dagsp;


import org.junit.jupiter.api.Test;
import org.lattuse.algo.graph.Graph;
import static org.junit.jupiter.api.Assertions.*;

public class DAGShortestPathsTest {

    @Test
    public void testShortestPathSimple() {
        Graph g = new Graph(3, true, 0, "edge");
        g.addEdge(0, 1, 3);
        g.addEdge(1, 2, 4);
        DAGShortestPaths sp = new DAGShortestPaths(g, 0);
        int[] dist = sp.shortestPaths();
        assertEquals(0, dist[0]);
        assertEquals(3, dist[1]);
        assertEquals(7, dist[2]);
    }

    @Test
    public void testLongestPathSimple() {
        Graph g = new Graph(3, true, 0, "edge");
        g.addEdge(0, 1, 3);
        g.addEdge(1, 2, 4);
        DAGShortestPaths sp = new DAGShortestPaths(g, 0);
        int[] dist = sp.longestPaths();
        assertEquals(0, dist[0]);
        assertEquals(3, dist[1]);
        assertEquals(7, dist[2]);
    }

    @Test
    public void testUnreachableNode() {
        Graph g = new Graph(4, true, 0, "edge");
        g.addEdge(0, 1, 1);
        // node 3 disconnected
        DAGShortestPaths sp = new DAGShortestPaths(g, 0);
        int[] dist = sp.shortestPaths();
        assertEquals(Integer.MAX_VALUE, dist[3]);
        int[] longest = sp.longestPaths();
        assertEquals(Integer.MIN_VALUE, longest[3]);
    }
}

