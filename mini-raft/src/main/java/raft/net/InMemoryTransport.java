package raft.net;
import raft.rpc.RaftMessage;

public class InMemoryTransport implements Transport {
    private MessageHandler handler;

    @Override
    public void send(RaftMessage message, String destinationNodeId) {
        if (handler != null) {
            handler.onMessage(message);
        }
    }

    @Override
    public void start(MessageHandler handler) {
        this.handler = handler;
    }

    @Override
    public void stop() {
        this.handler = null;
    }
    
}
