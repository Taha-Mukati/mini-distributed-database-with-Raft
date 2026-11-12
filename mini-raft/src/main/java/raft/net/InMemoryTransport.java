package raft.net;
import raft.rpc.RaftMessage;

public class InMemoryTransport implements Transport {
    private MessageHandler handler;

    private final String nodeId;
    private final InMemoryNetwork network;


    public InMemoryTransport(String nodeId, InMemoryNetwork network) {
        this.nodeId = nodeId;
        this.network = network;
    }

    @Override
    public void start(MessageHandler handler) {
        network.register(nodeId, handler);
    }

    @Override
    public void stop() {
        network.unregister(nodeId);
    }
    
    @Override
    public void send(String destinationNodeId, RaftMessage message) {
        network.deliver(destinationNodeId, message);
    }



}
