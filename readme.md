# Project Report
Name: Mukashev Sultanbek

Group: SE-2422


## Data Summary

This project processes multiple directed graphs representing city-service and internal analytics tasks dependencies. Data is provided as JSON containing an array of graphs.

- **Number of graphs processed:** 9
- **Graph sizes:**
    - Nodes range from small (6–10) to medium (10–20) and large scale (up to 50).
- **Weight model:** Edge weights are used consistently across all graphs to represent durations or costs associated with task dependencies.



## Summary of Analysis Output (per graph)

- **Strongly Connected Components (SCC):**  
  Each graph’s cyclic dependencies are detected and compressed via Tarjan’s algorithm producing a list of SCCs and their sizes.

- **Condensation Graph & Topological Ordering:**  
  A DAG representing the graph of SCCs is built. Topological ordering on this condensation provides a valid linearization of cyclically-compressed tasks.

- **Shortest and Longest Paths in DAG:**  
  Using dynamic programming over the topological order:
    - The shortest path distances from the source SCC highlight earliest possible task completions.
    - The longest path (critical path) gives the most extended duration dependency chain, critical for scheduling.
    - The actual longest path vertex sequence is reconstructed for detailed scheduling insight.



## Sample Output Excerpts for Graph ID 0

- **SCC Components:**  
  [ [ 2, 1, 0 ], [ 7 ], [ 6 ], [ 5 ], [ 4 ], [ 3 ] ]

- **Topological Order:**  
  [ 0, 5, 4, 3, 2, 1 ]

- **Shortest Distances from Source SCC:**  
  [ 2147483647, 10, 7, 6, 2, 0 ]  
  (Infinity value or 2147483647 represents unreachable nodes from source.)

- **Longest Distances (Critical Path Lengths):**  
  [ -2147483648, 10, 7, 6, 2, 0 ]  
  (Negative infinity or -2147483648 corresponds to unreachable nodes.)

- **Longest Path (Critical Path):**  
  [ 5, 4, 3, 2, 1 ]



## Metrics and Performance

For each graph, detailed instrumentation metrics are recorded, including:

| Metric                  | Description                                |
|-------------------------|--------------------------------------------|
| SCC DFS Visits          | Number of node visits during SCC detection |
| SCC Edge Visits          | Number of edges traversed in SCC DFS       |
| Topo Push Count          | Number of times nodes pushed in topological sort queue |
| Topo Pop Count           | Number of times nodes popped in topological sort queue  |
| DAG Relaxations          | Number of edge relaxations in shortest/longest paths   |
| Time SCC (ns)            | Nanoseconds taken for SCC computation      |
| Time Topo (ns)           | Nanoseconds taken for topological sorting  |
| Time Shortest Path (ns)  | Nanoseconds taken for shortest path computation  |
| Time Longest Path (ns)   | Nanoseconds taken for longest path computation   |

Typical profiling shows that SCC and topological sorting times scale with graph size, with path computations slightly faster due to linear DP on DAG.



## Conclusion

This project successfully consolidates SCC detection with topological sorting and shortest/longest path DP to analyze complex city-task dependencies. The JSON output provides both structural and performance insights suitable for smart scheduling applications.

