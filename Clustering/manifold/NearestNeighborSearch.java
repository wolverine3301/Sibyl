package manifold;

/**
 * Nearest neighbor search, also known as proximity search, similarity search
 * or closest point search, is an optimization problem for finding closest
 * points in metric spaces. The problem is: given a set S of points in a metric
 * space M and a query point q &isin; M, find the closest point in S to q.
 *
 * In machine learning, we often build a nearest neighbor search data structure,
 * and then search with object in the same dataset. The object itsef is of course
 * the nearest one with distance 0. But this is meaningless and we therefore do
 * the reference check during the search and excludes the query object from the
 * results.
 * 
 * @param <K> the type of keys.
 * @param <V> the type of associated objects.
 *
 */
public interface NearestNeighborSearch<K, V> {
    /**
     * Search the nearest neighbor to the given sample.
     *
     * @param q the query key.
     * @return the nearest neighbor
     */
    Neighbor<K, V> nearest(K q);
}
