package manifold;

import java.util.Optional;

import graph.AdjacencyList;
import graph.Graph;

class NearestNeighborGraph {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(NearestNeighborGraph.class);

    /**
     * The original sample index.
     */
    public final int[] index;
    /**
     * Nearest neighbor graph.
     */
    public final Graph graph;

    /**
     * Constructor.
     * @param index the original sample index.
     * @param graph the nearest neighbor graph.
     */
    public NearestNeighborGraph(int[] index, Graph graph) {
        this.index = index;
        this.graph = graph;
    }

    /** The nearest neighbor edge consumer. */
    public interface EdgeConsumer {
        /**
         * Performs this operation on the edge.
         * @param v1 the center of neighborhood.
         * @param v2 the neighbor.
         * @param weight the weight/distance.
         * @param j the index of neighbor of knn search.
         */
        void accept(int v1, int v2, double weight, int j);
    }

    /**
     * Creates a nearest neighbor graph.
     *
     * @param data the dataset.
     * @param k k-nearest neighbor.
     * @param sideEffect an optional lambda to perform some side effect operations.
     */
    public static Graph of(double[][] data, int k, Optional<EdgeConsumer> sideEffect) {
        // This is actually faster on many core systems.
        LinearSearch<double[]> knn = new LinearSearch<>(data, new EuclideanDistance());

        int n = data.length;
        Graph graph = new AdjacencyList(n);

        if (sideEffect.isPresent()) {
            for (int i = 0; i < n; i++) {
                EdgeConsumer consumer = sideEffect.get();
                Neighbor<double[], double[]>[] neighbors = knn.knn(data[i], k);

                int v1 = i;
                for (int j = 0; j < neighbors.length; j++) {
                    int v2 = neighbors[j].index;
                    double weight = neighbors[j].distance;
                    graph.setWeight(v1, v2, weight);
                    consumer.accept(v1, v2, weight, j);
                }
            }
        } else {
            for (int i = 0; i < n; i++) {
                for (Neighbor<double[], double[]> neighbor : knn.knn(data[i], k)) {
                    graph.setWeight(i, neighbor.index, neighbor.distance);
                }
            }
        }

        return graph;
    }

    /**
     * Finds the largest connected components of a nearest neighbor graph.
     * If the graph has multiple connected components, keep the
     * largest one.
     *
     * @param graph the nearest neighbor graph.
     */
    public static NearestNeighborGraph largest(Graph graph) {
        int n = graph.getNumVertices();

        // Use largest connected component.
        int[][] cc = graph.bfs();
        int[] index;
        if (cc.length == 1) {
            index = new int[n];
            for (int i = 0; i < n; i++) {
                index[i] = i;
            }
        } else {
            n = 0;
            int largest = 0;
            for (int i = 0; i < cc.length; i++) {
                if (cc[i].length > n) {
                    largest = i;
                    n = cc[i].length;
                }
            }

            logger.info("{} connected components, largest one has {} samples.", cc.length, n);

            index = cc[largest];
            graph = graph.subgraph(index);
        }

        return new NearestNeighborGraph(index, graph);
    }
}
