
package raft.net;
import raft.rpc.RaftMessage;


public interface Transport {
    
    public void send (RaftMessage message, String destinationNodeId );

    public void start (MessageHandler handler)
}
