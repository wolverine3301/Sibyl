package manifold;

/**
 * The immutable object encapsulates the results of nearest neighbor search.
 * A returned neighbor for nearest neighbor search contains the key of object
 * (e.g. the weight vector of a neuron) and the object itself (e.g. a neuron,
 * which also contains other information beyond weight vector), an index of
 * object in the dataset, which is often useful, and the distance between
 * the query key to the object key.
 *
 * @param <K> the type of keys.
 * @param <V> the type of associated objects.
 */
public class Neighbor<K, V> implements Comparable<Neighbor<K,V>> {
    /**
     * The key of neighbor.
     */
    public final K key;
    /**
     * The data object of neighbor. It may be same as the key object.
     */
    public final V value;
    /**
     * The index of neighbor object in the dataset.
     */
    public final int index;
    /**
     * The distance between the query and the neighbor.
     */
    public final double distance;

    /**
     * Constructor.
     * @param key the key of neighbor.
     * @param object the value of neighbor.
     * @param index the index of neighbor object in the dataset.
     * @param distance the distance between the query and the neighbor.
     */
    public Neighbor(K key, V object, int index, double distance) {
        this.key = key;
        this.value = object;
        this.index = index;
        this.distance = distance;
    }

    @Override
    public int compareTo(Neighbor<K,V> o) {
        int d = Double.compare(distance, o.distance);
        // Sometime, the dataset contains duplicate samples.
        // If the distances are same, we sort by the sample index.
        return d == 0 ? Integer.compare(index, o.index) : d;
    }

    @Override
    public String toString() {
        return String.format("%s(%d):%d", key, index, distance);
    }

    /** Creates a neighbor object, of which key and object are the same. */
    public static <T> Neighbor<T, T> of(T key, int index, double distance) {
        return new Neighbor<>(key, key, index, distance);
    }
}
