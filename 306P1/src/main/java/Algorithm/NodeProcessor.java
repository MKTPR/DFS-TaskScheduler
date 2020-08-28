package Algorithm;

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
