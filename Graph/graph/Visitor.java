package graph;
/**
 * 
 * @author logan.collier
 *
 */
public interface Visitor {
    /**
     * Performs some operations on the currently-visiting vertex
     * @param vertex - the index of vertex.
     */
    void visit(int vertex);
}