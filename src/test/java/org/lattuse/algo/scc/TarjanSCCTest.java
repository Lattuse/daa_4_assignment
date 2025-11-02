package org.lattuse.algo.scc;

import org.junit.jupiter.api.Test;
import org.lattuse.algo.graph.Graph;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TarjanSCCTest {

    @Test
    public void testSingleNode() {
        Graph g = new Graph(1, true, 0, "edge");
        TarjanSCC scc = new TarjanSCC(g);
        List<List<Integer>> components = scc.findSCCs();
        assertEquals(1, components.size(), "Single node graph should have one SCC");
        assertEquals(1, components.get(0).size());
    }

    @Test
    public void testTwoNodeCycle() {
        Graph g = new Graph(2, true, 0, "edge");
        g.addEdge(0, 1, 1);
        g.addEdge(1, 0, 1);
        TarjanSCC scc = new TarjanSCC(g);
        List<List<Integer>> components = scc.findSCCs();
        assertEquals(1, components.size(), "Two nodes cycle should be one SCC");
        assertTrue(components.get(0).contains(0));
        assertTrue(components.get(0).contains(1));
    }

    @Test
    public void testAcyclicGraph() {
        Graph g = new Graph(3, true, 0, "edge");
        g.addEdge(0, 1, 5);
        g.addEdge(1, 2, 10);
        TarjanSCC scc = new TarjanSCC(g);
        List<List<Integer>> components = scc.findSCCs();
        assertEquals(3, components.size(), "Acyclic graph should produce 3 SCCs (each node)");
    }
}