package org.lattuse.algo.scc;

import org.junit.jupiter.api.Test;
import org.lattuse.algo.graph.Graph;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CondensationGraphTest {

    @Test
    public void testCondensationGraph() {
        Graph g = new Graph(5, true, 0, "edge");
        g.addEdge(0, 1, 1);
        g.addEdge(1, 2, 1);
        g.addEdge(2, 0, 1);  // cycle 0-1-2
        g.addEdge(3, 4, 1);

        TarjanSCC scc = new TarjanSCC(g);
        List<List<Integer>> sccs = scc.findSCCs();

        CondensationGraph condGraph = new CondensationGraph(g, sccs);
        Graph cg = condGraph.condensation;

        // There should be 3 SCCs: {0,1,2}, {3}, {4}
        assertEquals(3, sccs.size());
        assertEquals(3, cg.n);

        // Edges in condensation graph should reflect edges between SCCs (3->4)
        assertTrue(cg.adj[1].size() == 1 || cg.adj[2].size() == 1);
    }
}

