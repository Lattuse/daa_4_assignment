package org.lattuse.algo.topo;

import org.junit.jupiter.api.Test;
import org.lattuse.algo.graph.Graph;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TopologicalSortTest {

    @Test
    public void testSimpleDAG() {
        Graph g = new Graph(3, true, 0, "edge");
        g.addEdge(0, 1, 1);
        g.addEdge(1, 2, 1);
        TopologicalSort topo = new TopologicalSort(g);
        List<Integer> order = topo.kahnSort();
        assertEquals(3, order.size());
        assertTrue(order.indexOf(0) < order.indexOf(1));
        assertTrue(order.indexOf(1) < order.indexOf(2));
    }

    @Test
    public void testCycleDetection() {
        Graph g = new Graph(2, true, 0, "edge");
        g.addEdge(0, 1, 1);
        g.addEdge(1, 0, 1);
        TopologicalSort topo = new TopologicalSort(g);
        List<Integer> order = topo.kahnSort();
        assertNull(order, "Cycle graph should not return a topological order");
    }
}

