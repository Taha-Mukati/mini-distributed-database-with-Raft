
package raft.net;
import raft.rpc.RaftMessage;


public interface Transport {
    
    public void send (String destinationNodeId, RaftMessage message);

    public void start (MessageHandler handler);

    public void stop ();
    
}
