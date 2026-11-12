package raft.net;
import raft.rpc.RaftMessage;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class InMemoryNetwork {
    
    private final Map<String, MessageHandler> handlers = new ConcurrentHashMap<>();

    public void register(String nodeId, MessageHandler handler) {
        handlers.put(nodeId, handler);
    }

    public void unregister(String nodeId) {
        handlers.remove(nodeId);
    }

    public void deliver (String destinationNodeId, RaftMessage message) {
        MessageHandler handler = handlers.get(destinationNodeId);
        if (handler != null) {
            handler.onMessage(message);
        }
    }
}
