package org.lattuse.algo.metrics;


// Here is container for traversal and algorithm metrics.

public class Metrics {
    public long sccDfsVisits;
    public long sccEdgeVisits;
    public long topoPushCount;
    public long topoPopCount;
    public long dagRelaxations;
    public long timeSccNs;
    public long timeTopoNs;
    public long timeShortestPathNs;
    public long timeLongestPathNs;
}

