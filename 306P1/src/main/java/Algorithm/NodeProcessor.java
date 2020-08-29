package Algorithm;

/**
 * NodeProcessor is a simple class which represents a Node-Processor pair.
 * This class is used for methods in other classes which requires to return a Node-Processor pair.
 */
public class NodeProcessor {
    private Node node;
    private Processor processor;

    NodeProcessor(Node node, Processor processor){
        this.node = node;
        this.processor = processor;
    }

    public Processor getProcessor() {
        return processor;
    }

    public Node getNode() {
        return node;
    }
}
